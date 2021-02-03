package kr.or.ddit.mvc.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@RequestMapping("ajax")
@Controller
public class AjaxController {
	//������Ʈ���θ޼��尡 ���� �޼��尡 �����ϱ� ���� �긦 ���� �����ϰ� �갡 ��ȯ�ϴ� ���� �𵨰�ü�� �ڵ����� �־���(Model)
	@ModelAttribute(name = "")
	public List<String> rangers(){
		List<String> rangers = new ArrayList<>();
		rangers.add("brown");
		rangers.add("cony");
		rangers.add("james");
		rangers.add("sally");
		rangers.add("moon");
		
		return rangers;
	}
	
	
	//json ���� �׽�Ʈ
	//���� ���� ��
	//localhost/ajax/jsonView
	@RequestMapping("jsonView")
	public String jsonView() {
		List<String> rangers = new ArrayList<>();
	
		//���ø����̼� ���ؽ�Ʈ���� ����� <bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView"> ������ id�� �����ָ� ��ȯ
		return "jsonView";
		
	}
	
	
	@RequestMapping("jsonViewViewObj")
	public View jsonViewViewObj() {
		return new MappingJackson2JsonView();
	}
	
	//������ �� �Ⱦ�
	@RequestMapping("jsonViewMav")
	public ModelAndView jsonViewMav() {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("jsonView");
		
		return mav;
	}
	
	
	
	
}
