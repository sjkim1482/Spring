package kr.or.ddit.user.web;

import static org.junit.Assert.*;
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


@ContextConfiguration(locations = {"classpath:/kr/or/ddit/config/spring/application-context.xml",
"classpath:/kr/or/ddit/config/spring/root-context.xml"})
@WebAppConfiguration		//������ ȯ���� Web����� appliction Context�� ����
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest extends WebTestConfig{

	@Test
	public void wiewTest() throws Exception {
		//perform() : ���𰡸� ������ �Ѵ�
		//status().isOk() : 200
		//view().name("hello") : viewname ��ȯ
		//model().attributeExists("userVo") : ����ִ� �Ӽ� ��ȯ
		MvcResult mvcResult = mockMvc.perform(get("/user/allUser")).andExpect(status().isOk())
										   .andExpect(view().name("/user/allUser"))
										   .andDo(print())
										   .andReturn();
		
		ModelAndView mav = mvcResult.getModelAndView();
		
		assertEquals("/user/allUser", mav.getViewName());
	
		
		
	}

}
