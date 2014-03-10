package org.servlet;

/**
 * author:rickie
 * func:�����������ԡ���ס���Ը����ҳ���ʱ���ѯ��ҳ���Ӧ�Ļظ��������ṩJS������ò��ص�
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dboprate.JSPConnectDB;

public class QueryReplyCountServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		int msgID = Integer.valueOf(request.getParameter("msgID"));

		// �������ݿ�
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);

		try {
			Statement stm1 = connection.createStatement();
			String sql = "select reply_count from tb_msg where msg_id = "+msgID+"";
			ResultSet rs1 = stm1.executeQuery(sql);
			rs1.next();
			int count = rs1.getInt("reply_count");

			rs1.close();
			stm1.close();
			connection.close();

			out.print(count);
			out.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
