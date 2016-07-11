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


#角色表 elecrole
create table elecRole(
	roleId varchar(100) primary key,
	rolename varchar(50) unique#角色名称
);


#权限表elecFunction  
create table elecFunction(
	functionId varchar(100) primary key,
	path varchar(255) unique,#请求的action的路径
	functionName varchar(50) not null,
	groups varchar(50)#分组
);

#角色权限关联表 elecRoleFunction
create table elecRoleFunction(
    roleId varchar(100) ,
    functionId varchar(100),
	foreign key(roleId) references elecRole(roleId)  on delete cascade on update cascade,
	foreign key(functionId) references elecFunction(functionId)  on delete cascade on update cascade
);

#用户角色关联表elecUserRole
create table elecUserRole(
    userId varchar(100) ,
    roleId varchar(100) ,
    foreign key(userId) references elecUser(userId)  on delete cascade on update cascade,
    foreign key(roleId) references elecRole(roleId)  on delete cascade on update cascade
);

#申请模板表
create table elecApplyTemplate(
	templateId varchar(100) primary key,  #模板id
	filename varchar(100), #模板文件名
	path text ,#模板文件真实路径
	processDefinitionKey varchar(100)#关联的流程定义的key
);

#申请信息表
create table elecApply(
	applyId varchar(100) primary key,
	userId varchar(100), #申请人的信息
	username varchar(50),
	account varchar(50) , 
	processDefinitionId  varchar(100) ,#记录提交申请时特定版本的流程定义 , 供审批时使用
	processDefinitionKey varchar(50),
	applyTime date,
	applyStatus varchar(50) ,   #申请状态
	filename varchar(100),#申请文件的信息
	path text
);
