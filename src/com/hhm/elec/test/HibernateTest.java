package com.hhm.elec.test;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.hhm.elec.domain.ElecText;

public class HibernateTest {
	private static Configuration configuration=null;
	private static SessionFactory sFactory=null;
	
	static{
		configuration=new Configuration();
		
		configuration.configure("hibernate.cfg.xml");
		sFactory=configuration.buildSessionFactory();
	}
	
	public static void addElecText(ElecText elecText){
		//打开session
		Session session=sFactory.openSession();
		//开启事务提交器
		Transaction ts=session.beginTransaction();
		
		try {
			//保存对象
			session.save(elecText);
			//提交数据到数据库
			ts.commit();
		} catch (HibernateException e) {
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}finally{
			session.close();
			sFactory.close();
		}
		
	}
	
	public static void main(String[] args) {
		//准备数据
		ElecText elecText=new ElecText();
		elecText.setTextComment("text");
		elecText.setTextName("hhmText");
		elecText.setTextDate(new Date());
		
		addElecText(elecText);
	}
}
