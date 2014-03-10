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

public class SetFreshTime2ZeroServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String pageType = request.getParameter("pageType");
		String currentID = request.getParameter("currentID");

		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);

		if (pageType.equals("biaobai_waterfall")) {
			try {
				Statement stm = connection.createStatement();
				String sql = "update tb_member set biaobai_time=0 where open_id= '"
						+ currentID + "' ";
				stm.execute(sql);
				stm.close();
				connection.close();

				out.print("ok");
				out.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (pageType.equals("xinyuan_waterfall")) {

		} else if (pageType.equals("liuyan_waterfall")) {

		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
