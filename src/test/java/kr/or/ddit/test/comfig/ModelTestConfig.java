package kr.or.ddit.test.comfig;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/kr/or/ddit/config/spring/root-context.xml",
		"classpath:/kr/or/ddit/config/spring/datasource-context.xml"})
public class ModelTestConfig{
	
	@Ignore
	@Test
	public void dummy() {
		
	}

}
