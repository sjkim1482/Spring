package kr.or.ddit.hello;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.test.comfig.WebTestConfig;
import kr.or.ddit.user.model.UserVo;

/*
 * java - gui swing, awt, java fx, swt
 */

@ContextConfiguration(locations = {"classpath:/kr/or/ddit/config/spring/application-context.xml",
								   "classpath:/kr/or/ddit/config/spring/root-context.xml"})
@WebAppConfiguration		//스프링 환경을 Web기반의 appliction Context로 생성
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloControllerTest extends WebTestConfig {
	
	//설정파일이 다 들어왔는지 체크
	//@Resource(name="helloController")
	// 스프링빈중에 대입 가능한 타입의 스프링 빈을 주입한다.
	// 만약 동일한 타입의 스프링 빈이 여러개 있을 경우 @Qulifier 어노테이션을 통해
	// 특정 스프링 빈의 이름을 지칭할 수 있다.
	//	==> @Resource 어노테이션 하나를 사용 했을 때
	/*
	 * @Autowired private HelloController helloController;
	 */
	
	
	
	
	//localhost/hello/view
	@Test
	public void wiewTest() throws Exception {
		//perform() : 무언가를 실행을 한다
		//status().isOk() : 200
		//view().name("hello") : viewname 반환
		//model().attributeExists("userVo") : 들어있는 속성 반환
		MvcResult mvcResult = mockMvc.perform(get("/hello/view")).andExpect(status().isOk())
										   .andExpect(view().name("hello"))
										   .andExpect(model().attributeExists("userVo"))
										   .andDo(print())
										   .andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		assertEquals("hello", mav.getViewName());
		UserVo userVo = (UserVo)mav.getModel().get("userVo");
		
		assertEquals("브라운", userVo.getUsernm());
		
		
	}
	
	@Test
	public void pathVariableTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/hello/path/cony"))
									   .andExpect(status().isOk())
									   .andExpect(model().attributeExists("subpath"))
									   .andDo(print())
									   .andReturn();
	}
	
	
	
	
}














