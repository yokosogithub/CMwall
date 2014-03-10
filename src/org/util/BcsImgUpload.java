package org.util;
/***************************************************************************
 * 
 * Copyright (c) 2012 Baidu.com, Inc. All Rights Reserved
 * 
 **************************************************************************/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.model.Resource;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.policy.PolicyAction;
import com.baidu.inf.iis.bcs.policy.PolicyEffect;
import com.baidu.inf.iis.bcs.policy.Statement;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.request.PutSuperfileRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import java.io.BufferedReader; 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.io.OutputStream;
import java.io.RandomAccessFile; 
import java.io.Reader; 

@SuppressWarnings("unused")
public class BcsImgUpload {
	private static final Log log = LogFactory.getLog(BcsImgUpload.class);
	// ----------------------------------------
	static String host = "bcs.duapp.com";
	static String accessKey = "F878d991de3cb892203c8b238b8480bf";
	static String secretKey = "01dbea53d5b181b29ab85b32d96ea974";
	static String bucket = "cmtechnology-wechat-cmwall-files";


	public static String imgUpload(String bcsSavePath,InputStream in) throws IOException{
		//File imgfile = createSampleFile(in);
		
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		
		try {
			if( !putObjectByInputStream(baiduBCS, bcsSavePath ,in) ){
				return "fileTooBig";
			}
		} catch (BCSServiceException e) {
			log.warn("Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
			return "error";
		}
		
		return "success";
	}
	
	
	private static File createSampleFile(InputStream in) {
		try {
			File file = File.createTempFile("java-sdk-", ".jpg");
			file.deleteOnExit();

			FileOutputStream os = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int len = 0;
			while((len = in.read(b)) != -1){
				os.write(b,0,len);
			}

			return file;
		} catch (IOException e) {
			log.error("tmp file create failed.");
			return null;
		}
	}
	
	
	public static void putObjectByFile(BaiduBCS baiduBCS,String bcsSavePath,File file) {
		PutObjectRequest request = new PutObjectRequest(bucket, bcsSavePath, file);
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("image/jpeg");
		// metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		log.info("x-bs-request-id: " + response.getRequestId());
		log.info(objectMetadata);
	}
	
	
	public static boolean putObjectByInputStream(BaiduBCS baiduBCS,String bcsSavePath,InputStream in) throws IOException {
		
		byte[] b = input2byte(in);
		int size = b.length;
		if( size > 1024*200 ){
			return false;
		}
		InputStream is = byte2Input(b);
		
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/jpeg");
		objectMetadata.setContentLength(size);
		PutObjectRequest request = new PutObjectRequest(bucket, bcsSavePath, is, objectMetadata);
		ObjectMetadata result = baiduBCS.putObject(request).getResult();
		log.info(result);
		
		return true;
	}
	
	public static InputStream byte2Input(byte[] buf) {  
        return new ByteArrayInputStream(buf);  
    }  
  
    public static byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    } 
	
	
}