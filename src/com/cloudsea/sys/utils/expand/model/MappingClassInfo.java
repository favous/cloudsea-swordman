package com.cloudsea.sys.utils.expand.model;


import java.util.Map;



public class MappingClassInfo {
	private String tableName;
	private String idName;
	private String sequenceName;
	private PrimaryKeyTypeEnum pkType;
	private Map<String, String> nameCastMap;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public Map<String, String> getNameCastMap() {
		return nameCastMap;
	}
	public void setNameCastMap(Map<String, String> nameCastMap) {
		this.nameCastMap = nameCastMap;
	}
	public PrimaryKeyTypeEnum getPkType() {
		return pkType;
	}
	public void setPkType(PrimaryKeyTypeEnum pkType) {
		this.pkType = pkType;
	}
	public String getSequenceName() {
		return sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
}