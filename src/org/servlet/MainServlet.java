package org.servlet;

/**
 * author:rickie
 * func:负责接收来自微信服务器的消息并响应微信服务器
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dboprate.DBoprate;
import org.message.resp.TextMessage;
import org.service.CoreService;
import org.util.MessageUtil;
import org.util.SignUtil;

import org.util.Config;
<<<<<<< HEAD
import java.util.logging.Logger;
import java.util.logging.Level;
=======
>>>>>>> remotes/CMwall/master

// import com.baidu.bae.api.util.BaeEnv;

/**
 * 核心请求处理类
 * 
 * @author 刘星
 * @date 2013-09-15
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	/**
	 * 确认请求来自微信服务器	
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
<<<<<<< HEAD
		out.flush();
=======
>>>>>>> remotes/CMwall/master
		out = null;	
		
	}

	
	/**
	 * 处理微信服务器发来的消息并回复
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		// 连接数据库
		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(request,DBoprate.connDB(request));

<<<<<<< HEAD
		// 输出日志
		// Logger logger = Logger. getLogger("name");
		// logger.log(Level.INFO, " this is for notice log print \n" + respMessage );

		
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.flush();
		out.close();
		out = null;	
=======
		
		// 响应消息
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
>>>>>>> remotes/CMwall/master
		
		// 每次请求开始连接数据库，单次响应完毕后关闭数据库
		DBoprate.disConnDB();
	}

}


