package kr.or.ddit.user.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;

public interface UserService {
	
	
	
	UserVo selectUser(String userid);
	
	
	//��ü ����� ���� ��ȸ
	List<UserVo> selectAllUser();
	
	// ����� ����¡ ��ȸ
	Map<String, Object> selectPagingUser(PageVo vo);
	
	//���� ��ü �� ��ȸ
	int selectAllUserCount();
	
	//����� ���� ����
	int modifyUser(UserVo userVo);
	
	//����� ���� ���
	int registUser(UserVo userVo);

	//����� ���� ����
	int deleteUser(String userid);
	
	
}
