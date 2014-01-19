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

import net.sf.json.JSONArray;

import org.dboprate.JSPConnectDB;
import org.util.Rs2Json;

public class QueryMyMsgServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String currentID = request.getParameter("currentID");
		
		// Á´½ÓÊý¾Ý¿â
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			Statement stm1 = connection.createStatement();
			String sql1 = " select * from tb_msg where open_id = '"+currentID+"' order by create_time desc ";
			ResultSet rs1 = stm1.executeQuery(sql1);
			JSONArray jArr = Rs2Json.getMyJsonArr(rs1);
			
			rs1.close();
			stm1.close();
			connection.close();
			
			out.print(jArr);
			out.flush();
			out.close();
			
		}catch (Exception e) {
			
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
