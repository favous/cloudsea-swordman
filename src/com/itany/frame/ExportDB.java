package com.itany.frame;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class ExportDB {
	
	public static void main(String[] args) {
		//根据hibernate.cfg.xml文件生成Configuration对象
		Configuration cfg= new Configuration().configure();
		//将Configuration对象通过适配器模式得到导出对象
		SchemaExport export=new SchemaExport(cfg);
		//通过导出对象提交sql脚本，创建数据库表
		export.create(true, true);
	}

}
