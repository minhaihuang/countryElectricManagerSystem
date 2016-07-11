package com.hhm.elec.test;

import java.util.Set;

import org.hibernate.cfg.Configuration;

import com.hhm.elec.domain.ElecRole;
import com.hhm.elec.domain.ElecUser;

public class Test {
	public static void main(String[] args) {
		
		ElecUser elecUser=new ElecUser();
		
		elecUser.setAccount("zhangsan1");
		
		//Elec 
		
		Set<ElecRole> roles=elecUser.getRoles();
		
		System.out.println(roles);
		
	}
}
