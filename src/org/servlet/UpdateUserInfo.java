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

public class UpdateUserInfo extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String currentID = request.getParameter("currentID");
		String weixinID = request.getParameter("weixinID");
		int hobby = Integer.valueOf(request.getParameter("hobby"));
		String school = request.getParameter("school");
<<<<<<< HEAD
		String profession = request.getParameter("profession");
		String grade = request.getParameter("grade");
		String shuoshuo = request.getParameter("shuoshuo");
		String lovestate = request.getParameter("lovestate");
=======
		String shuoshuo = request.getParameter("shuoshuo");
>>>>>>> remotes/CMwall/master
		String weixinNum = request.getParameter("weixinNum");
		
		// �������ݿ�
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
<<<<<<< HEAD
			String sql = "update tb_member set weixin_id='"+weixinID+"', hobby="+hobby+", school='"+school+"', profession='"+profession+"', grade='"+grade+"', shuoshuo='"+shuoshuo+"', love_state='"+lovestate+"', weixin_num='"+weixinNum+"' where open_id='"+currentID+"'";
=======
			String sql = "update tb_member set weixin_id='"+weixinID+"', hobby="+hobby+", school='"+school+"', shuoshuo='"+shuoshuo+"', weixin_num='"+weixinNum+"' where open_id='"+currentID+"'";
>>>>>>> remotes/CMwall/master
			Statement stm = connection.createStatement();
			stm.execute(sql);
			
			stm.close();
			connection.close();
			
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
