package com.cloudsea.sys.utils.expand.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cloudsea.sys.utils.expand.JdbcExpandUtil;
import com.cloudsea.sys.utils.expand.model.SQLOperatSymbolEnum;

public class Test {
	
	public static void main(String[] args) {
		User user = new User();
		user.setId(8600l);
		user.setUserNo("123");
		List<User> list = JdbcExpandUtil.query(user);
		
		Map<String, SQLOperatSymbolEnum> operationMap = new HashMap<String, SQLOperatSymbolEnum>(); 
//		operationMap.put("userNo", SQLOperatSymbolEnum.NOT_EQUAL);
//		operationMap.put("userNo", SQLOperatSymbolEnum.NOT_LIKE);
//		operationMap.put("id", SQLOperatSymbolEnum.GREATER_EQUAL);
//		operationMap.put("loginName", SQLOperatSymbolEnum.NOT_NULL);
		operationMap.put("id", SQLOperatSymbolEnum.LESS);
		operationMap.put("userNo", SQLOperatSymbolEnum.LIKE);
		LinkedHashMap<String, Integer> orders = new LinkedHashMap<String, Integer>();
		list = JdbcExpandUtil.query(user, operationMap, orders);
		System.out.println(list.size());
	}

}
