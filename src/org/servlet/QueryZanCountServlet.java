package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dboprate.JSPConnectDB;

public class QueryZanCountServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		int msgID = Integer.valueOf(request.getParameter("msgID"));

		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);

		
		String sql = "select zan_count from tb_msg where msg_id="+msgID;

		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			String zanCount = String.valueOf(rs.getInt("zan_count"));

			stm.close();
			connection.close();

			out.print(zanCount);
			out.flush();
			out.close();

		} catch (Exception e) {
			out.print("error");
			out.flush();
			out.close();
			e.printStackTrace();
		}
		
	}

}
