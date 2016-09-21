package com.cloudsea.sys.utils.expand.model;

public enum SQLOperatSymbolEnum {
	
	EQUAL(1, "EQUAL"),
	NOT_EQUAL(2, ""),
	GREATER(3, ""),
	GREATER_EQUAL(4, ""),
	LESS(5, ""),
	LESS_EQUAL(6, ""),
	LIKE(7, ""),
	NOT_LIKE(8, ""),
	BETWEEN(9, ""),
	NOT_BETWEEN(10, ""),
	IN(11, ""),
	NOT_IN(12, ""),
	NULL(13, ""),
	NOT_NULL(14, "");
	
	private int key;
	private String name;
	
	private SQLOperatSymbolEnum(int key, String name) {
		this.key = key;
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
