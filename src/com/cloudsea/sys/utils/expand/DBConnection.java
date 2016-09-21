package com.cloudsea.sys.utils.expand;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cloudsea.sys.utils.expand.constant.Propertise;

public class DBConnection {
	public static Connection getConnection() {
		try {
			Class.forName(Propertise.driver);
			return DriverManager.getConnection(Propertise.url, Propertise.name, Propertise.password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void freeConnection(Connection conn, PreparedStatement ps,
			ResultSet rs) {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
	}

	public static void freeConnection(Connection conn, PreparedStatement ps) {
		freeConnection(conn, ps, null);		
	}
}
