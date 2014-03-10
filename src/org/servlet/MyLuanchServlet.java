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

public class MyLuanchServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String currentID = request.getParameter("currentID");
		String content = request.getParameter("content");
		String description = request.getParameter("description");
		int type = Integer.valueOf(request.getParameter("type")); // 1:表白 2：许愿 3：留言
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			// 首先获取当前用户的用户信息
			String sql1 = "select * from tb_member where open_id = '"+currentID+"' ";
			Statement stm1 = connection.createStatement();
			ResultSet rs1 = stm1.executeQuery(sql1);
			rs1.next();
			String weixinID = rs1.getString("weixin_id");
			String iconURL = rs1.getString("icon_url");
			int sex = rs1.getInt("sex");
			double locationX = rs1.getDouble("locationx");
			double locationY = rs1.getDouble("locationy");
			rs1.close();
			stm1.close();
			
			// 将此条msg插入数据库
			String sql2 = " insert into tb_msg (open_id,weixin_id,icon_url,sex,content,description,type,locationx,locationy)" +
					" values ( '"+currentID+"','"+weixinID+"','"+iconURL+"',"+sex+",'"+content+"','"+description+"',"+type+","+locationX+","+locationY+" ) ";
			Statement stm2 = connection.createStatement();
			stm2.execute(sql2);
			stm2.close();
			
			out.print("success");
			out.flush();
			out.close();
			
		}catch (Exception e) {
			out.print("error");
			out.flush();
			out.close();
		}
	}

}
