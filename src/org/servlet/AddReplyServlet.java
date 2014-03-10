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

public class AddReplyServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		// �����ظ���Ӧ���Ե�ID
		int msgID = Integer.valueOf(request.getParameter("msgID"));
		// �����ظ��ĸ��ظ�ID
		int fatherReplyID = Integer.valueOf(request.getParameter("fatherReplyID"));
		// �ظ��˵�openid
		String currentID = request.getParameter("currentID");
		// �ظ�����
		String content = request.getParameter("content");

		// �������ݿ�
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);

		// ����Ǹ��ظ��������һ���ظ�
		if (fatherReplyID == 0) {
			// ��������
			try {
				Statement stm = connection.createStatement();
				String sql = "insert into tb_reply (msg_id,open_id,content) values ( "
						+ msgID
						+ ",'"
						+ currentID
						+ "','"
						+ content
						+ "' ) ";
				stm.execute(sql);

				// replycount+1
				Statement stm3 = connection.createStatement();
				String sql1 = "update tb_msg set reply_count = reply_count+1 where msg_id = "
						+ msgID + " ";
				stm3.execute(sql1);
				stm3.close();

				stm.close();
				connection.close();

				String msg = "ok";
				out.println(msg);
				out.flush();
				out.close();

			} catch (Exception e) {
				String msg = "error";
				out.println(msg);
				out.close();
				e.printStackTrace();
			}
		}
		// ������ӻظ��������һ���ظ�
		else {
			try {
				Statement stm = connection.createStatement();
				String sql = "insert into tb_reply (msg_id,father_reply_id,open_id,content) "
						+ "values ( "
						+ msgID
						+ ", "
						+ fatherReplyID
						+ " ,'" + currentID + "','" + content + "' ) ";
				stm.execute(sql);

				// replycount+1
				Statement stm3 = connection.createStatement();
				String sql1 = "update tb_msg set reply_count = reply_count+1 where msg_id = "+ msgID + " ";
				stm3.execute(sql1);
				stm3.close();

				stm.close();
				connection.close();

				String msg = "ok";
				out.print(msg);
				out.close();

			} catch (Exception e) {
				String msg = "error";
				out.print(msg);
				out.close();
				e.printStackTrace();
			}
		}

	}
}
