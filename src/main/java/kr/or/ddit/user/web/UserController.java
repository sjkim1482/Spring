package kr.or.ddit.user.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserService;

@RequestMapping("user")
@Controller
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	@RequestMapping(path = "allUser",method = RequestMethod.GET)
	public String allUser(Model model) {
		
		List<UserVo> userList = userService.selectAllUser();
		
		
		model.addAttribute("userList", userList);
		return "/user/allUser";
	}
	
	
}
