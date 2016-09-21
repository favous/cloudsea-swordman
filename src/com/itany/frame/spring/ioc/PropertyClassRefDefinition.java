package com.itany.frame.spring.ioc;

public class PropertyClassRefDefinition {
	
	/** bean属性name  */  
    private String name;  
    
    /** 关联的Class */  
    private Class<?> classRef;  
    
    public PropertyClassRefDefinition(String name, Class<?> classRef) {  
        super();  
        this.classRef = classRef;
        this.name = name;  
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getClassRef() {
		return classRef;
	}

	public void setClassRef(Class<?> classRef) {
		this.classRef = classRef;
	}

}
