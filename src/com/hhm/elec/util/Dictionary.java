package com.hhm.elec.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 这是数据字典的单例类
 * 
 * @author 黄帅哥
 * 
 */
public class Dictionary {

	/*
	 * 单例模式的三要素： 1，有一个类的静态对象 2，构造器私有化，防止外部新建单例类的对象
	 * 3，单例类内部提供一个静态方法，该方法返回一个该类的对象，外部只能通过该方法获取对象，并且只能获取一次
	 */

	private static Dictionary dictionary = null;
	/*
	 * 访问容器的方式：
	 * 1，可以通过el表达式：${request.dictionary.xxxMap},
	 * 2，可以通过ongl表达式。
	 * 3，通过struts标签
	 */
	
	// 生产三个访问数据字典数据的容器

	// 1,Map<groupKey,Map<itemKey,itemValue>>,包含所有的字典的数据，并且数据是分组的
	private Map<String, Map<String, Object>> dictionaryMap = null;

	// 2，Map<groupKey,groupValue>，只包含group标签的key和value的属性值
	private Map<String, String> groupMap = null;

	// 3,Map<itemKey,itemValue>，存储所有item标签的key可value
	private Map<String, String> itemMap = null;
	
	//定义锁
	private Object lock=new Object();

	// 构造器私有化
	private Dictionary() {
	}

	/**
	 * 对外的静态方法,为了防止线程冲突，需要加上同步
	 * 
	 * @return
	 */
	public static synchronized Dictionary getDictionary() {
		if (dictionary == null) {
			dictionary = new Dictionary();
			dictionary.init();
		}

		return dictionary;
	}

	/**
	 * 初始化数据字典，获取数据
	 */
	private void init() {
		System.out.println("数据字典的初始化方法开始执行了............");
		// 1,Map<groupKey,Map<itemKey,itemValue>>,包含所有的字典的数据，并且数据是分组的
		Map<String, Map<String, Object>> dictionaryMap2 =new LinkedHashMap<String, Map<String,Object>>();

		// 2，Map<groupKey,groupValue>，只包含group标签的key和value的属性值
		Map<String, String> groupMap2=new LinkedHashMap<String, String>();

		// 3,Map<itemKey,itemValue>，存储所有item标签的key可value
		Map<String, String> itemMap2=new LinkedHashMap<String, String>();
		
		//文件路径
		String filePath=Dictionary.class.getClassLoader().getResource("Dictionary.xml").getFile();
		//利用dom4j技术解析数据字典文件
		SAXReader reader=new SAXReader();
		try {
			//解析文件
			Document document=reader.read(filePath);
			//获取根节点
			Element rootElement=document.getRootElement();
			
			//获取根节点下的直接关联的子节点
			List<Element> groupElements=rootElement.elements();
			
			//遍历
			for (Element groupElement : groupElements) {
				//获取所有的key属性和value属性
				String key=groupElement.attributeValue("key");
				String value=groupElement.attributeValue("value");
				
				//有效性检查
				if(key==null||key.trim().length()==0){
					throw new RuntimeException("数据字典数据不能为空");
				}
				if(value==null||value.trim().length()==0){
					throw new RuntimeException("数据字典数据不能为空");
				}
				
				//加入groupMap2
				groupMap2.put(key, value);
				
				//临时容器
				Map<String, Object> tempMap =new LinkedHashMap<String, Object>();

				
				//获得groupElement下的所有item元素
				List<Element> itemElements=groupElement.elements();
				for (Element itemElement : itemElements) {
					//获取所有的key属性和value属性
					String key1=itemElement.attributeValue("key");
					String value1=itemElement.attributeValue("value");
					
					//有效性检查
					if(key1==null||key1.trim().length()==0){
						throw new RuntimeException("数据字典数据不能为空");
					}
					if(value1==null||value1.trim().length()==0){
						throw new RuntimeException("数据字典数据不能为空");
					}
					
					//加入临时容器
					tempMap.put(key1, (Object)value1);
					//加入itemMap2
					itemMap2.put(key1, value1);
				}
				//加入dictionaryMap2
				dictionaryMap2.put(key, tempMap);
			}
			
			//利用锁机制赋值，目的就是为了让这三个容器同时更新，因为已经在获取Dictionary对象的时候已经有线程同步了，在这里可不加
			synchronized (lock) {
				dictionaryMap=dictionaryMap2;
				itemMap=itemMap2;
				groupMap=groupMap2;
			}
		} catch (Exception e) {
			throw new RuntimeException("数据字典初始化失败"+e.getMessage());
		}
		
	}

	/**
	 * 重新加载参数的方法，为了防止线程冲突，需要加上同步
	 */
	public static synchronized void reload() {
		if (dictionary == null) {
			getDictionary();// 若对象还不存在，则去新建对象，并且加载参数
		} else {// 如果对象已经存在，则直接去调用初始化方法，重新加载参数

		}
	}

	public Map<String, Map<String, Object>> getDictionaryMap() {
		return dictionaryMap;
	}

	public void setDictionaryMap(Map<String, Map<String, Object>> dictionaryMap) {
		this.dictionaryMap = dictionaryMap;
	}

	public Map<String, String> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<String, String> groupMap) {
		this.groupMap = groupMap;
	}

	public Map<String, String> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, String> itemMap) {
		this.itemMap = itemMap;
	}

}
