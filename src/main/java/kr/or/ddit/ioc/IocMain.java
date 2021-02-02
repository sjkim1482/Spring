package kr.or.ddit.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.repository.UserDao;
import kr.or.ddit.user.repository.UserDaoImpl;
import kr.or.ddit.user.service.UserService;

public class IocMain {

	private static final Logger logger = LoggerFactory.getLogger(IocMain.class);
	
	public static void main(String[] args) {
		//1. ������ ���� ������ �̿��Ͽ� ������ �����̳ʸ� ���� (kr/or/ddit/ioc/ioc.xml)
		//		������ �����̳� Ÿ�� : ApplicationContext
		//2. ������ �����̳ʿ��� ������� ������ ��(��ü)�� ��û
		//		DL(Dependency Lookup) : ������ �����̳ʿ��� ������ ���� ��û�ϴ� ����
		//		IOC, DL / DI
		//3. ������ �����̳ʿ��� �����ǰ� �ִ� ���� �� ����� ������ Ȯ��
//			d:\\upload file;
//				class classpath
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/kr/or/ddit/ioc/ioc.xml");
		
		//context: �����̳�
		UserDao userDao = (UserDao)context.getBean("userDao");
		
		UserVo userVo = userDao.selectUser("brown");
		
		logger.debug("userVo : {}", userVo);
		
		//������ �����̳ʷ� ���� userService ������ ���� DL�� ���� ������
		//getUser �޼ҵ带 call, ��ȯ�� ��(userVo)�� logget�� ���� ���
		
		UserService userService = (UserService)context.getBean("userService");
		UserVo userVo1 = userService.selectUser("brown");
		
		logger.debug("userVo : {}", userVo1);
		
		for(String beanName : context.getBeanDefinitionNames()) {
			logger.debug("beanName : {}", beanName);
		}

		
	}

}
