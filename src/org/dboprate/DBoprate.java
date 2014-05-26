package org.dboprate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;

import org.util.Config;

// import com.baidu.bae.api.util.BaeEnv;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.sql.*;

/**
 * @author ���� ���ڣ�2013.9.16 ���ݿ������ ��װ���ݿ��������
 */

public class DBoprate {
	public static Connection connection = null;
	public static Statement stmt = null;
	public static ResultSet rs = null;
	public static String sql = null;

	public static boolean connDB(HttpServletRequest request) {

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
			stmt = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void disConnDB() {
		try {
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
=======
	public static ArrayList<ResultSet> queryPersonnalCenter(String open_id,
			int sex) {
		ResultSet biaobaiRs = null;
		ResultSet xinyuanRs = null;
		ResultSet goudaRs = null;
		ArrayList<ResultSet> resultArr = new ArrayList<ResultSet>();

		try {
			String sqlStr = "select * from tb_biaobai where open_id = '"
					+ open_id + "' order by create_time desc";
			biaobaiRs = stmt.executeQuery(sqlStr);

			// ���ΪŮ�Ų�ѯ��Ը��
			if (sex == 0) {
				sqlStr = " select * from tb_xinyuan where open_id = '"
						+ open_id + "' order by create_time desc";
				xinyuanRs = stmt.executeQuery(sqlStr);
			}

			sqlStr = " select * from tb_gouda where open_id = '" + open_id
					+ "' order by create_time desc";
			goudaRs = stmt.executeQuery(sqlStr);

			resultArr.add(biaobaiRs);
			resultArr.add(xinyuanRs);
			resultArr.add(goudaRs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultArr;
	}

>>>>>>> remotes/CMwall/master
}
