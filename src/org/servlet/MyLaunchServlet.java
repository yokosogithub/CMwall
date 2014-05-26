package org.servlet;

/**
 * 讲传过来的发布插入数据库
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

public class MyLaunchServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// 插入需要的参数列表：open_id weixin_id icon_url sex content description
		// locationx locationy
		String type = request.getParameter("type");
		String currentID = request.getParameter("currentID");
		String content = request.getParameter("content");
		String description = request.getParameter("description");

		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);

		// 先根据open_id获取该发布用户的个人信息
		String openID = currentID;
		String weixinID = null;
		String iconURL = null;
		int sex = 0;
		double locationx = 0.0;
		double locationy = 0.0;
		try {
			Statement stm1 = connection.createStatement();
			String sql1 = "select * from tb_member where open_id = '" + openID
					+ "' ";
			ResultSet rs1 = stm1.executeQuery(sql1);
			rs1.next();

			weixinID = rs1.getString("weixin_id");
			iconURL = rs1.getString("icon_url");
			sex = rs1.getInt("sex");
			locationx = rs1.getDouble("locationx");
			locationy = rs1.getDouble("locationy");

			rs1.close();
			stm1.close();
		} catch (SQLException e) {
			e.printStackTrace();
			out.print("error");
			out.flush();
			out.close();
		}

		// 如果是发布表白
		if (type.equals("biaobai")) {
			try {
				Statement stm2 = connection.createStatement();
				String sql2 = "insert into tb_biaobai (open_id,weixin_id,icon_url,sex,content,description,locationx,locationy)"
						+ " values ( '"
						+ openID
						+ "','"
						+ weixinID
						+ "','"
						+ iconURL
						+ "',"
						+ sex
						+ ",'"
						+ content
						+ "','"
						+ description
						+ "',"
						+ locationx
						+ ","
						+ locationy
						+ " ) ";
				stm2.execute(sql2);

				stm2.close();
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
				out.print("error");
				out.flush();
				out.close();
			}
		}
		// 如果是发许愿
		else if (type.equals("xinyuan")) {
			try {
				Statement stm2 = connection.createStatement();
				String sql2 = "insert into tb_xinyuan (open_id,weixin_id,icon_url,content,description,locationx,locationy)"
						+ " values ( '"
						+ openID
						+ "','"
						+ weixinID
						+ "','"
						+ iconURL
						+ "','"
						+ content
						+ "','"
						+ description
						+ "'," + locationx + "," + locationy + " ) ";
				stm2.execute(sql2);

				stm2.close();
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
				out.print("error");
				out.flush();
				out.close();
			}
		}
		// 如果是发布留言
		else if (type.equals("gouda")) {
			try {
				Statement stm2 = connection.createStatement();
				String sql2 = "insert into tb_gouda (open_id,weixin_id,icon_url,sex,content,description,locationx,locationy)"
						+ " values ( '"
						+ openID
						+ "','"
						+ weixinID
						+ "','"
						+ iconURL
						+ "',"
						+ sex
						+ ",'"
						+ content
						+ "','"
						+ description
						+ "',"
						+ locationx
						+ ","
						+ locationy
						+ " ) ";
				stm2.execute(sql2);

				stm2.close();
				connection.close();

			} catch (SQLException e) {
				e.printStackTrace();
				out.print("error");
				out.flush();
				out.close();
			}
		}

		out.print("success");
		out.flush();
		out.close();
	}
}
