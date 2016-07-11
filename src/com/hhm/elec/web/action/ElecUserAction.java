package com.hhm.elec.web.action;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.google.gson.Gson;
import com.hhm.elec.domain.ElecUser;
import com.hhm.elec.service.ElecUserService;
import com.hhm.elec.util.Conditions;
import com.hhm.elec.util.Conditions.Operator;
import com.hhm.elec.util.DataTablesPage;
import com.hhm.elec.util.Dictionary;
import com.hhm.elec.util.Md5Utils;
import com.hhm.elec.util.PoiUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ElecUserAction extends ActionSupport implements
		ModelDriven<ElecUser> {
	private ElecUser elecUser = new ElecUser();
	private ElecUserService elecUserService = null;
	//下面的三个数据是分页专用的。
	private String sEcho;
    private int iDisplayStart;//页面的初始索引
    private int iDisplayLength;//每页显示的条数
    
    
    private String checkNumber=null;//验证码
    private String rememberMe=null;//记住我（记住登陆账号与密码）
    
    private String username=null;//导出用户列表的时候使用
    
   
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getSEcho() {
		return sEcho;
	}

	public void setSEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public int getIDisplayStart() {
		return iDisplayStart;
	}

	public void setIDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getIDisplayLength() {
		return iDisplayLength;
	}

	public void setIDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public ElecUser getElecUser() {
		return elecUser;
	}

	public void setElecUser(ElecUser elecUser) {
		this.elecUser = elecUser;
	}

	public ElecUserService getElecUserService() {
		return elecUserService;
	}

	public void setElecUserService(ElecUserService elecUserService) {
		this.elecUserService = elecUserService;
	}

	/**
	 * 填充数据后返回
	 */
	public ElecUser getModel() {
		return elecUser;
	}
	
	/**
	 * 此方法用来导航到用户列表的首页
	 * @return
	 */
	public String userHome(){
		return "userHome";
	}
	
	/**
	 * 此方法用来导航到添加用户的页面
	 * @return
	 */
	public String userAdd(){
		return "userAdd";
	}

	/**
	 * 此方法用来加入一个用户
	 * @return
	 */
	public String addUser(){
		//对密码进行加密处理，利用MD5Utils
		String md5Password=Md5Utils.md5(elecUser.getPassword());
		
		elecUser.setPassword(md5Password);
		
		this.elecUserService.addUser(elecUser);
		return "addUserSuccess";
	}
	
	/**
	 * 检查登陆账号的唯一性
	 * @return
	 * @throws IOException 
	 */
	public String checkAccountUnique() throws IOException{
		
		String account=elecUser.getAccount();
		
		//用于修改用户登录账号时的数据校验
		String oldAccountValue=(String) ServletActionContext.getServletContext().getAttribute("accountValue");
		if(oldAccountValue!=null&&oldAccountValue.trim().length()!=0){
			if(account.equals(oldAccountValue)){
				ServletActionContext.getResponse().getWriter().write(true+"");
				//ServletActionContext.getServletContext().removeAttribute("accountValue");
				System.out.println(123456);
				return null;
			}
		}
		boolean isUnique=this.elecUserService.checkAccountUnique(account);
		
		try {
			System.out.println("the method is being used");
			
			ServletActionContext.getResponse().getWriter().write(isUnique+"");
		} catch (IOException e) {
			System.out.println("服务器错误");
			e.printStackTrace();
		}
		
		//因为不需要struts进行页面导航，所以此处一定要声明为null
		return null;
	}
	
	/**
	 * 用来动态获取数据
	 * @return
	 * @throws IOException 
	 */
	public String page() throws IOException{
		
		DataTablesPage<ElecUser> page=new DataTablesPage<ElecUser>();
		page.setSEcho(sEcho);
		page.setIDisplayLength(iDisplayLength);
		page.setIDisplayStart(iDisplayStart);
		
		
		//加入条件
		Conditions conditions=new Conditions();
		conditions.addConditions("username", elecUser.getUsername(), Operator.LIKE);
		
		this.elecUserService.getPageData(page,conditions);
		
		//将数据转换为Json格式
		Gson gson=new Gson();
		String jsonResult=gson.toJson(page);
		//反馈数据
		ServletActionContext.getResponse().getWriter().print(jsonResult);
		
		return null;
	}
	
	/**
	 * 删除用户
	 */
	public String delete(){
		this.elecUserService.delete(elecUser);
		return "delete";
	}
	
	/**
	 * 用于导航到更新用户的页面
	 * @return
	 */
	public String userEdit(){
		//获取用户
		ElecUser elecUser1=this.elecUserService.getElecUserByUserId(elecUser.getUserId());
		
		//将登陆账号设置到application中，用于修改时的账号唯一性校验
		ServletActionContext.getServletContext().setAttribute("accountValue", elecUser1.getAccount());
		
		//移除栈顶元素
		ActionContext.getContext().getValueStack().pop();
		//加入新的栈顶元素
		ActionContext.getContext().getValueStack().push(elecUser1);
		
		return "userEdit";
	}
	
	/**
	 * 更新用户
	 * @return
	 */
	public String updateUser(){
		//更新时，如果修改了密码，则对新密码进行加密，若没有，则忽略
		//从数据库中获取用户
		ElecUser oldElecUser=this.elecUserService.getElecUserByUserId(elecUser.getUserId());
		
		String oldPassWord=oldElecUser.getPassword();
		
		//旧密码与新密码进行比较
		if(!oldPassWord.equals(elecUser.getPassword())){
			//不相等，加密，新密码
			String newPassword=Md5Utils.md5(elecUser.getPassword());
			elecUser.setPassword(newPassword);
		}
		
		this.elecUserService.updateUser(elecUser);
		return "update";
	}
	
	/**
	 * 此方法用来登陆
	 * @return
	 */
	public String login(){
		//检查验证码是否正确
		//获取session中的验证码
		String tempCheckNumber=(String) ServletActionContext.getRequest().getSession().getAttribute("checkNumber");
		//判断与用户输入的验证码是否相等
		if(!checkNumber.equals(tempCheckNumber)){
			this.addActionError("验证码不正确");
			return "loginError";
		}
		
		
		//获取账号与密码
		String account=elecUser.getAccount();
		String password=elecUser.getPassword();
		
		boolean loginSucc=false;
		
		//数据有效性检查
		if(account==null||account.trim().length()==0){
			this.addActionError("登陆账号不能为空");
			return "loginError";
		}
		
		if(password==null||password.trim().length()==0){
			this.addActionError("登陆密码不能为空");
			return "loginError";
		}
		
		
		//先对用户输入的密码直接查询，查询不到，再加密查询
		//查询是否有该用户，先假设密码已经加密
		Conditions conditions=new Conditions();
		conditions.addConditions("account", account, Operator.EQUAL);
		conditions.addConditions("password",password, Operator.EQUAL);
		
		List<ElecUser> elecUsers=this.elecUserService.findByConditions(conditions);
		
		//不存在
		if(elecUsers!=null&&elecUsers.size()!=0){
			elecUser=elecUsers.get(0);
			loginSucc=true;
		}else{
			//再次查询，对密码进行加密后查询
			Conditions conditions1=new Conditions();
			conditions1.addConditions("account", account, Operator.EQUAL);
			conditions1.addConditions("password",Md5Utils.md5(password), Operator.EQUAL);
			
			List<ElecUser> elecUsers1=this.elecUserService.findByConditions(conditions1);
			if(elecUsers1!=null&&elecUsers1.size()!=0){
				loginSucc=true;
				elecUser=elecUsers1.get(0);
			}else{
				this.addActionError("登陆账号或者密码错误");
				return "loginError";
			}
		}
		
		
		
		if(loginSucc){
		//存在，设置到session中
		ServletActionContext.getRequest().getSession().setAttribute("elecUser", elecUser);
		
	
		//如果用户选择记住我（记住账号与密码）
		if("yes".equals(rememberMe)){
			//设置到cookie中
			
			//设置账号cookie
			Cookie accountCookie=new Cookie("account", account);
			//设置缓存有效时间
			accountCookie.setMaxAge(60*60*24*7);
			//设置可访问的路径，任意路径可访问
			accountCookie.setPath("/");

			//设置密码cookie
			Cookie passwordCookie=new Cookie("password", password);
			passwordCookie.setMaxAge(60*60*24*7);
			//设置可访问的路径，任意路径可访问
			passwordCookie.setPath("/");
			
			ServletActionContext.getResponse().addCookie(accountCookie);
			ServletActionContext.getResponse().addCookie(passwordCookie);

		}
		}
		return "loginSuccess";
	}
	
	
	/**
	 * 导出用户报表
	 * @return
	 * @throws Exception 
	 */
	public String exportUserExcel() throws Exception{
		Conditions conditions=new Conditions();
		conditions.addConditions("username", username, Operator.LIKE);
		
		//根据条件查询到用户
		List<ElecUser> userList=this.elecUserService.findByConditions(conditions);
		
//		int rowIndex=0;
//		short cellIndex=0;
//		
//		//准备报表对象
//		HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
//		
//		
//		//建立excel表
//		HSSFSheet hssfSheet=hssfWorkbook.createSheet();
//		//设置表格名
//		hssfWorkbook.setSheetName(0, "表格1", HSSFWorkbook.ENCODING_UTF_16);
//		
//		//建立行对象
//		HSSFRow hssfRow=hssfSheet.createRow(rowIndex);
//		
//		//建立单元格对象
//		HSSFCell hssfCell0=hssfRow.createCell(cellIndex);
//		//设置单元格被编码
//		hssfCell0.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cellIndex++;
//		hssfCell0.setCellValue("用户姓名");
//		
//		HSSFCell hssfCell1=hssfRow.createCell(cellIndex);
//		//设置单元格被编码
//		hssfCell1.setEncoding(HSSFCell.ENCODING_UTF_16);
//		cellIndex++;
//		hssfCell1.setCellValue("用户年龄");
//		
//		HSSFCell hssfCell2=hssfRow.createCell(cellIndex);
//		//设置单元格被编码
//		hssfCell2.setEncoding(HSSFCell.ENCODING_UTF_16);
//		hssfCell2.setCellValue("性别");
//		
//		rowIndex++;
//		
//		for (ElecUser elecUser : userList) {
//			//重置单元格索引
//			cellIndex=0;
//			
//			//创建新行
//			HSSFRow hRow=hssfSheet.createRow(rowIndex);
//			
//			//建立单元格对象
//			HSSFCell hCell0=hRow.createCell(cellIndex);
//			//设置单元格被编码
//			hCell0.setEncoding(HSSFCell.ENCODING_UTF_16);
//			cellIndex++;
//			hCell0.setCellValue(elecUser.getUsername());
//			
//			HSSFCell hCell1=hRow.createCell(cellIndex);
//			//设置单元格被编码
//			hCell1.setEncoding(HSSFCell.ENCODING_UTF_16);
//			cellIndex++;
//			hCell1.setCellValue(elecUser.getPhone());
//			
//			HSSFCell hCell2=hRow.createCell(cellIndex);
//			//设置单元格被编码
//			hCell2.setEncoding(HSSFCell.ENCODING_UTF_16);
//			hCell2.setCellValue(Dictionary.getDictionary().getItemMap().get(elecUser.getGender()));
//			
//			rowIndex++;
//			
//		}
		int count = 1;
		// 准备数据
		String[] titleRowData = new String[] { "用户的登录账号", "用户名", "性别" };

		if (userList != null) {
			count += userList.size();
		}
		String[][] data = new String[count][];

		data[0] = titleRowData;

		if (userList != null) {
			for (int i = 0; i < userList.size(); i++) {
				ElecUser user = userList.get(i);
				String[] rowData = new String[3];
				rowData[0] = user.getAccount();
				rowData[1] = user.getUsername();
				rowData[2] = Dictionary.getDictionary().getItemMap().get(user.getGender());

				data[i + 1] = rowData;
			}
		}

		ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");
		ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息.xls", "utf-8"));
		// 把excel对象的数据写入到输出流()
		OutputStream out = ServletActionContext.getResponse().getOutputStream();

//		 hssfWorkbook.write(out);
//		 out.flush();
		PoiUtil.createTable(data, out);
		
		return null;
	}
	
	
	/**
	 * 导出用户报表，扇形图
	 * @return
	 * @throws IOException 
	 */
	public String exportChart() throws IOException{
		//获取全部的用户数据
		Conditions conditions=new Conditions();
		conditions.addConditions("isDelete", false, Operator.EQUAL);
		List<ElecUser> userList=this.elecUserService.findByConditions(conditions);
		
		//设置数据格式
		Map<String,Integer> dataMap=new HashMap<String,Integer>();
		
		for (ElecUser elecUser : userList) {
			String unit=elecUser.getUnits();
			Integer count=dataMap.get(unit);
			
			if(count==null){
				count=0;
			}
			
			count++;
			//加入数据
			dataMap.put(unit, count);
						
		}
		
		
		//画图形
		
		//准备数据
		DefaultPieDataset dataset=new DefaultPieDataset();
		
		Set<String> units=dataMap.keySet();
		Iterator<String> it=units.iterator();
		
		while(it.hasNext()){
			String unitName=it.next();
			int count=dataMap.get(unitName);
			
			//从数据字典中获取中文的单位名
			unitName=Dictionary.getDictionary().getItemMap().get(unitName);
			if(unitName==null){
				unitName="神秘用户";
			}
			
			dataset.setValue(unitName, count);
			
		}
		
		//准备图形对象
		JFreeChart chart=ChartFactory.createPieChart3D(
				"各单位用户人数统计",
				dataset,
				true,//是否显示图例
				false,//是否显示图例工具
				false
				);
		
		
		//设置样式
		//设置样式
		Font titleFont=new Font("宋体", Font.BOLD, 18);
		Font labelFont=new Font("宋体", Font.BOLD, 15);
		
		//设置标题字体
		TextTitle textTitle=chart.getTitle();
		textTitle.setFont(titleFont);
		
		//设置标签字体
		LegendTitle legendTitle=chart.getLegend();//获取图例
		legendTitle.setItemFont(labelFont);
		
		//设置绘图区域的字体
		PiePlot3D plot=(PiePlot3D) chart.getPlot();
		plot.setLabelFont(labelFont);
		
		// 设置标签样式
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {1}人{2}"));
		
		
		
		
		//保存为图片形式
		//设置文件路径
		String rootPath=ServletActionContext.getServletContext().getRealPath("/");
		String path=rootPath+"/WEB-INF/attach/chart";
		
		File dir=new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		//设置文件名
		String fileName=UUID.randomUUID().toString()+".png";
		ServletActionContext.getRequest().setAttribute("fileName", fileName);
		
		File file=new File(path,fileName);
		//保存文件
		ChartUtilities.saveChartAsJPEG(file, chart, 600, 400);
		return "exportChart";
	}
}
