#ELecMatter 待办事宜
create table elecMatter(
	matterId varchar(100) primary key,
	stationRunStatus text,#站点运行情况
	devRunStatus text,# 设备运行情况
	createDate date
);

#ElecUser用户表
create table elecUser(

   #基本数据
	userId varchar(100) primary key,
	account varchar(30) unique,#登录账号
	password varchar(100),#密码
	username varchar(30),#用户名
	gender varchar(20),#性别
	birthday date,#出生日期
	address varchar(255),#地址
	homeTel varchar(20),#家庭电话
	phone varchar(20),#手机号码
	email varchar(30),#电子邮箱
	isDuty varchar(30),#是否在职
	units varchar(30),#所属单位
	onDutyDate date,#入职日期
	offDutyDate date,#离职日期
	comment text ,#备注 
	
	#为了保证数据安全,问责的机制
	createUser varchar(100),#创建者userId
	createDate date,#创建日期
	lastUpdateUser varchar(100),#最后一次修改的userId,(其他修改记录使用历史表,或者记录日志)
	lastUpdateDate date,#最后一次修改日期
	
	#假删除,数据才是核心,没有了数据 , 为了数据的恒久性 
	isDelete boolean
);