package com.imooc.bearlive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {

	public static Connection getConnection() throws SQLException {
		// 获取到参数。保存到服务器Mysql里面。
		String driveName = "jdbc:mysql://";
		String host = "192.168.1.97";
		String port = "30314";
		String dbName = "d007df5a";
		String url = driveName + host + ":" + port + "/" + dbName;

		String user = "1e347b33";
		String password = "4079a7aa";
		Connection dbcon = null;

		dbcon = DriverManager.getConnection(url, user, password);

		return dbcon;
	}
}
