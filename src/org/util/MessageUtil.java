package org.util;

import java.io.InputStream;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.message.req.LinkMessage;
import org.message.resp.Article;
import org.message.resp.MusicMessage;
import org.message.resp.NewsMessage;
import org.message.resp.TextMessage;

import java.sql.Timestamp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 消息工具类
 * 
 * @author 刘星
 * @date 2013-09-14
 */
public class MessageUtil {

	/**
	 * token
	 */
	public static final String TOKEN = "joinlimi";

	/**
	 * 获取用户信息的微信get地址
	 */
	public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

	/**
	 * 返回消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 解析微信发来的请求（XML）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMessage
	 *            文本消息对象
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMessage
	 *            音乐消息对象
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMessage
	 *            图文消息对象
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * 扩展xstream，使其支持CDATA块
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	/**
	 * 结果集转化为图文消息
	 */

	// 生成表白墙、许愿墙、留言板入口
	public static String retuenEnter(String url, int type, String fromUserName,
			String toUserName) {
		String respMessage = null;

		// 默认回复此文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent("rsToNewsMsg方法");
		// 将文本消息对象转换成xml字符串
		respMessage = MessageUtil.textMessageToXml(textMessage);

		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();

		// 表白墙入口
		if (type == 0) {
			Article article1 = new Article();
			article1.setTitle(">>点击进入附近表白墙");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
		// 许愿墙入口
		else if (type == 1) {
			Article article1 = new Article();
			article1.setTitle(">>点击进入附近许愿墙");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
		// 许愿墙入口
		else if (type == 2) {
			Article article1 = new Article();
			article1.setTitle(">>点击进入附近留言板");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
		// all_waterfall入口
		else if (type == 3) {
			Article article1 = new Article();
			article1.setTitle(">>点击查看附近新鲜事");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}

		newsMessage.setArticleCount(articleList.size());
		newsMessage.setArticles(articleList);
		respMessage = MessageUtil.newsMessageToXml(newsMessage);

		return respMessage;
	}

	// 返回附近的表白、心愿、留言列表
	/*
	 * public static String rsToNewsMsg2(ResultSet rs, String reqContent, String
	 * fromUserName, String toUserName,double reqX,double reqY) { String
	 * respMessage = null; try { // 默认回复此文本消息 TextMessage textMessage = new
	 * TextMessage(); textMessage.setToUserName(fromUserName);
	 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
	 * Date().getTime());
	 * textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	 * textMessage.setFuncFlag(0); textMessage.setContent("rsToNewsMsg方法"); //
	 * 将文本消息对象转换成xml字符串 respMessage = MessageUtil.textMessageToXml(textMessage);
	 * 
	 * // 创建图文消息 NewsMessage newsMessage = new NewsMessage();
	 * newsMessage.setToUserName(fromUserName);
	 * newsMessage.setFromUserName(toUserName); newsMessage.setCreateTime(new
	 * Date().getTime());
	 * newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 * newsMessage.setFuncFlag(0);
	 * 
	 * List<Article> articleList = new ArrayList<Article>();
	 * 
	 * // 第一条图文第一列作为图片标题 Article article1 = new Article();
	 * article1.setTitle("留言板："); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // 后期做一个页面 article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * while(rs.next()){ int row = rs.getRow() - 1; Article article = new
	 * Article(); // 文字还是图片 int type = rs.getInt("type"); // 如果是文字 if( type == 0
	 * ){ // 获取gouda_id String goudaID = String.valueOf(rs.getInt("gouda_id"));
	 * // 获取微信号 String weixinID = rs.getString("weixin_id"); // 获取头像 String
	 * iconURL = rs.getString("icon_url"); // 获取文字内容 String content =
	 * rs.getString("content"); // 获取创建时间 Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * // 获取性别 int sex = rs.getInt("sex");
	 * 
	 * // 获取经纬度 double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // 计算距离 int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * // 如果内容过长，显示前50个字 String newContent = MessageUtil.msgCut(content);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +newContent; }else{
	 * String sexImg = emoji(0x1F6B9); itemContent = sexImg + "(" + distance +
	 * ")" + " " +newContent; } article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL); // 转码以方便以参数传递
	 * String goudaID1 = URLEncoder.encode(goudaID, "UTF-8"); String weixinID1 =
	 * URLEncoder.encode(weixinID, "UTF-8"); String content1 =
	 * URLEncoder.encode(content, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime, "UTF-8"); String distance1 =
	 * URLEncoder.encode(webDistance, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL, "UTF-8");
	 * 
	 * article.setUrl(
	 * "http://cmwechatweb.duapp.com/GoudaTextChuancanServlet?currentID='"
	 * +fromUserName
	 * +"'&goudaID="+goudaID1+"&weixinID="+weixinID1+"&content="+content1
	 * +"&createTime="
	 * +createTime1+"&distance="+distance1+"&iconURL="+iconURL1+""); }else if(
	 * type == 1 ){ // 图片求勾搭 // 获取gouda_id String goudaID =
	 * String.valueOf(rs.getInt("gouda_id")); // 获取微信号 String weixinID =
	 * rs.getString("weixin_id"); // 获取头像 String iconURL =
	 * rs.getString("icon_url"); // 获取图片路径 String content =
	 * rs.getString("content"); // 获取图片描述 String description =
	 * rs.getString("description"); // 获取创建时间 Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * // 获取性别 int sex = rs.getInt("sex");
	 * 
	 * // 获取经纬度 double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // 计算距离 int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"（图片）" +
	 * MessageUtil.msgCut(description); }else{ String sexImg = emoji(0x1F6B9);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"（图片）" +
	 * MessageUtil.msgCut(description); } article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL); // 转码以方便以参数传递
	 * String goudaID1 = URLEncoder.encode(goudaID, "UTF-8"); String weixinID1 =
	 * URLEncoder.encode(weixinID, "UTF-8"); String content1 =
	 * URLEncoder.encode(content, "UTF-8"); String description1 =
	 * URLEncoder.encode(description, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime, "UTF-8"); String distance1 =
	 * URLEncoder.encode(distance, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL, "UTF-8");
	 * 
	 * article.setUrl("http://cmwechatweb.duapp.com/GoudaImgChuancanServlet?" +
	 * "currentID='"
	 * +fromUserName+"'&goudaID="+goudaID1+"&weixinID="+weixinID1+"&content="
	 * +content1+"" +
	 * "&description='"+description1+"'&createTime="+createTime1+"&distance="
	 * +distance1+"&iconURL="+iconURL1+""); } articleList.add(article); }
	 * 
	 * Article lastArticle = new Article();
	 * lastArticle.setTitle("【回复“再来一发”查看更多】"); lastArticle.setDescription("");
	 * // 将图片置为空 lastArticle.setPicUrl("");
	 * lastArticle.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(lastArticle);
	 * 
	 * newsMessage.setArticleCount(articleList.size());
	 * newsMessage.setArticles(articleList); respMessage =
	 * MessageUtil.newsMessageToXml(newsMessage);
	 * 
	 * }catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return respMessage; }
	 * 
	 * // 返回附近的表白、心愿列表 public static String rsToNewsMsg(ResultSet rs, String
	 * reqContent, String fromUserName, String toUserName,double reqX,double
	 * reqY) { String respMessage = null; try { // 默认回复此文本消息 TextMessage
	 * textMessage = new TextMessage(); textMessage.setToUserName(fromUserName);
	 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
	 * Date().getTime());
	 * textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	 * textMessage.setFuncFlag(0); textMessage.setContent("获得列表失败~"); //
	 * 将文本消息对象转换成xml字符串 respMessage = MessageUtil.textMessageToXml(textMessage);
	 * 
	 * // 创建图文消息 NewsMessage newsMessage = new NewsMessage();
	 * newsMessage.setToUserName(fromUserName);
	 * newsMessage.setFromUserName(toUserName); newsMessage.setCreateTime(new
	 * Date().getTime());
	 * newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 * newsMessage.setFuncFlag(0);
	 * 
	 * List<Article> articleList = new ArrayList<Article>();
	 * 
	 * // 多图文消息 if ("表白墙".equals(reqContent)) { // 第一条图文第一列作为图片标题 Article
	 * article1 = new Article(); article1.setTitle("表白墙：");
	 * article1.setDescription(""); article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // 后期做一个页面 article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * while(rs.next()){ int row = rs.getRow() - 1; Article article = new
	 * Article(); // 文字还是语音 int type = rs.getInt("type"); // 如果是文字 if( type == 0
	 * ){ // 获取biaobai_id String biaobaiID =
	 * String.valueOf(rs.getInt("biaobai_id")); // 获取微信号 String weixinID =
	 * rs.getString("weixin_id"); // 获取头像 String iconURL =
	 * rs.getString("icon_url"); // 获取文字内容 String content =
	 * rs.getString("content"); // 获取创建时间 Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * 
	 * int sex = rs.getInt("sex");
	 * 
	 * // 获取经纬度 double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // 计算距离 int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * // 如果内容过长，显示前50个字 String newContent = MessageUtil.msgCut(content);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +newContent; }else{
	 * String sexImg = emoji(0x1F6B9); itemContent = sexImg + "(" + distance +
	 * ")" + " " +newContent; } article.setTitle(itemContent);
	 * article.setDescription("");
	 * 
	 * article.setPicUrl(iconURL); // 后期加跳转界面 // 图文消息的url是动态jsp也没有问题，支持参数传递。
	 * String goudaID1 = URLEncoder.encode(biaobaiID, "UTF-8"); String weixinID1
	 * = URLEncoder.encode(weixinID, "UTF-8"); String content1 =
	 * URLEncoder.encode(content, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime, "UTF-8"); String distance1 =
	 * URLEncoder.encode(webDistance, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL, "UTF-8"); // String data = content;
	 * article.setUrl
	 * ("http://cmwechatweb.duapp.com/BiaobaiTextChuancanServlet?currentID='"
	 * +fromUserName
	 * +"'&goudaID="+goudaID1+"&weixinID="+weixinID1+"&content="+content1
	 * +"&createTime="
	 * +createTime1+"&distance="+distance1+"&iconURL="+iconURL1+"");
	 * 
	 * }else if( type == 1 ){ // 图片表白 // 获取biaobai_id String biaobaiID =
	 * String.valueOf(rs.getInt("biaobai_id")); // 获取微信号 String weixinID =
	 * rs.getString("weixin_id"); // 获取头像 String iconURL =
	 * rs.getString("icon_url"); // 获取图片路径 String content =
	 * rs.getString("content"); // 获取图片描述 String description =
	 * rs.getString("description"); // 获取创建时间 Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * // 获取性别 int sex = rs.getInt("sex");
	 * 
	 * // 获取经纬度 double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // 计算距离 int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"（图片）" +
	 * MessageUtil.msgCut(description); }else{ String sexImg = emoji(0x1F6B9);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"（图片）" +
	 * MessageUtil.msgCut(description); } article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL); // 转码以方便以参数传递
	 * String goudaID1 = URLEncoder.encode(biaobaiID, "UTF-8"); String weixinID1
	 * = URLEncoder.encode(weixinID, "UTF-8"); String content1 =
	 * URLEncoder.encode(content, "UTF-8"); String description1 =
	 * URLEncoder.encode(description, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime, "UTF-8"); String distance1 =
	 * URLEncoder.encode(webDistance, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL, "UTF-8");
	 * 
	 * article.setUrl("http://cmwechatweb.duapp.com/BiaobaiImgChuancanServlet?"
	 * +
	 * "currentID='"+fromUserName+"'&goudaID="+goudaID1+"&weixinID="+weixinID1+
	 * "&content="+content1+"" +
	 * "&description='"+description1+"'&createTime="+createTime1
	 * +"&distance="+distance1+"&iconURL="+iconURL1+""); }else{ continue; }
	 * articleList.add(article); }
	 * 
	 * Article lastArticle = new Article();
	 * lastArticle.setTitle("【回复“再来一发”查看更多】"); lastArticle.setDescription("");
	 * // 将图片置为空 lastArticle.setPicUrl("");
	 * lastArticle.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(lastArticle);
	 * 
	 * newsMessage.setArticleCount(articleList.size());
	 * newsMessage.setArticles(articleList); respMessage =
	 * MessageUtil.newsMessageToXml(newsMessage); } // 多图文消息 else if
	 * ("许愿墙".equals(reqContent)) { // 第一条图文第一列作为图片标题 Article article2 = new
	 * Article(); article2.setTitle("许愿墙："); article2.setDescription("");
	 * article2.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // 后期做一个页面 article2.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article2);
	 * 
	 * while(rs.next()){ Article article = new Article(); // 文字还是语音 int type =
	 * rs.getInt("type"); // 如果是文字 if( type == 0 ){ // 获取xinyuan_id String
	 * xinyuanID = String.valueOf(rs.getInt("xinyuan_id")); // 获取微信号 String
	 * weixinID = rs.getString("weixin_id"); // 获取头像 String iconURL =
	 * rs.getString("icon_url"); // 获取文字内容 String content =
	 * rs.getString("content"); // 获取创建时间 Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * 
	 * // 获取经纬度 double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // 计算距离 int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * // 如果内容过长，显示前50个字 String newContent = MessageUtil.msgCut(content);
	 * 
	 * String sexImg = emoji(0x1F6BA); String itemContent = sexImg + "(" +
	 * distance + ")" + " " +newContent; //String itemContent = "(" + distance +
	 * "m" +")" + " " + content; article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL);
	 * 
	 * String goudaID1 = URLEncoder.encode(xinyuanID, "UTF-8"); String weixinID1
	 * = URLEncoder.encode(weixinID, "UTF-8"); String content1 =
	 * URLEncoder.encode(content, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime, "UTF-8"); String distance1 =
	 * URLEncoder.encode(webDistance, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL, "UTF-8"); // String data = content;
	 * article.setUrl
	 * ("http://cmwechatweb.duapp.com/XinyuanTextChuancanServlet?currentID='"
	 * +fromUserName
	 * +"'&goudaID="+goudaID1+"&weixinID="+weixinID1+"&content="+content1
	 * +"&createTime="
	 * +createTime1+"&distance="+distance1+"&iconURL="+iconURL1+"");
	 * 
	 * }else if( type == 1 ){ // 图片许愿 // 获取xinyuan_id String xinyuanID =
	 * String.valueOf(rs.getInt("xinyuan_id")); // 获取微信号 String weixinID =
	 * rs.getString("weixin_id"); // 获取头像 String iconURL =
	 * rs.getString("icon_url"); // 获取图片路径 String content =
	 * rs.getString("content"); // 获取图片描述 String description =
	 * rs.getString("description"); // 获取创建时间 Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * 
	 * // 获取经纬度 double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // 计算距离 int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * String itemContent = "";
	 * 
	 * String sexImg = emoji(0x1F6BA); itemContent = sexImg + "(" + distance +
	 * ")" + " " +"（图片）" + MessageUtil.msgCut(description);
	 * 
	 * article.setTitle(itemContent); article.setDescription("");
	 * article.setPicUrl(iconURL); // 转码以方便以参数传递 String goudaID1 =
	 * URLEncoder.encode(xinyuanID, "UTF-8"); String weixinID1 =
	 * URLEncoder.encode(weixinID, "UTF-8"); String content1 =
	 * URLEncoder.encode(content, "UTF-8"); String description1 =
	 * URLEncoder.encode(description, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime, "UTF-8"); String distance1 =
	 * URLEncoder.encode(webDistance, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL, "UTF-8");
	 * 
	 * article.setUrl("http://cmwechatweb.duapp.com/XinyuanImgChuancanServlet?"
	 * +
	 * "currentID='"+fromUserName+"'&goudaID="+goudaID1+"&weixinID="+weixinID1+
	 * "&content="+content1+"" +
	 * "&description='"+description1+"'&createTime="+createTime1
	 * +"&distance="+distance1+"&iconURL="+iconURL1+""); }else{ continue; }
	 * articleList.add(article); }
	 * 
	 * Article lastArticle = new Article();
	 * lastArticle.setTitle("【回复“再来一发”查看更多】"); lastArticle.setDescription("");
	 * // 将图片置为空 lastArticle.setPicUrl("");
	 * lastArticle.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(lastArticle);
	 * 
	 * newsMessage.setArticleCount(articleList.size());
	 * newsMessage.setArticles(articleList); respMessage =
	 * MessageUtil.newsMessageToXml(newsMessage); } } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * return respMessage; }
	 * 
	 * // 如果附近为空，加一条假数据 // type: 1为表白墙 2为心愿墙 3为留言板 public static String
	 * returnNewsMsg(String reqContent, String fromUserName, String toUserName)
	 * { String respMessage = null; try { // 默认回复此文本消息 TextMessage textMessage =
	 * new TextMessage(); textMessage.setToUserName(fromUserName);
	 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
	 * Date().getTime());
	 * textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	 * textMessage.setFuncFlag(0); textMessage.setContent("获得列表失败~"); //
	 * 将文本消息对象转换成xml字符串 respMessage = MessageUtil.textMessageToXml(textMessage);
	 * 
	 * // 创建图文消息 NewsMessage newsMessage = new NewsMessage();
	 * newsMessage.setToUserName(fromUserName);
	 * newsMessage.setFromUserName(toUserName); newsMessage.setCreateTime(new
	 * Date().getTime());
	 * newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 * newsMessage.setFuncFlag(0);
	 * 
	 * List<Article> articleList = new ArrayList<Article>();
	 * 
	 * if ("表白墙".equals(reqContent)) { // 第一条图文第一列作为图片标题 Article article1 = new
	 * Article(); article1.setTitle("表白墙："); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // 后期做一个页面 article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * Article article2 = new Article(); String sexImg = emoji(0x1F6BA);
	 * article2.setTitle(sexImg+"(200m)" + " " +
	 * "我不说，你不懂。限你三天之内来接我回家>_< @反应迟钝的Mr张"); article2.setDescription("");
	 * article2.setPicUrl(
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * );
	 * 
	 * String weixinID2 = "xuan-xuan1992"; String content2 =
	 * "我不说，你不懂。限你三天之内来接我回家>_< @反应迟钝的Mr张"; String createTime2 = "昨天 14:16";
	 * String distance2 = "200m"; String distance3 = "0.2"; String iconURL2 =
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * ;
	 * 
	 * String goudaID1 = URLEncoder.encode("1", "UTF-8"); String weixinID1 =
	 * URLEncoder.encode(weixinID2, "UTF-8"); String content1 =
	 * URLEncoder.encode(content2, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime2, "UTF-8"); String distance1 =
	 * URLEncoder.encode(distance3, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL2, "UTF-8");
	 * 
	 * article2.setUrl(
	 * "http://cmwechatweb.duapp.com/BiaobaiTextChuancanServlet?currentID='"
	 * +fromUserName
	 * +"'&goudaID='"+goudaID1+"'&weixinID="+weixinID1+"&content="+content1
	 * +"&createTime="
	 * +createTime1+"&distance="+distance1+"&iconURL="+iconURL1+"");
	 * articleList.add(article2); } else if ("许愿墙".equals(reqContent)) { //
	 * 第一条图文第一列作为图片标题 Article article1 = new Article();
	 * article1.setTitle("许愿墙："); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // 后期做一个页面 article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * Article article2 = new Article(); String sexImg = emoji(0x1F6BA);
	 * article2.setTitle(sexImg+"(200m)" + " " + "我想要个小仙人球，电脑辐射伤不起=。=");
	 * article2.setDescription(""); article2.setPicUrl(
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * );
	 * 
	 * String weixinID2 = "xuan-xuan1992"; String content2 =
	 * "我想要个小仙人球，电脑辐射伤不起=。="; String createTime2 = "昨天 14:16"; String distance2
	 * = "200m"; String distance3 = "0.2"; String iconURL2 =
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * ;
	 * 
	 * String goudaID1 = URLEncoder.encode("1", "UTF-8"); String weixinID1 =
	 * URLEncoder.encode(weixinID2, "UTF-8"); String content1 =
	 * URLEncoder.encode(content2, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime2, "UTF-8"); String distance1 =
	 * URLEncoder.encode(distance3, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL2, "UTF-8");
	 * 
	 * article2.setUrl(
	 * "http://cmwechatweb.duapp.com/XinyuanTextChuancanServlet?currentID='"
	 * +fromUserName
	 * +"'&goudaID='"+goudaID1+"'&weixinID="+weixinID1+"&content="+content1
	 * +"&createTime="
	 * +createTime1+"&distance="+distance1+"&iconURL="+iconURL1+"");
	 * articleList.add(article2); } else if ("留言板".equals(reqContent)) { //
	 * 第一条图文第一列作为图片标题 Article article1 = new Article();
	 * article1.setTitle("留言板："); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // 后期做一个页面 article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * Article article2 = new Article(); String sexImg = emoji(0x1F6BA);
	 * article2.setTitle(sexImg+"(200m)" + " " +
	 * "诺大校园，每个人都有属于自己的圈子，迷失的人迷失了，相逢的人会再相逢，我在厘米墙，你在哪里？");
	 * article2.setDescription(""); article2.setPicUrl(
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * );
	 * 
	 * String weixinID2 = "xuan-xuan1992"; String content2 =
	 * "诺大校园，每个人都有属于自己的圈子，迷失的人迷失了，相逢的人会再相逢，我在厘米墙，你在哪里？"; String createTime2 =
	 * "昨天 14:16"; String distance2 = "200m"; String distance3 = "0.2"; String
	 * iconURL2 =
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * ;
	 * 
	 * String goudaID1 = URLEncoder.encode("1", "UTF-8"); String weixinID1 =
	 * URLEncoder.encode(weixinID2, "UTF-8"); String content1 =
	 * URLEncoder.encode(content2, "UTF-8"); String createTime1 =
	 * URLEncoder.encode(createTime2, "UTF-8"); String distance1 =
	 * URLEncoder.encode(distance3, "UTF-8"); String iconURL1 =
	 * URLEncoder.encode(iconURL2, "UTF-8");
	 * 
	 * 
	 * article2.setUrl(
	 * "http://cmwechatweb.duapp.com/GoudaTextChuancanServlet?currentID='"
	 * +fromUserName
	 * +"'&goudaID="+goudaID1+"&weixinID="+weixinID1+"&content="+content1
	 * +"&createTime="
	 * +createTime1+"&distance="+distance1+"&iconURL="+iconURL1+"");
	 * articleList.add(article2); }
	 * 
	 * newsMessage.setArticleCount(articleList.size());
	 * newsMessage.setArticles(articleList); respMessage =
	 * MessageUtil.newsMessageToXml(newsMessage);
	 * 
	 * }catch (Exception e) { e.printStackTrace(); }
	 * 
	 * return respMessage; }
	 */

	// 根据结果集生成个人中心newsMsg
	public static String rsTopersonnalCenter(ArrayList<ResultSet> resultArr,
			String fromUserName, String toUserName, String weixinID, int sex,
			String iconURL, String locationLabel) {
		String respMessage = null;
		try {
			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			textMessage.setContent("获取列表失败~");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 创建图文消息
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			List<Article> articleList = new ArrayList<Article>();

			// 第一条图文第一列作为图片标题
			Article article1 = new Article();
			article1.setTitle("个人中心：");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl("http://2.cmwechatweb.duapp.com");
			articleList.add(article1);

			// 第二列作为用户信息
			String sexImg = "";
			if (sex == 0) {
				sexImg = emoji(0x1F6BA);
			} else {
				sexImg = emoji(0x1F6B9);
			}
			Article article2 = new Article();
			article2.setTitle(sexImg + weixinID + "\n" + "最近更新位置："+ locationLabel);
			article2.setDescription("");
			article2.setPicUrl(iconURL);
			article2.setUrl("http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+fromUserName+"'&ifSelf=true");
			articleList.add(article2);

			// 获得结果集
			ResultSet biaobaiRs = resultArr.get(0);
			ResultSet xinyuanRs = resultArr.get(1);
			ResultSet liuyanRs = resultArr.get(2);

//			// 第三列作为私信栏
//			Article article3 = new Article();
//			article3.setTitle("\n>私信箱");
//			article3.setDescription("");
//			article3.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fsixin_icon.jpg");
//			// article3.setUrl("");
//			articleList.add(article3);
//
//			// 第四列作为发过的表白
//			Article article4 = new Article();
//			article4.setTitle("\n>我的表白");
//			article4.setDescription("");
//			article4.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fbiaobai_icon.jpg");
//			// article4.setUrl("");
//			articleList.add(article4);
//
//			// 第五列作为发过的心愿
//			if (sex == 0) {
//				Article article5 = new Article();
//				article5.setTitle("\n>我的许愿");
//				article5.setDescription("");
//				article5.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fxuyuan_icon.png");
//				// article5.setUrl("");
//				articleList.add(article5);
//			}
//
//			// 第六列作为发过的留言
//			Article article6 = new Article();
//			article6.setTitle("\n>我的留言");
//			article6.setDescription("");
//			article6.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fliuyan_icon.jpg");
//			// article6.setUrl("");
//			articleList.add(article6);

			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);

		} catch (Exception e) {
		}

		return respMessage;
	}

	// 判断一个字符串是否为重复内容
	public static boolean isRepeatStr(String numOrStr) {
		boolean flag = true;
		char str = numOrStr.charAt(0);
		for (int i = 0; i < numOrStr.length(); i++) {
			if (str != numOrStr.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public static String simpleDistance(int distance) {
		int newDistance = 0;
		String newStrDistance = "0m";

		if (distance <= 100 && distance > 0) {
			newStrDistance = "100m";
		} else if (distance < 1000) {
			int bai = distance / 100;
			newDistance = bai * 100;
			newStrDistance = newDistance + "m";
		} else {
			int qian = distance / 1000;
			int bai = (distance - qian * 1000) / 100;
			newStrDistance = qian + "." + bai + "km";
		}

		return newStrDistance;
	}

	public static String simpleDistance2(int distance) {
		int newDistance = 0;
		String newStrDistance = "0";

		if (distance == 0) {
			newStrDistance = "0";
		} else if (distance <= 100 && distance > 0) {
			newStrDistance = "0.1";
		} else if (distance < 1000) {
			int bai = distance / 100;
			newStrDistance = "0." + bai;
		} else {
			int qian = distance / 1000;
			int bai = (distance - qian * 1000) / 100;
			newStrDistance = qian + "." + bai;
		}

		return newStrDistance;
	}

	public static String msgCut(String content) {
		String newContent = "";

		if (content.length() > 30) {
			newContent = content.substring(0, 30) + "...";
		} else {
			newContent = content;
		}

		return newContent;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}
