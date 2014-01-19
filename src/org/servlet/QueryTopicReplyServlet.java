package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.dao.Reply;
import org.dao.TopicReply;
import org.dboprate.JSPConnectDB;

public class QueryTopicReplyServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		int topicID = Integer.valueOf(request.getParameter("topicID"));
		
		List<TopicReply> replyArr = new ArrayList<TopicReply>();
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			String sql1 = " select * from tb_topic_reply where topic_id = "+topicID;
			Statement stm1 = connection.createStatement();
			ResultSet rs1 = stm1.executeQuery(sql1);
			
			while (rs1.next()) {
				TopicReply topicReply = new TopicReply();
				
				// 该条回复用户的open_id
				String openID = rs1.getString("open_id");
				topicReply.setOpenID(openID);
				// 根据openID获取该回复用户的用户信息
				Statement stm2 = connection.createStatement();
				String sql2 = "select * from tb_member where open_id = '"+openID+"'";
				ResultSet rs2 = stm2.executeQuery(sql2);
				if( !rs2.next() ){
					// 如果该用户已取消关注，则不加入回复列表
					continue;
				}
				// 该回复的昵称以及头像
				topicReply.setWeixinID(rs2.getString("weixin_id"));
				topicReply.setIconURL(rs2.getString("icon_url"));
				topicReply.setSex(rs2.getInt("sex"));
				
				rs2.close();
				stm2.close();
				
				topicReply.setReplyID(rs1.getInt("reply_id"));
				int fatherReplyID = rs1.getInt("father_reply_id");
				topicReply.setFatherReplyID(fatherReplyID);
				topicReply.setTopicID(topicID);
				// 设置显示的回复的内容
				if (fatherReplyID == 0) {
					// 如果是父回复
					topicReply.setContent(rs1.getString("content"));
				} else {
					// 如果是子回复
					// 查询该条回复对应父回复的回复人
					Statement stm3 = connection.createStatement();
					String sql3 = "select * from tb_member where open_id = (select open_id from tb_topic_reply where reply_id="+ fatherReplyID + ")";
					ResultSet rs3 = stm3.executeQuery(sql3);
					String weixinID;
					if ( !rs3.next() ){
						weixinID = "???";
					}else{
						weixinID = rs3.getString("weixin_id");
					}
					topicReply.setContent("回复" + weixinID + "："+ rs1.getString("content"));

					rs3.close();
					stm3.close();
				}
				// 获取该条回复的回复时间
				Timestamp time = rs1.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				topicReply.setCreateTime(create_time);
				
				replyArr.add(topicReply);
			}
			rs1.close();
			stm1.close();
			connection.close();
			
		}catch (Exception e) {
			
		}
		
		JSONArray jArr = JSONArray.fromObject(replyArr);
		
		out.println(jArr);
		out.flush();
		out.close();
		
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
