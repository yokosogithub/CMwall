package org.dboprate;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;

import org.util.Config;

public class JSPConnectDB {

	private Connection connection = null;

	public Connection connecDB(HttpServletRequest request) {

		try {
			/***** 1. 替换为你自己的数据库名（可从管理中心查看到） *****/
			String databaseName = Config.MYSQLNAME;

			/****** 2. 从环境变量里取出数据库连接需要的参数 ******/
			String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
			String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
			String username = request.getHeader("BAE_ENV_AK");
			String password = request.getHeader("BAE_ENV_SK");
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String connName = dbUrl + serverName + databaseName;

			/****** 3. 接着连接并选择数据库名为databaseName的服务器 ******/
			Class.forName(driverName);
			connection = DriverManager.getConnection(connName, username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

}
