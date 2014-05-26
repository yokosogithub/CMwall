package org.servlet;

/**
 * author:rickie
 * func:�����������΢�ŷ���������Ϣ����Ӧ΢�ŷ�����
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
 * ������������
 * 
 * @author ����
 * @date 2013-09-15
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	/**
	 * ȷ����������΢�ŷ�����	
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ΢�ż���ǩ��
		String signature = request.getParameter("signature");
		// ʱ���
		String timestamp = request.getParameter("timestamp");
		// �����
		String nonce = request.getParameter("nonce");
		// ����ַ���
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// ͨ������signature���������У�飬��У��ɹ���ԭ������echostr����ʾ����ɹ����������ʧ��
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
	 * ����΢�ŷ�������������Ϣ���ظ�
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��������Ӧ�ı��������ΪUTF-8����ֹ�������룩
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
	
		// �������ݿ�
		// ���ú���ҵ���������Ϣ��������Ϣ
		String respMessage = CoreService.processRequest(request,DBoprate.connDB(request));

<<<<<<< HEAD
		// �����־
		// Logger logger = Logger. getLogger("name");
		// logger.log(Level.INFO, " this is for notice log print \n" + respMessage );

		
		// ��Ӧ��Ϣ
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.flush();
		out.close();
		out = null;	
=======
		
		// ��Ӧ��Ϣ
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
>>>>>>> remotes/CMwall/master
		
		// ÿ������ʼ�������ݿ⣬������Ӧ��Ϻ�ر����ݿ�
		DBoprate.disConnDB();
	}

}


