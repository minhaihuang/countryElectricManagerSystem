package com.hhm.elec.util;


import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 封装报表技术
 * 
 * @author 黄帅哥
 * 
 */
public class PoiUtil {

	public static void createTable(String[][] data, OutputStream out) throws Exception {
		// 准备报表工作记事本
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		// 创建表
		HSSFSheet hssfSheet = hssfWorkbook.createSheet();

		// 获取头信息
		String[] titleData = data[0];

		// 设置前几列的宽度
		for (int i = 0; i < titleData.length; i++) {
			hssfSheet.setColumnWidth((short) i, (short)5000);
		}
		
		int rowIndex=0;
		short cellIndex=0;
		//第一行
		HSSFRow hssfRow=hssfSheet.createRow(rowIndex);
		
		//设置字体的样式
		HSSFCellStyle titleCellStyle=hssfWorkbook.createCellStyle();
		HSSFFont hssfFont=hssfWorkbook.createFont();
		hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗显示
		titleCellStyle.setFont(hssfFont);
		
		//设置头行信息的样式
		for(int i=0;i<titleData.length;i++){
			HSSFCell hssfCell=hssfRow.createCell(cellIndex);//创建单元格
			
			hssfCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置编码
			hssfCell.setCellValue(titleData[i]);//设置值
			hssfCell.setCellStyle(titleCellStyle);//设置样式
			cellIndex++;
		}
		
		rowIndex++;//行增加
		
		//获取真正的数据，将他们设置进入表格
		for(int i=1;i<data.length;i++){
			//创建行
			HSSFRow hRow=hssfSheet.createRow(rowIndex);
			rowIndex++;//行增加
			
			String[] trueData=data[i];
			
			//单元格索引设置为0
			cellIndex=0;
			for (String string : trueData) {
				//创建单元格
				HSSFCell hCell=hRow.createCell(cellIndex);
				hCell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);//设置编码
				hCell.setCellValue(string);//设置值
				cellIndex++;
			}
		}

		//把表格对象加入输出流
		hssfWorkbook.write(out);
		
		out.flush();
	}
}
