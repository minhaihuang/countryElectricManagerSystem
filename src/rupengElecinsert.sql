insert into elecUser(userId,account,password,username,gender,units,isDuty,isDelete) values('00001','zhangsan1','202cb962ac59075b964b07152d234b70','张三1','gender_male','units_shanghai1','isDuty_on',false);
insert into elecUser(userId,account,password,username,gender,units,isDuty,isDelete) values('00002','zhangsan2','202cb962ac59075b964b07152d234b70','张三2','gender_male','units_shanghai1','isDuty_on',false);
insert into elecUser(userId,account,password,username,gender,units,isDuty,isDelete) values('00003','zhangsan3','202cb962ac59075b964b07152d234b70','张三3','gender_male','units_shanghai1','isDuty_on',false);
insert into elecUser(userId,account,password,username,gender,units,isDuty,isDelete) values('00004','lisi1','202cb962ac59075b964b07152d234b70','李四1','gender_male','units_shanghai1','isDuty_on',false);
insert into elecUser(userId,account,password,username,gender,units,isDuty,isDelete) values('00005','lisi2','202cb962ac59075b964b07152d234b70','李四2','gender_male','units_shanghai1','isDuty_on',false);
insert into elecUser(userId,account,password,username,gender,units,isDuty,isDelete) values('00006','wangwu1','202cb962ac59075b964b07152d234b70','王五1','gender_male','units_shanghai1','isDuty_on',false);


insert into elecRole(roleId,rolename) values('001','超级管理员');
insert into elecRole(roleId,rolename) values('002','系统管理员');
insert into elecRole(roleId,rolename) values('003','会计');
insert into elecRole(roleId,rolename) values('004','经理');
insert into elecRole(roleId,rolename) values('005','项目组长');
insert into elecRole(roleId,rolename) values('006','普通员工');


insert into elecFunction(functionId,path,functionName,groups) values('0001','userAction_add','添加用户','系统管理');
insert into elecFunction(functionId,path,functionName,groups) values('0002','userAction_update','修改用户','系统管理');
insert into elecFunction(functionId,path,functionName,groups) values('0003','userAction_userEdit','查看用户详情','系统管理');
insert into elecFunction(functionId,path,functionName,groups) values('0004','userAction_delete','删除用户','系统管理');
insert into elecFunction(functionId,path,functionName,groups) values('0005','userAction_page','查看用户列表','系统管理');
insert into elecFunction(functionId,path,functionName,groups) values('0006','deviceAction_add','添加设备','技术设施维护管理');
insert into elecFunction(functionId,path,functionName,groups) values('0007','deviceAction_update','修改设备','技术设施维护管理');
insert into elecFunction(functionId,path,functionName,groups) values('0008','deviceAction_page','查看设备','技术设施维护管理');
insert into elecFunction(functionId,path,functionName,groups) values('0009','runAction_runStatus','查看运行情况','站点设备运行管理');
insert into elecFunction(functionId,path,functionName,groups) values('0010','runAction_fixStatus','查看维护请况','站点设备运行管理');
insert into elecFunction(functionId,path,functionName,groups) values('0011','runAction_addRunStatus','设备运行情况','站点设备运行管理');
insert into elecFunction(functionId,path,functionName,groups) values('0012','runAction_addFixStatus','设备维护情况','站点设备运行管理');


insert into elecUserRole(userId,roleId) values('00001','006');
insert into elecUserRole(userId,roleId) values('00002','006');
insert into elecUserRole(userId,roleId) values('00003','006');
insert into elecUserRole(userId,roleId) values('00004','005');
insert into elecUserRole(userId,roleId) values('00005','005');
insert into elecUserRole(userId,roleId) values('00006','004');
insert into elecUserRole(userId,roleId) values('00006','002');

insert into elecRoleFunction(roleId,functionId) values('006','0011');
insert into elecRoleFunction(roleId,functionId) values('006','0012');
insert into elecRoleFunction(roleId,functionId) values('005','0011');
insert into elecRoleFunction(roleId,functionId) values('005','0012');
insert into elecRoleFunction(roleId,functionId) values('005','0001');
insert into elecRoleFunction(roleId,functionId) values('004','0002');
insert into elecRoleFunction(roleId,functionId) values('004','0003');
insert into elecRoleFunction(roleId,functionId) values('004','0004');
insert into elecRoleFunction(roleId,functionId) values('004','0005');
insert into elecRoleFunction(roleId,functionId) values('004','0006');
insert into elecRoleFunction(roleId,functionId) values('004','0007');
insert into elecRoleFunction(roleId,functionId) values('004','0008');
insert into elecRoleFunction(roleId,functionId) values('004','0009');
insert into elecRoleFunction(roleId,functionId) values('001','0001');
insert into elecRoleFunction(roleId,functionId) values('001','0002');
insert into elecRoleFunction(roleId,functionId) values('001','0003');
insert into elecRoleFunction(roleId,functionId) values('001','0004');
insert into elecRoleFunction(roleId,functionId) values('001','0005');
insert into elecRoleFunction(roleId,functionId) values('001','0006');
insert into elecRoleFunction(roleId,functionId) values('001','0007');
insert into elecRoleFunction(roleId,functionId) values('001','0008');
insert into elecRoleFunction(roleId,functionId) values('001','0009');
insert into elecRoleFunction(roleId,functionId) values('001','0010');
insert into elecRoleFunction(roleId,functionId) values('001','0011');
insert into elecRoleFunction(roleId,functionId) values('001','0012');


