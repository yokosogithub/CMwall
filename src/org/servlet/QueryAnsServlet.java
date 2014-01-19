package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.dao.Reply;
import org.dao.TopicAnswer;
import org.dboprate.JSPConnectDB;

public class QueryAnsServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		int topicID = Integer.valueOf(request.getParameter("topicID"));
		
		List<TopicAnswer> ansArr = new ArrayList<TopicAnswer>();
		JSONArray jArr = new JSONArray();
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			String sql1 = " select * from tb_topic_answer where topic_id = "+topicID+" ";
			Statement stm1 = connection.createStatement();
			ResultSet rs1 = stm1.executeQuery(sql1);
			
			while(rs1.next()){
				TopicAnswer ans = new TopicAnswer();
				ans.setAnswerID(rs1.getInt("answer_id"));
				ans.setTopicID(rs1.getInt("topic_id"));
				ans.setContent(rs1.getString("content"));
				ans.setVoteCount(rs1.getInt("vote_count"));
				ansArr.add(ans);
			}
			
			rs1.close();
			stm1.close();
			connection.close();
			
			jArr = JSONArray.fromObject(ansArr);
		}catch (Exception e) {
			out.print("error");
			out.flush();
			out.close();
		}
		
		out.print(jArr);
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
}
