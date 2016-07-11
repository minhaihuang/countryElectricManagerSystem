package com.hhm.elec.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hhm.elec.dao.IDao;
import com.hhm.elec.dao.IElecTextDao;
import com.hhm.elec.dao.impl.DaoImpl;
import com.hhm.elec.domain.ElecText;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.Operator;

/**
 * 测试Dao层中封装的方法
 * 
 * @author 黄帅哥
 * 
 */
public class DaoTest {

	// 读取配置文件，获取bean
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

	private IElecTextDao elecTextDao = (IElecTextDao) context
			.getBean("elecTextDao");

	public void addOrUpdate() {
		ElecText elecText = new ElecText();
		elecText.setTextName("text1");

		elecTextDao.addOrUpdate(elecText);
	}

	// 测试加入一个容器中的全部bean
	public void addOrUpdateAll() {

		List<ElecText> elecTexts = new ArrayList<ElecText>();

		ElecText elecText1 = new ElecText();
		elecText1.setTextName("text2");

		ElecText elecText2 = new ElecText();
		elecText2.setTextName("text3");

		elecTexts.add(elecText1);
		elecTexts.add(elecText2);

		this.elecTextDao.addOrUpdateAll(elecTexts);
	}

	// 测试根据对象删除
	public void deteteBean() {
		ElecText elecText1 = new ElecText();
		elecText1.setTextId("8a8cc7ce53cfef1a0153cfef206a0005");
		elecText1.setTextName("text2");

		this.elecTextDao.deteteBean(elecText1);
	}

	// 测试根据id删除
	public void deleteBeanById() {
		this.elecTextDao.deleteBeanById("8a8cc7ce53cfef1a0153cfef206a0006");
	}

	// 测试根据容器删除
	public void deleteBeansAll() {
		List<ElecText> elecTexts = new ArrayList<ElecText>();

		ElecText elecText1 = new ElecText();
		elecText1.setTextId("8a8cc7ce53cfea9f0153cfeaa2c40001");

		ElecText elecText2 = new ElecText();
		elecText2.setTextId("8a8cc7ce53cfef1a0153cfef1f6d0003");

		elecTexts.add(elecText1);
		elecTexts.add(elecText2);

		elecTexts.add(elecText1);
		elecTexts.add(elecText2);

		this.elecTextDao.deleteBeansAll(elecTexts);
	}

	// 测试更新的方法

	public void testUpdate() {
		ElecText elecText = new ElecText();
		elecText.setTextId("8a8cc7ce53cff2b90153cff2bd4e0001");
		elecText.setTextName("text5");

		this.elecTextDao.addOrUpdate(elecText);

	}

	// 测试根据id来获取对象
	public void getById() {
		ElecText elecText = this.elecTextDao
				.getById("8a8cc7ce53cff2b90153cff2bd4e0001");
		System.out.println(elecText.getTextName());
	}

	public void getByConditions() {
		Conditions conditions = new Conditions();
		conditions.addConditions("textId", "8a8cc7ce53cff", Operator.LIKE);
		// conditions.addConditions("textName", "text4", Operator.EQUAL);
		conditions.addOrderBy("textName", true);
		conditions.addOrderBy("textComment", true);
		List<ElecText> elecTexts = elecTextDao.geyByConditions(conditions);

		for (ElecText elecText : elecTexts) {
			System.out.println(elecText.getTextName() + "--"
					+ elecText.getTextComment());
		}
	}

	public void getAll() {
		List<ElecText> elecTexts = elecTextDao.getAll();

		for (ElecText elecText : elecTexts) {
			System.out.println(elecText.getTextName() + "--"
					+ elecText.getTextComment());
		}
	}

	public static void main(String[] args) {
		DaoTest daoTest = new DaoTest();
		//daoTest.getByConditions();
		daoTest.getAll();
	}
}
