package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dboprate.JSPConnectDB;

public class DianZanServlet extends HttpServlet {
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

		String sql = "update tb_msg set zan_count=zan_count+1 where msg_id="
				+ msgID;

		try {
			Statement stm = connection.createStatement();
			stm.execute(sql);

			stm.close();
			connection.close();
		} catch (SQLException e) {
			out.print("error");
			out.flush();
			out.close();
			e.printStackTrace();
		}
		
		out.print("success");
		out.flush();
		out.close();

	}
}
