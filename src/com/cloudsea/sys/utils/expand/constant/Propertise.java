package com.cloudsea.sys.utils.expand.constant;


import java.sql.Connection;

public class Propertise {

	public static final String DATA_SCHEMA = null;
	
	public static final String driver = "oracle.jdbc.driver.OracleDriver";
	public static final String url = "jdbc:oracle:thin:@localhost:1521:ORCL";
	public static final String name = "webframe";
	public static final String password = "root";
	public static final int DB_ISOLATION = Connection.TRANSACTION_READ_COMMITTED;

}
