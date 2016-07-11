package com.hhm.elec.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.struts2.config.ServletContextSingleton;

import com.hhm.elec.util.Dictionary;

/**
 * 这是数据字典类Dictionary的监听器
 * 
 * @author 黄帅哥
 * 
 */
public class DictionaryListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//初始化字典对象，并且设置到ServletContext中
		Dictionary dictionary=Dictionary.getDictionary();
		
		servletContextEvent.getServletContext().setAttribute("dictionary", dictionary);
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
