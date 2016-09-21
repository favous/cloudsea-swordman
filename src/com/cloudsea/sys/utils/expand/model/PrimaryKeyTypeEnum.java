package com.cloudsea.sys.utils.expand.model;

public enum PrimaryKeyTypeEnum {
	
	AUTO(1, "auto"),
	ASSIGNED(2, "assigned");
	
	private int key;
	private String name;
	
	private PrimaryKeyTypeEnum(int key, String name) {
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
