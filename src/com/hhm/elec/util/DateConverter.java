package com.hhm.elec.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
/**
 * 这是日期的转换类
 * @author 黄帅哥
 *
 */
public class DateConverter extends DefaultTypeConverter {
	//定义各种日期格式
	private DateFormat[] dataFormats={new SimpleDateFormat("yyyy-MM-dd"),
			//new SimpleDateFormat("dd-MM-yyyy"),//日期转换失败的罪魁祸首
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")};
	
	@Override
	public Object convertValue(Map<String, Object> context, Object value,
			Class toType) {
		if(toType==Date.class){//字符串转换为日期
			Date date=null;
			//获得字符串
			String[] strs=(String[])value;
			String dateStr=strs[0];
			
			try {
				date=dataFormats[0].parse(dateStr);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			//没有符合的格式，则抛出异常
			if (date == null) {
				throw new XWorkException("Could not parse date : " + dateStr);
			}
			return date;
		}else if(toType==String.class){//日期转换为字符串
			return dataFormats[1].format(value);
		}
		return null;
	}
}
