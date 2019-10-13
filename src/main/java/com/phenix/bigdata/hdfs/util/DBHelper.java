package com.phenix.bigdata.hdfs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelper {
	private Properties propertys;
	private Connection conn = null;  
	private PreparedStatement pst = null;
    
    public DBHelper(Properties propertys) {
    	this.propertys = propertys;
    }
    
    public Connection getConn() {
		return conn;
	}
    
    public void open() throws ClassNotFoundException, SQLException {
    	String url = propertys.getProperty("db.url"); //"jdbc:mysql://127.0.0.1/student"
        String driver = propertys.getProperty("db.driver"); //"com.mysql.jdbc.Driver"
        String user = propertys.getProperty("db.user"); //"root"
        String password = propertys.getProperty("db.password");//"root"
    	Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);//获取连接  
    }
    
    public PreparedStatement execute(String sql) throws SQLException {
    	pst = conn.prepareStatement(sql);
    	return pst;
    }
    
    public void close() throws SQLException {
    	if (pst != null) {
    		pst.close();
    	}
    	
    	if (conn != null) {
    		conn.close();
    	}
    }
}