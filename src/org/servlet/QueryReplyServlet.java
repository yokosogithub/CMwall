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
		
		// �������ݿ�
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		

		// �������ʾ�ظ�
		if (button_type.equals("show_reply")) {

			try {
				// �������ݿ��ж�Ӧ�����Ļظ�
				String sql = "select * from tb_reply where msg_id = "+msgID+"";
				Statement stm1 = connection.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql);

				while (rs1.next()) {
					Reply reply = new Reply();

					// �����ظ��û���open_id
					String open_id = rs1.getString("open_id");
					reply.setOpenID(open_id);
					// ����open_id��ȡ�ûظ��û����û���Ϣ
					Statement stm2 = connection.createStatement();
					sql = "select * from tb_member where open_id = '"+open_id+"'";
					ResultSet rs2 = stm2.executeQuery(sql);
					if( !rs2.next() ){
						// ������û���ȡ����ע���򲻼���ظ��б�
						continue;
					}
					// �ûظ����ǳ��Լ�ͷ��
					reply.setWeixinID(rs2.getString("weixin_id"));
<<<<<<< HEAD
					reply.setSex(rs2.getInt("sex"));
=======
>>>>>>> remotes/CMwall/master
					reply.setIconURL(rs2.getString("icon_url"));

					rs2.close();
					stm2.close();

					// �����ظ���ID
					reply.setReplyID(rs1.getInt("reply_id"));
					// �����ظ����ظ���ID
					int fatherReplyID = rs1.getInt("father_reply_id");
					reply.setFatherReplyID(fatherReplyID);
					// �����ظ���Ӧ״̬��ID
					reply.setMsgID(msgID);
					// ������ʾ�Ļظ�������
					if (fatherReplyID == 0) {
						// ����Ǹ��ظ�
						reply.setContent(rs1.getString("content"));
					} else {
						// ������ӻظ�
						// ��ѯ�����ظ���Ӧ���ظ��Ļظ���
						Statement stm4 = connection.createStatement();
						String sql4 = "select * from tb_member where open_id = (select open_id from tb_reply where reply_id="
								+ fatherReplyID + ")";
						ResultSet rs4 = stm4.executeQuery(sql4);
						rs4.next();
						String weixinID = rs4.getString("weixin_id");
						reply.setContent("�ظ�" + weixinID + "��"
								+ rs1.getString("content"));

						rs4.close();
						stm4.close();
					}
					// ��ȡ�����ظ��Ļظ�ʱ��
					Timestamp time = rs1.getTimestamp("create_time");
					String createTime2 = time.toString();
					String[] strs = createTime2.split("[:]");
					String create_time = strs[0] + ":" + strs[1];
					reply.setCreateTime(create_time);

					replyArr.add(reply);
				}
				// �����ѯ���Ļظ������������Ӧ��׵�reply_count�ֶ�
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

		// �������˸��ظ����ӻظ���ֻ��ѯ�¼ӵĻظ���Ϊ��ǰ�û��������ظ�
		else if (button_type.equals("father_reply") || button_type.equals("son_reply")) {
			String currentID = request.getParameter("currentID");

			// �������ݿ��иո���ӵ���������
			String sql = "select * from tb_reply where open_id = '"+currentID+"' and msg_id = "+ msgID+" order by reply_id desc limit 1 ";
			try {
				Statement stm1 = connection.createStatement();
				ResultSet rs1 = stm1.executeQuery(sql);
				rs1.next();

				Reply reply = new Reply();

				// �����ظ��û���open_id
				reply.setOpenID(currentID);
				// ����open_id��ȡ�ûظ��û����û���Ϣ
				Statement stm2 = connection.createStatement();
				sql = "select * from tb_member where open_id = '"+currentID+"' ";
				ResultSet rs2 = stm2.executeQuery(sql);
				rs2.next();
				// ���øûظ����ǳ��Լ�ͷ��
				reply.setWeixinID(rs2.getString("weixin_id"));
<<<<<<< HEAD
				reply.setSex(rs2.getInt("sex"));
=======
>>>>>>> remotes/CMwall/master
				reply.setIconURL(rs2.getString("icon_url"));
				rs2.close();
				stm2.close();
				// �����ظ���ID
				reply.setReplyID(rs1.getInt("reply_id"));
				// ���ñ����ظ���Ӧ��׵�ID
				reply.setMsgID(msgID);
				// �����ظ��ĸ��ظ�ID
				int fatherReplyID = rs1.getInt("father_reply_id");
				reply.setFatherReplyID(fatherReplyID);
				if (fatherReplyID == 0) {
					// ����Ǹ��ظ�
					reply.setContent(rs1.getString("content"));
				} else {
					// ������ӻظ�
					// ��ѯ�����ظ���Ӧ���ظ��Ļظ���
					Statement stm3 = connection.createStatement();
<<<<<<< HEAD
					String sql3 = "select * from tb_member where open_id = (select open_id from tb_reply where reply_id="+ fatherReplyID + ")";
					ResultSet rs3 = stm3.executeQuery(sql3);
					String weixinID;
					if( !rs3.next() ){
						weixinID = "???";
					}else{
						weixinID = rs3.getString("weixin_id");
					}

					reply.setContent("�ظ�" + weixinID + "��"+ rs1.getString("content"));
=======
					String sql3 = "select * from tb_member where open_id = (select open_id from tb_reply where reply_id="
							+ fatherReplyID + ")";
					ResultSet rs3 = stm3.executeQuery(sql3);
					rs3.next();
					String weixinID = rs3.getString("weixin_id");

					reply.setContent("�ظ�" + weixinID + "��"
							+ rs1.getString("content"));
>>>>>>> remotes/CMwall/master
				}
				// ��ȡ�����ظ��Ļظ�ʱ��
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
