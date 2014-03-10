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

import org.dao.UserInfo;
import org.dboprate.JSPConnectDB;

public class QueryUserInfo extends HttpServlet  {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String currentID = request.getParameter("currentID");
		
		// �������ݿ�
		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);
		
		try{
			String sql = "select * from tb_member where open_id ='"+currentID+"'";
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			
			String iconURL = rs.getString("icon_url");
			String weixinID = rs.getString("weixin_id");
			String hobby = rs.getString("hobby");
			String school = rs.getString("school");
			String shuoshuo = rs.getString("shuoshuo");
			String weixinNum = rs.getString("weixin_num");
			int sex = rs.getInt("sex");
			
			// ����Ϣ��װ��һ���û���Ϣ������
			UserInfo userInfo = new UserInfo();
			userInfo.setIconURL(iconURL);
			userInfo.setWeixinID(weixinID);
			userInfo.setHobby(hobby);
			userInfo.setSchool(school);
			userInfo.setShuoshuo(shuoshuo);
			userInfo.setWeixinNum(weixinNum);
			userInfo.setSex(sex);
			// ���û���Ϣ����ת��Ϊjson����
			JSONArray json = JSONArray.fromObject(userInfo);
			
			rs.close();
			stm.close();
			connection.close();
			
			out.print(json);
			out.flush();
			out.close();
			
		}catch (Exception e) {
			out.print("error");
			out.flush();
			out.close();
		}
	
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
