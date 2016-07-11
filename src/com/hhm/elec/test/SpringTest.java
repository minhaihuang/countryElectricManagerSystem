package com.hhm.elec.test;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.hhm.elec.domain.ElecText;
import com.hhm.elec.service.ElecTextService;

/**
 * 测试spring框架
 * 
 * @author 黄帅哥
 * 
 */
public class SpringTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");

		//ElecText elecText = (ElecText) context.getBean("elecText");
		//HibernateTemplate hibernateTemplate=(HibernateTemplate) context.getBean("hibernateTemplate");
		ElecTextService elecTextService=(ElecTextService) context.getBean("elecTextService");
		
		// 准备数据
//		ElecText elecText = new ElecText();
//		elecText.setTextComment("text2");
//		elecText.setTextName("hhmText2");
//		elecText.setTextDate(new Date());
//
//		elecTextService.add(elecText);
//		ElecText elecText=elecTextService.getById("8a8cc7ce53c582a30153c582a5720001");
//		
//		System.out.println(elecText.getTextComment());
	

	}

	public static void testSpringAndHibernate(ElecText elecText) {
		
		
		
	}
}
