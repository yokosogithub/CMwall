package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dboprate.JSPConnectDB;

public class VoteAnsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
	
		int answerID = Integer.valueOf(request.getParameter("answerID"));
		int topicID = Integer.valueOf(request.getParameter("topicID"));
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			String sql1 = " update tb_topic_answer set vote_count = vote_count+1 where answer_id = "+answerID;
			Statement stm1 = connection.createStatement();
			stm1.execute(sql1);
			// 热度+1
			String sql2 = " update tb_topic set heat = heat+1 where topic_id = "+topicID;
			stm1.execute(sql2);
			
			stm1.close();
			connection.close();
			
		}catch (Exception e) {
			out.print("error");
			out.flush();
			out.close();
		}
		
		out.print("success");
		out.flush();
		out.close();
	}

}
