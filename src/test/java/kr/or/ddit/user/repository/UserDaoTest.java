package kr.or.ddit.user.repository;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.test.comfig.ModelTestConfig;
import kr.or.ddit.user.model.UserVo;

//eclipse / maven
//������ ȯ�濡�� junit �ڵ带 ���� ==> junit �ڵ嵵 ������ ������ ���

public class UserDaoTest extends ModelTestConfig{

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Test
	public void getUserTest() {
		/***Given***/
		String userid = "brown";

		/***When***/
	 	UserVo userVo = userDao.selectUser(userid);

		/***Then***/
	 	assertEquals("����", userVo.getUsernm());
	}
	
	
	@Test
	public void selectAllUserTest() {
		/***Given***/
		/***When***/
	 	List<UserVo> userVoList = userDao.selectAllUser();

		/***Then***/
	 	assertEquals(78, userVoList.size());
	}
	
	@Test
	public void selectPagingUserTest() {
		/***Given***/
		PageVo pageVo = new PageVo(1, 5);

		/***When***/
		List<UserVo> userVoList = userDao.selectPagingUser(pageVo);

		/***Then***/
	 	assertEquals(5, userVoList.size());
	}
	
	@Test
	public void selectAllUserCountTest() {
		/***Given***/
	

		/***When***/
	 	int userCnt = userDao.selectAllUserCount();

		/***Then***/
	 	assertEquals(78, userCnt);
	}
	
	@Test
	public void modifyUserTest() {
		/***Given***/
		UserVo uservo = new UserVo();
		uservo.setUserid("a005");
		uservo.setUsernm("�����̸�");
		uservo.setPass("1233567");
		uservo.setAlias("��������");
		uservo.setAddr1("�����ּ�1");
		uservo.setAddr2("�����ּ�2");
		uservo.setZipcode("1234");
		uservo.setFilename("���������̸�");
		uservo.setRealfilename("����������̸�");
		uservo.setReg_dt(new Date());

		/***When***/
	 	int cnt = userDao.modifyUser(uservo);

		/***Then***/
	 	assertEquals(1, cnt);
	}
	
	@Test
	public void registUserTest() {
		/***Given***/
		UserVo uservo = new UserVo();
		uservo.setUserid("a006");
		uservo.setUsernm("�����̸�");
		uservo.setPass("1233567");
		uservo.setAlias("��������");
		uservo.setAddr1("�����ּ�1");
		uservo.setAddr2("�����ּ�2");
		uservo.setZipcode("1234");
		uservo.setFilename("���������̸�");
		uservo.setRealfilename("����������̸�");
		uservo.setReg_dt(new Date());

		/***When***/
	 	int cnt = userDao.registUser(uservo);

		/***Then***/
	 	assertEquals(1, cnt);
	}
	
	@Test
	public void deleteUserTest() {
		/***Given***/
		String userid = "a006";

		/***When***/
		int cnt = userDao.deleteUser(userid);

		/***Then***/
	 	assertEquals(1, cnt);
	}

}
