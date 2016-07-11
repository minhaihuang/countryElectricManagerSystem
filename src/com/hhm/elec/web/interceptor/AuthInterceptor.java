package com.hhm.elec.web.interceptor;

import java.util.HashSet;
import java.util.Set;

import com.hhm.elec.domain.ElecFunction;
import com.hhm.elec.domain.ElecRole;
import com.hhm.elec.domain.ElecUser;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 这是拦截器，判断用户是否某操作的权限
 * @author 黄帅哥
 *
 */
public class AuthInterceptor extends AbstractInterceptor {
	//这是白名单，在该名单里面的所有路径都不需要进行权限判断
	Set<String> whiteMenu=new HashSet<String>();
	@Override
	public void init() {
		// 初始白名单
		whiteMenu.add("elecUserAction_login");
		whiteMenu.add("elecUserAction_logout");
		whiteMenu.add("menuAction_left");
		whiteMenu.add("menuAction_title");
		whiteMenu.add("menuAction_loading");
		whiteMenu.add("menuAction_alermXZ");
		whiteMenu.add("menuAction_alermJX");
		whiteMenu.add("menuAction_alermYS");
		whiteMenu.add("menuAction_alermSB");
		whiteMenu.add("menuAction_alermZD");
	}
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		//获取请求的路径，即action的名称
		String actionName=actionInvocation.getProxy().getActionName();
		//System.out.println(actionName);
		//判断请求路径是否在白名单中，如果在，则直接让其通过
		
		
		//欺骗服务器的行为
		boolean mark=true;
		if(mark){
			return actionInvocation.invoke();
		}
		
		if(whiteMenu.contains(actionName)){
			actionInvocation.invoke();
		}else{
			//否则，进行权限的判断
			//判断用户是否已经登陆
			ElecUser elecUser=(ElecUser) actionInvocation.getInvocationContext().getSession().get("elecUser");
			if(elecUser==null){
				//用户还没有登陆
				return "authError";//权限不足
			}else{
				//用户已经登陆
				//取出用户的所有角色
				Set<ElecRole> roles=elecUser.getRoles();
				//逐个遍历角色，取出角色的所有权限，若有其中一个权限符合，让其通过，否则返回权限不足错误
				for (ElecRole elecRole : roles) {
					Set<ElecFunction> functions=elecRole.getFunctions();
					for (ElecFunction elecFunction : functions) {
						if(elecFunction.getPath().equals(actionName)){
							//有该权限，通过
							actionInvocation.invoke();
						}
					}
				}
			}
		}
		return "authError";
	}

}
