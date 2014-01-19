package org.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.dao.ImgURL;
import org.util.BcsImgUpload;


public class ImgUploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;  
	  
    /** 
     * 实现图片上传
     */   
    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {  
          
        //设置接收的编码格式  
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
    
        try {  
        	DiskFileItemFactory dff = new DiskFileItemFactory(); 
        	dff.setSizeThreshold(1024000); 
        	ServletFileUpload sfu = new ServletFileUpload(dff);  
        	FileItemIterator fii = null;
        	fii = sfu.getItemIterator(request); 
        	
        	while(fii.hasNext()){
        		FileItemStream fis = fii.next();
        		
        		try{
        			
        			if(!fis.isFormField() && fis.getName().length()>0){ 
        				String fileName = fis.getName();
        				Pattern reg = Pattern.compile("[.]jpg|png|jpeg$");
        				Matcher matcher=reg.matcher(fileName);
        				if(!matcher.find()) {  
                            out.print("formatError");
                            out.flush();
                            out.close();
                            break;  
                        }
        				
        				// 生成图片访问路径
    	                String randomStr = getRandomString(10);
    	                String imgURL = "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/Cover%2F" + randomStr + ".jpg" ;
    	                String imgUploadURL = "/Cover/" + randomStr + ".jpg" ;
    	                
        				InputStream in = fis.openStream();
        				
        				String result = BcsImgUpload.imgUpload(imgUploadURL , in);
        				
        				if(  result.equals("success") ){
    	                	out.print(imgURL);
    	              	    out.flush();
    	              	    out.close();
    	                }else if( result.equals("fileTooBig") ){
    	                	out.print("fileTooBig");
    	              	    out.flush();
    	              	    out.close();
    	                }else{
    	                	out.print("error");
    	              	    out.flush();
    	              	    out.close();
    	                }
        				
        			}
        			
        		}catch (Exception e) {
	        		e.printStackTrace();  
	    	    	out.print("error");
	    	    	out.flush();
	    	    	out.close();
				}
        	}
        }catch (Exception e) {
        	e.printStackTrace();  
	    	out.print("error");
	    	out.flush();
	    	out.close();
		}
    }  
    
    


    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
        Random random = new Random();   
        StringBuffer sb = new StringBuffer();   
        for (int i = 0; i < length; i++) {   
            int number = random.nextInt(base.length());   
            sb.append(base.charAt(number));   
        }   
        return sb.toString();   
     }  
    
	
}

