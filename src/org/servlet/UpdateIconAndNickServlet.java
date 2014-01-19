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

public class UpdateIconAndNickServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String taOpenID = request.getParameter("taOpenID");
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			Statement stm1 = connection.createStatement();
			String sql1 = " select * from tb_member where open_id = '"+taOpenID+"' ";
			ResultSet rs1 = stm1.executeQuery(sql1);
			if( !rs1.next() ){
				// 如果该用户已经取消关注
				rs1.close();
				stm1.close();
				connection.close();
				out.print("error");
				out.flush();
				out.close();
			}
			String weixinID = rs1.getString("weixin_id");
			String iconURL = rs1.getString("icon_url");
			String result = weixinID+"&**&"+iconURL;
			
			rs1.close();
			stm1.close();
			connection.close();
			
			out.print(result);
			out.flush();
			out.close();
			
		}catch (Exception e) {
			
		}
		
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
