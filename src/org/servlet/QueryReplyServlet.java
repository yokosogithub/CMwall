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
import org.dboprate.JSPConnectDB;

public class QueryReplyServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		String button_type = request.getParameter("button_type");
		int msgID = Integer.valueOf(request.getParameter("msgID"));

		List<Reply> replyArr = new ArrayList<Reply>();
		
		// 连接数据库
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		

		// 如果是显示回复
		if (button_type.equals("show_reply")) {

			try {
				// 查找数据库中对应该条的回复
				String sql = "select * from tb_reply where msg_id = "+msgID+"";
				Statement stm1 = connection.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql);

				while (rs1.next()) {
					Reply reply = new Reply();

					// 该条回复用户的open_id
					String open_id = rs1.getString("open_id");
					reply.setOpenID(open_id);
					// 根据open_id获取该回复用户的用户信息
					Statement stm2 = connection.createStatement();
					sql = "select * from tb_member where open_id = '"+open_id+"'";
					ResultSet rs2 = stm2.executeQuery(sql);
					if( !rs2.next() ){
						// 如果该用户已取消关注，则不加入回复列表
						continue;
					}
					// 该回复的昵称以及头像
					reply.setWeixinID(rs2.getString("weixin_id"));
					reply.setSex(rs2.getInt("sex"));
					reply.setIconURL(rs2.getString("icon_url"));

					rs2.close();
					stm2.close();

					// 该条回复的ID
					reply.setReplyID(rs1.getInt("reply_id"));
					// 该条回复父回复的ID
					int fatherReplyID = rs1.getInt("father_reply_id");
					reply.setFatherReplyID(fatherReplyID);
					// 该条回复对应状态的ID
					reply.setMsgID(msgID);
					// 设置显示的回复的内容
					if (fatherReplyID == 0) {
						// 如果是父回复
						reply.setContent(rs1.getString("content"));
					} else {
						// 如果是子回复
						// 查询该条回复对应父回复的回复人
						Statement stm4 = connection.createStatement();
						String sql4 = "select * from tb_member where open_id = (select open_id from tb_reply where reply_id="
								+ fatherReplyID + ")";
						ResultSet rs4 = stm4.executeQuery(sql4);
						rs4.next();
						String weixinID = rs4.getString("weixin_id");
						reply.setContent("回复" + weixinID + "："
								+ rs1.getString("content"));

						rs4.close();
						stm4.close();
					}
					// 获取该条回复的回复时间
					Timestamp time = rs1.getTimestamp("create_time");
					String createTime2 = time.toString();
					String[] strs = createTime2.split("[:]");
					String create_time = strs[0] + ":" + strs[1];
					reply.setCreateTime(create_time);

					replyArr.add(reply);
				}
				// 计算查询到的回复数量并存入对应表白的reply_count字段
				int count = replyArr.size();
				Statement stm3 = connection.createStatement();
				String sql1 = "update tb_msg set reply_count = "+count+" where msg_id = "+msgID+"";
				stm3.execute(sql1);
				stm3.close();

				rs1.close();
				stm1.close();
				connection.close();

			} catch (Exception e) {

			}

			JSONArray jArr = JSONArray.fromObject(replyArr);
			out.println(jArr);

			out.flush();
			out.close();
		}

		// 如果点击了父回复或子回复，只查询新加的回复人为当前用户的那条回复
		else if (button_type.equals("father_reply") || button_type.equals("son_reply")) {
			String currentID = request.getParameter("currentID");

			// 查找数据库中刚刚添加的那条留言
			String sql = "select * from tb_reply where open_id = '"+currentID+"' and msg_id = "+ msgID+" order by reply_id desc limit 1 ";
			try {
				Statement stm1 = connection.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql);
				rs1.next();

				Reply reply = new Reply();

				// 该条回复用户的open_id
				reply.setOpenID(currentID);
				// 根据open_id获取该回复用户的用户信息
				Statement stm2 = connection.createStatement();
				sql = "select * from tb_member where open_id = '"+currentID+"' ";
				ResultSet rs2 = stm2.executeQuery(sql);
				rs2.next();
				// 设置该回复的昵称以及头像
				reply.setWeixinID(rs2.getString("weixin_id"));
				reply.setSex(rs2.getInt("sex"));
				reply.setIconURL(rs2.getString("icon_url"));
				rs2.close();
				stm2.close();
				// 该条回复的ID
				reply.setReplyID(rs1.getInt("reply_id"));
				// 设置本条回复对应表白的ID
				reply.setMsgID(msgID);
				// 本条回复的父回复ID
				int fatherReplyID = rs1.getInt("father_reply_id");
				reply.setFatherReplyID(fatherReplyID);
				if (fatherReplyID == 0) {
					// 如果是父回复
					reply.setContent(rs1.getString("content"));
				} else {
					// 如果是子回复
					// 查询该条回复对应父回复的回复人
					Statement stm3 = connection.createStatement();
					String sql3 = "select * from tb_member where open_id = (select open_id from tb_reply where reply_id="+ fatherReplyID + ")";
					ResultSet rs3 = stm3.executeQuery(sql3);
					String weixinID;
					if( !rs3.next() ){
						weixinID = "???";
					}else{
						weixinID = rs3.getString("weixin_id");
					}

					reply.setContent("回复" + weixinID + "："+ rs1.getString("content"));
				}
				// 获取该条回复的回复时间
				Timestamp time = rs1.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				reply.setCreateTime(create_time);

				rs1.close();
				stm1.close();
				connection.close();

				replyArr.add(reply);

				JSONArray jArr = JSONArray.fromObject(replyArr);
				out.println(jArr);
				out.flush();
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
