package kr.or.ddit.user.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserService;
import kr.or.ddit.util.FileUtil;
import kr.or.ddit.validator.UserVoValidator;

@RequestMapping("user")
@Controller
public class UserController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(path = "allUser",method = RequestMethod.GET)
	public String allUser(Model model) {
		
		List<UserVo> userList = userService.selectAllUser();
		
		
		model.addAttribute("userList", userList);
		return "/user/allUser";
	}
	
	/////////////////////////////////////////////////////
	
	@RequestMapping(path = "allUserTiles",method = RequestMethod.GET)
	public String allUserTiles(Model model) {
		
		List<UserVo> userList = userService.selectAllUser();
		
		
		model.addAttribute("userList", userList);
		return "tiles.user.allUser";
	}
	
	/////////////////////////////////////////////////////
	
	//@RequestMapping("paingUser")
	public String paingUser(@RequestParam(defaultValue = "1") int page, 
							@RequestParam(defaultValue = "5") int pageSize,
							@RequestParam(name = "p") int price) {
		logger.debug("page : {}, pageSize : {}, price : {} ", page, pageSize, price);
		
		
		
		return "";
	}
	

	@RequestMapping(path="deleteUser",method = RequestMethod.POST)
	public String deleteUser(String userid, RedirectAttributes ra) {
		
		int deleteCnt = 0;
		
		try {
			deleteCnt = userService.deleteUser(userid);
		} catch (Exception e) {
			e.printStackTrace();
			deleteCnt= 0;
		}
		
		if(deleteCnt==1) {
			return "redirect:/user/pagingUser";
		}else {
			ra.addAttribute("userid", userid);
			return "redirect:user/user";
		}
		
		
	}
	
	@RequestMapping(path="modifyUser",method = RequestMethod.GET)
	public String modifyUserView(String userid, Model model) {
		
		model.addAttribute("user", userService.selectUser(userid));
		
		return "user/userModify";
	}
	
	@RequestMapping(path="modifyUser",method = RequestMethod.POST)
	public String modifyUser(UserVo userVo, Model model,RedirectAttributes ra, MultipartFile profile) {
		userVo.setFilename("");
		UserVo vo = userService.selectUser(userVo.getUserid());
		
		
		if(userVo.getFilename()==null) {
			userVo.setFilename(vo.getFilename());
			
			if(userVo.getFilename()==null) {
				userVo.setFilename("");
			}
		}
		
		
		if(userVo.getRealfilename()==null) {
			userVo.setRealfilename(vo.getRealfilename());
			
			if(userVo.getRealfilename()==null) {
				userVo.setRealfilename("");
			}
		}
		
		
		try {
			String fileExtension = FileUtil.getFileExtension(profile.getOriginalFilename());
			String realFileName = UUID.randomUUID().toString()+fileExtension;
			
			profile.transferTo(new File("d:/upload/" + realFileName));
			userVo.setFilename(profile.getOriginalFilename());
			
			userVo.setRealfilename(realFileName);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int updateCnt = 0;
		
		try {
			updateCnt = userService.modifyUser(userVo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			updateCnt= 0;
		}
		
		 
		
		if(updateCnt==1) {
			ra.addAttribute("userid", userVo.getUserid());
			return "redirect:/user/userInfo";
		}else {
			model.addAttribute("user", userVo);
			return "user/userModify";
		}
		
	}
	
	
	
	@RequestMapping("fileupload/upload")
	public String fileupload(String userid, MultipartFile picture) {
		
		logger.debug("userid : {}", userid);
		logger.debug("filesize : {}, name : {} , originalFilename : {}",picture.getSize(),picture.getName(),picture.getOriginalFilename());
		
		
		try {
			picture.transferTo(new File("d:/upload/" + picture.getOriginalFilename()));
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "file/view";
	}
	
	
	
	@RequestMapping(path="registUser",method = RequestMethod.GET)
	public String registUser() {
		
		
		return "user/registUser";
	}
	
	
	//중요! 해당 커멘드 객체 뒤에 꼭 BindingResult 객체 넣기
	//bindingResult 객체는 command 객체 바로 뒤에 인자로 기술해야 한다
	@RequestMapping(path="registUser",method = RequestMethod.POST)
	public String registUser(@Valid UserVo uservo, BindingResult result, Model model, MultipartFile profile) {
		// 위에서 객체를보내고
		//new UserVoValidator().validate(uservo, result);
		// 여기서 바뀐 결과가 나옴(참조객체니깐)
		if(result.hasErrors()) {
			logger.debug("result has error");
			model.addAttribute("user", uservo);
			return "user/registUser";
		}
		
		try {
			String fileExtension = FileUtil.getFileExtension(profile.getOriginalFilename());
			String realFileName = UUID.randomUUID().toString()+fileExtension;
			
			profile.transferTo(new File("d:/upload/" + realFileName));
			uservo.setFilename(profile.getOriginalFilename());
			
			uservo.setRealfilename(realFileName);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int insertCnt = 0;
		
		try {
			insertCnt = userService.registUser(uservo);
		} catch (Exception e) {
			insertCnt= 0;
		}
		
		 
		
		if(insertCnt==1) {
			return "redirect:/user/pagingUser";
		}else {
			model.addAttribute("user", uservo);
			return "user/registUser";
		}
		
		
	}
	
	
	@RequestMapping("userInfo")
	public String userInfo(String userid, Model model) {
		
		model.addAttribute("user",userService.selectUser(userid));
		
		
		return "tiles.user.user";
		//return "user/user";
	}
	
	///////////////////////////////////////////////////////
	@RequestMapping("pagingUserTiles")
	public String paingUserTiles(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		

		
		//tiles-definition에 설정한 name
		return "tiles.user.pagingUser";
	}
	///////////////////////////////////////////////////////
	
	
	@RequestMapping("pagingUser")
	public String paingUser(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		

		
		
		return "user/pagingUser";
	}
	
	
	
	
	
	
	
	
	
	public String paingUser2(PageVo pageVo,Model model) {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" ,pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAllAttributes(map);
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		

		int startPage = 1;
		int endPage = pagination;
		if((pageVo.getPage()-2)>2) {
			if(pageVo.getPage()==pagination||pageVo.getPage()==pagination-1||pageVo.getPage()==pagination-3) {
				startPage = pagination-4;
				
			}else{
				startPage = pageVo.getPage()-2;
			}
			if(startPage+4<pagination) {
				endPage = startPage+4;
			}
		}
		logger.debug("start : {}",startPage);
		if((pageVo.getPage()+2)<pagination-1) {
			if(pageVo.getPage()==1) {
				endPage = pageVo.getPage()+4;
			}else if(pageVo.getPage() == 2) {
				endPage = pageVo.getPage()+3;
			}else if(pageVo.getPage() == 4){
				endPage = pageVo.getPage()+1;
			}else {
				endPage = pageVo.getPage()+2;
			}
			if(endPage-4>pageVo.getPage()) {
				startPage = endPage-4;
			}
		}
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		

		
		
		return "user/pagingUser";
	}
	
	@RequestMapping("excelDownload")
	public String excelDownload(Model model) {
		List<String> header = new ArrayList<>();
		header.add("사용자아이디");
		header.add("사용자이름");
		header.add("사용자별명");
		header.add("등록일자");
		header.add("도로주소");
		header.add("상세주소");
		header.add("우편번호");
		
		model.addAttribute("header", header);
		model.addAttribute("data", userService.selectAllUser());
		
		return "userExcelDownloadView";
	}
	
	//localhost/user/profile
	@RequestMapping("profile")
	public void profile(HttpServletResponse resp, String userid, HttpServletRequest req) {

		resp.setContentType("image");
		
		// userid 파라미터를 이용하여
		// userService 객체를 동해 사용자의 사진 파일 이름을 획득
		// 파일 입출력을 통해 사진을 읽어들여 resp객체의 outputStream으로 응답 생성
		
		
		UserVo userVo = userService.selectUser(userid);
		
		String path = "";
		if(userVo.getRealfilename() == null) {
			path = req.getServletContext().getRealPath("/image/unknown.png");
		}else {
		
			path = userVo.getRealfilename();
		}
		logger.debug("path : {} ", path);
		
		try {
			
			FileInputStream fis = new FileInputStream(path);
			ServletOutputStream sos = resp.getOutputStream();
			
			byte[] buff = new byte[512];
			while(fis.read(buff)!=-1) {
				
				sos.write(buff);
				
			}
			
			
			fis.close();
			sos.flush();
			sos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	@RequestMapping("/profileDownload")
	public String fileDownload(String userid, Model model) {
		//1. 다운로드 파일의 경로 => realFilename
		//2. 다운로드시 보여줄 파일명 => filename
		//1, 2을 model에 넣어준다
		//userid 파라미터를 보낸다고 가정
		//파라미터를 이용해서 해당 사용자의 사진정보(realFilename, filename)를 조회
		
		UserVo userVo = userService.selectUser(userid);
		model.addAttribute("realFilename", userVo.getRealfilename());
		model.addAttribute("filename", userVo.getFilename());
		
		
		return "fd";
	}
	
	
}
