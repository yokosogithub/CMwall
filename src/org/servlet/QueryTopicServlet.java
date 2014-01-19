package org.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.util.DistanceCalculate;
import org.util.Rs2Json;

public class QueryTopicServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		double locationx = Double.valueOf(request.getParameter("locationx")); 
		double locationy = Double.valueOf(request.getParameter("locationy")); 
		
		// 获得附近的话题
		ResultSet rs2 = DistanceCalculate.getNearby(request,locationx, locationy , 4);
		JSONArray jArr = new JSONArray();
		
		try {
			jArr = Rs2Json.rs2Json(rs2, 4, locationx, locationy);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.print(jArr);
		out.flush();
		out.close();
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
