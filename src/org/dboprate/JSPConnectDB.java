package org.dboprate;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;

import org.util.Config;

public class JSPConnectDB {

	private Connection connection = null;

	public Connection connecDB(HttpServletRequest request) {

		try {
			/***** 1. �滻Ϊ���Լ������ݿ������ɴӹ������Ĳ鿴���� *****/
			String databaseName = Config.MYSQLNAME;

			/****** 2. �ӻ���������ȡ�����ݿ�������Ҫ�Ĳ��� ******/
			String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
			String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
			String username = request.getHeader("BAE_ENV_AK");
			String password = request.getHeader("BAE_ENV_SK");
			String driverName = "com.mysql.jdbc.Driver";
			String dbUrl = "jdbc:mysql://";
			String serverName = host + ":" + port + "/";
			String connName = dbUrl + serverName + databaseName;

			/****** 3. �������Ӳ�ѡ�����ݿ���ΪdatabaseName�ķ����� ******/
			Class.forName(driverName);
			connection = DriverManager.getConnection(connName, username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return connection;
	}

}
