package com.itany.frame.spring.ioc;

import java.util.List;
import java.util.Map;

public class PropertyDefinition {
	
	/** property标签的name属性  */  
    private String name;  
    
    /** property标签的value属性 */  
    private String value;  

    /** property标签的ref属性 */  
    private String ref;  
    
    /** property标签的list属性 */  
    private List<PropertyDefinition> list;  
    
    /** property标签的map属性 */  
    private Map<String, PropertyDefinition> map;  
    
    public PropertyDefinition(String name, String val, boolean isRelative) {  
        super();  
        if (isRelative){
            this.ref = val;  
        } else{
            this.value = val;  
        }
        this.name = name;  
    }

	public PropertyDefinition(String name, Map<String, PropertyDefinition> map) {
		super();
		this.name = name;
		this.map = map;
	}

	public PropertyDefinition(String name, List<PropertyDefinition> list) {
		super();
		this.name = name;
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public List<PropertyDefinition> getList() {
		return list;
	}

	public void setList(List<PropertyDefinition> list) {
		this.list = list;
	}

	public Map<String, PropertyDefinition> getMap() {
		return map;
	}

	public void setMap(Map<String, PropertyDefinition> map) {
		this.map = map;
	}

}
