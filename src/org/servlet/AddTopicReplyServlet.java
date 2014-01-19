package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dboprate.JSPConnectDB;

public class AddTopicReplyServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
	
		String currentID = request.getParameter("currentID");
		int topicID = Integer.valueOf(request.getParameter("topicID"));
		String content = request.getParameter("content");
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			String sql1 = " insert into tb_topic_reply (topic_id,open_id,content) values ("+topicID+",'"+currentID+"','"+content+"') ";
			Statement stm1 = connection.createStatement();
			stm1.execute(sql1);
			
			sql1 = " update tb_topic set heat = heat+3 where topic_id = "+topicID;
			stm1.execute(sql1);
			
			Date date =new Date();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//完整的时间
			String time=sdf.format(date);
			
			// 更新该回复对应话题的最后回复时间
			String sql2 = " update tb_topic set last_reply_time = '"+time+"' where topic_id = "+topicID;
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
