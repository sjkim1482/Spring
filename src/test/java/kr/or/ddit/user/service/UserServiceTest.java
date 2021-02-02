package kr.or.ddit.user.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.test.comfig.ModelTestConfig;
import kr.or.ddit.user.model.UserVo;



public class UserServiceTest extends ModelTestConfig{

	
	@Resource(name="userService")
	private UserService userService;

	@Test
	public void UserServiceTest() {
		/***Given***/
		String userid = "brown";

		/***When***/
		UserVo userVo = userService.selectUser(userid);

		/***Then***/
		assertEquals("브라운", userVo.getUsernm());
		
	}
	
	@Test
	public void selectAllUserTest() {
		/***Given***/
		/***When***/
	 	List<UserVo> userVoList = userService.selectAllUser();

		/***Then***/
	 	assertEquals(78, userVoList.size());
	}
	
	@Test
	public void selectPagingUserTest() {
		/***Given***/
		PageVo pageVo = new PageVo(1, 5);

		/***When***/
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		int cnt = (int)map.get("userCnt");
		
		/***Then***/
	 	assertEquals(5, userList.size());
	 	assertEquals(77, cnt);
	}
	
	@Test
	public void selectAllUserCountTest() {
		/***Given***/
	

		/***When***/
	 	int userCnt = userService.selectAllUserCount();

		/***Then***/
	 	assertEquals(78, userCnt);
	}
	
	@Test
	public void modifyUserTest() {
		/***Given***/
		UserVo uservo = new UserVo();
		uservo.setUserid("a005");
		uservo.setUsernm("수정이름11");
		uservo.setPass("123356711");
		uservo.setAlias("수정별명");
		uservo.setAddr1("수정주소1");
		uservo.setAddr2("수정주소2");
		uservo.setZipcode("1234");
		uservo.setFilename("수정파일이름");
		uservo.setRealfilename("찐수정파일이름");
		uservo.setReg_dt(new Date());

		/***When***/
	 	int cnt = userService.modifyUser(uservo);

		/***Then***/
	 	assertEquals(1, cnt);
	}
	
	@Test
	public void registUserTest() {
		/***Given***/
		UserVo uservo = new UserVo();
		uservo.setUserid("a006");
		uservo.setUsernm("생성이름");
		uservo.setPass("1233567");
		uservo.setAlias("생성별명");
		uservo.setAddr1("생성주소1");
		uservo.setAddr2("생성주소2");
		uservo.setZipcode("1234");
		uservo.setFilename("생성파일이름");
		uservo.setRealfilename("찐생성파일이름");
		uservo.setReg_dt(new Date());

		/***When***/
	 	int cnt = userService.registUser(uservo);

		/***Then***/
	 	assertEquals(1, cnt);
	}
	
	@Test
	public void deleteUserTest() {
		/***Given***/
		String userid = "a006";

		/***When***/
		int cnt = userService.deleteUser(userid);

		/***Then***/
	 	assertEquals(1, cnt);
	}

}
