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
 * ��Ϣ������
 * 
 * @author ����
 * @date 2013-09-14
 */
public class MessageUtil {

	/**
	 * token
	 */
	public static final String TOKEN = "joinlimi";

	/**
	 * ��ȡ�û���Ϣ��΢��get��ַ
	 */
	public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * ������Ϣ���ͣ�ͼ��
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * ������Ϣ���ͣ��ı�
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * ������Ϣ���ͣ�ͼƬ
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * ������Ϣ���ͣ�����λ��
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * ������Ϣ���ͣ���Ƶ
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * ������Ϣ���ͣ�����
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * �¼����ͣ�subscribe(����)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * �¼����ͣ�unsubscribe(ȡ������)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * �¼����ͣ�CLICK(�Զ���˵�����¼�)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * ����΢�ŷ���������XML��
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// ����������洢��HashMap��
		Map<String, String> map = new HashMap<String, String>();

		// ��request��ȡ��������
		InputStream inputStream = request.getInputStream();
		// ��ȡ������
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// �õ�xml��Ԫ��
		Element root = document.getRootElement();
		// �õ���Ԫ�ص������ӽڵ�
		List<Element> elementList = root.elements();

		// ���������ӽڵ�
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// �ͷ���Դ
		inputStream.close();
		inputStream = null;

		return map;
	}

	/**
	 * �ı���Ϣ����ת����xml
	 * 
	 * @param textMessage
	 *            �ı���Ϣ����
	 * @return xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * ������Ϣ����ת����xml
	 * 
	 * @param musicMessage
	 *            ������Ϣ����
	 * @return xml
	 */
	public static String musicMessageToXml(MusicMessage musicMessage) {
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}

	/**
	 * ͼ����Ϣ����ת����xml
	 * 
	 * @param newsMessage
	 *            ͼ����Ϣ����
	 * @return xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}

	/**
	 * ��չxstream��ʹ��֧��CDATA��
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// ������xml�ڵ��ת��������CDATA���
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
	 * �����ת��Ϊͼ����Ϣ
	 */

	// ���ɱ��ǽ����Ըǽ�����԰����
	public static String retuenEnter(String url, int type, String fromUserName,
			String toUserName) {
		String respMessage = null;

		// Ĭ�ϻظ����ı���Ϣ
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent("rsToNewsMsg����");
		// ���ı���Ϣ����ת����xml�ַ���
		respMessage = MessageUtil.textMessageToXml(textMessage);

		// ����ͼ����Ϣ
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();

		// ���ǽ���
		if (type == 0) {
			Article article1 = new Article();
			article1.setTitle(">>������븽�����ǽ");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
		// ��Ըǽ���
		else if (type == 1) {
			Article article1 = new Article();
			article1.setTitle(">>������븽����Ըǽ");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
		// ��Ըǽ���
		else if (type == 2) {
			Article article1 = new Article();
			article1.setTitle(">>������븽�����԰�");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
		// all_waterfall���
		else if (type == 3) {
			Article article1 = new Article();
			article1.setTitle(">>����鿴����������");
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

	// ���ظ����ı�ס���Ը�������б�
	/*
	 * public static String rsToNewsMsg2(ResultSet rs, String reqContent, String
	 * fromUserName, String toUserName,double reqX,double reqY) { String
	 * respMessage = null; try { // Ĭ�ϻظ����ı���Ϣ TextMessage textMessage = new
	 * TextMessage(); textMessage.setToUserName(fromUserName);
	 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
	 * Date().getTime());
	 * textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	 * textMessage.setFuncFlag(0); textMessage.setContent("rsToNewsMsg����"); //
	 * ���ı���Ϣ����ת����xml�ַ��� respMessage = MessageUtil.textMessageToXml(textMessage);
	 * 
	 * // ����ͼ����Ϣ NewsMessage newsMessage = new NewsMessage();
	 * newsMessage.setToUserName(fromUserName);
	 * newsMessage.setFromUserName(toUserName); newsMessage.setCreateTime(new
	 * Date().getTime());
	 * newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 * newsMessage.setFuncFlag(0);
	 * 
	 * List<Article> articleList = new ArrayList<Article>();
	 * 
	 * // ��һ��ͼ�ĵ�һ����ΪͼƬ���� Article article1 = new Article();
	 * article1.setTitle("���԰壺"); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // ������һ��ҳ�� article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * while(rs.next()){ int row = rs.getRow() - 1; Article article = new
	 * Article(); // ���ֻ���ͼƬ int type = rs.getInt("type"); // ��������� if( type == 0
	 * ){ // ��ȡgouda_id String goudaID = String.valueOf(rs.getInt("gouda_id"));
	 * // ��ȡ΢�ź� String weixinID = rs.getString("weixin_id"); // ��ȡͷ�� String
	 * iconURL = rs.getString("icon_url"); // ��ȡ�������� String content =
	 * rs.getString("content"); // ��ȡ����ʱ�� Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * // ��ȡ�Ա� int sex = rs.getInt("sex");
	 * 
	 * // ��ȡ��γ�� double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // ������� int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * // ������ݹ�������ʾǰ50���� String newContent = MessageUtil.msgCut(content);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +newContent; }else{
	 * String sexImg = emoji(0x1F6B9); itemContent = sexImg + "(" + distance +
	 * ")" + " " +newContent; } article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL); // ת���Է����Բ�������
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
	 * type == 1 ){ // ͼƬ�󹴴� // ��ȡgouda_id String goudaID =
	 * String.valueOf(rs.getInt("gouda_id")); // ��ȡ΢�ź� String weixinID =
	 * rs.getString("weixin_id"); // ��ȡͷ�� String iconURL =
	 * rs.getString("icon_url"); // ��ȡͼƬ·�� String content =
	 * rs.getString("content"); // ��ȡͼƬ���� String description =
	 * rs.getString("description"); // ��ȡ����ʱ�� Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * // ��ȡ�Ա� int sex = rs.getInt("sex");
	 * 
	 * // ��ȡ��γ�� double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // ������� int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"��ͼƬ��" +
	 * MessageUtil.msgCut(description); }else{ String sexImg = emoji(0x1F6B9);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"��ͼƬ��" +
	 * MessageUtil.msgCut(description); } article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL); // ת���Է����Բ�������
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
	 * lastArticle.setTitle("���ظ�������һ�����鿴���ࡿ"); lastArticle.setDescription("");
	 * // ��ͼƬ��Ϊ�� lastArticle.setPicUrl("");
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
	 * // ���ظ����ı�ס���Ը�б� public static String rsToNewsMsg(ResultSet rs, String
	 * reqContent, String fromUserName, String toUserName,double reqX,double
	 * reqY) { String respMessage = null; try { // Ĭ�ϻظ����ı���Ϣ TextMessage
	 * textMessage = new TextMessage(); textMessage.setToUserName(fromUserName);
	 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
	 * Date().getTime());
	 * textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	 * textMessage.setFuncFlag(0); textMessage.setContent("����б�ʧ��~"); //
	 * ���ı���Ϣ����ת����xml�ַ��� respMessage = MessageUtil.textMessageToXml(textMessage);
	 * 
	 * // ����ͼ����Ϣ NewsMessage newsMessage = new NewsMessage();
	 * newsMessage.setToUserName(fromUserName);
	 * newsMessage.setFromUserName(toUserName); newsMessage.setCreateTime(new
	 * Date().getTime());
	 * newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 * newsMessage.setFuncFlag(0);
	 * 
	 * List<Article> articleList = new ArrayList<Article>();
	 * 
	 * // ��ͼ����Ϣ if ("���ǽ".equals(reqContent)) { // ��һ��ͼ�ĵ�һ����ΪͼƬ���� Article
	 * article1 = new Article(); article1.setTitle("���ǽ��");
	 * article1.setDescription(""); article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // ������һ��ҳ�� article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * while(rs.next()){ int row = rs.getRow() - 1; Article article = new
	 * Article(); // ���ֻ������� int type = rs.getInt("type"); // ��������� if( type == 0
	 * ){ // ��ȡbiaobai_id String biaobaiID =
	 * String.valueOf(rs.getInt("biaobai_id")); // ��ȡ΢�ź� String weixinID =
	 * rs.getString("weixin_id"); // ��ȡͷ�� String iconURL =
	 * rs.getString("icon_url"); // ��ȡ�������� String content =
	 * rs.getString("content"); // ��ȡ����ʱ�� Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * 
	 * int sex = rs.getInt("sex");
	 * 
	 * // ��ȡ��γ�� double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // ������� int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * // ������ݹ�������ʾǰ50���� String newContent = MessageUtil.msgCut(content);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +newContent; }else{
	 * String sexImg = emoji(0x1F6B9); itemContent = sexImg + "(" + distance +
	 * ")" + " " +newContent; } article.setTitle(itemContent);
	 * article.setDescription("");
	 * 
	 * article.setPicUrl(iconURL); // ���ڼ���ת���� // ͼ����Ϣ��url�Ƕ�̬jspҲû�����⣬֧�ֲ������ݡ�
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
	 * }else if( type == 1 ){ // ͼƬ��� // ��ȡbiaobai_id String biaobaiID =
	 * String.valueOf(rs.getInt("biaobai_id")); // ��ȡ΢�ź� String weixinID =
	 * rs.getString("weixin_id"); // ��ȡͷ�� String iconURL =
	 * rs.getString("icon_url"); // ��ȡͼƬ·�� String content =
	 * rs.getString("content"); // ��ȡͼƬ���� String description =
	 * rs.getString("description"); // ��ȡ����ʱ�� Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * // ��ȡ�Ա� int sex = rs.getInt("sex");
	 * 
	 * // ��ȡ��γ�� double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // ������� int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * String itemContent = ""; if( sex == 0 ){ String sexImg = emoji(0x1F6BA);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"��ͼƬ��" +
	 * MessageUtil.msgCut(description); }else{ String sexImg = emoji(0x1F6B9);
	 * itemContent = sexImg + "(" + distance + ")" + " " +"��ͼƬ��" +
	 * MessageUtil.msgCut(description); } article.setTitle(itemContent);
	 * article.setDescription(""); article.setPicUrl(iconURL); // ת���Է����Բ�������
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
	 * lastArticle.setTitle("���ظ�������һ�����鿴���ࡿ"); lastArticle.setDescription("");
	 * // ��ͼƬ��Ϊ�� lastArticle.setPicUrl("");
	 * lastArticle.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(lastArticle);
	 * 
	 * newsMessage.setArticleCount(articleList.size());
	 * newsMessage.setArticles(articleList); respMessage =
	 * MessageUtil.newsMessageToXml(newsMessage); } // ��ͼ����Ϣ else if
	 * ("��Ըǽ".equals(reqContent)) { // ��һ��ͼ�ĵ�һ����ΪͼƬ���� Article article2 = new
	 * Article(); article2.setTitle("��Ըǽ��"); article2.setDescription("");
	 * article2.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // ������һ��ҳ�� article2.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article2);
	 * 
	 * while(rs.next()){ Article article = new Article(); // ���ֻ������� int type =
	 * rs.getInt("type"); // ��������� if( type == 0 ){ // ��ȡxinyuan_id String
	 * xinyuanID = String.valueOf(rs.getInt("xinyuan_id")); // ��ȡ΢�ź� String
	 * weixinID = rs.getString("weixin_id"); // ��ȡͷ�� String iconURL =
	 * rs.getString("icon_url"); // ��ȡ�������� String content =
	 * rs.getString("content"); // ��ȡ����ʱ�� Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * 
	 * // ��ȡ��γ�� double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // ������� int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * // ������ݹ�������ʾǰ50���� String newContent = MessageUtil.msgCut(content);
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
	 * }else if( type == 1 ){ // ͼƬ��Ը // ��ȡxinyuan_id String xinyuanID =
	 * String.valueOf(rs.getInt("xinyuan_id")); // ��ȡ΢�ź� String weixinID =
	 * rs.getString("weixin_id"); // ��ȡͷ�� String iconURL =
	 * rs.getString("icon_url"); // ��ȡͼƬ·�� String content =
	 * rs.getString("content"); // ��ȡͼƬ���� String description =
	 * rs.getString("description"); // ��ȡ����ʱ�� Timestamp time =
	 * rs.getTimestamp("create_time"); String createTime2 = time.toString();
	 * String [] strs = createTime2.split("[.]"); String createTime = strs[0];
	 * 
	 * // ��ȡ��γ�� double locationX = rs.getDouble("locationx"); double locationY =
	 * rs.getDouble("locationy"); // ������� int intDistance =
	 * (int)DistanceCalculate.getDistance(locationX, locationY, reqX, reqY);
	 * String distance = simpleDistance(intDistance); String webDistance =
	 * simpleDistance2(intDistance);
	 * 
	 * String itemContent = "";
	 * 
	 * String sexImg = emoji(0x1F6BA); itemContent = sexImg + "(" + distance +
	 * ")" + " " +"��ͼƬ��" + MessageUtil.msgCut(description);
	 * 
	 * article.setTitle(itemContent); article.setDescription("");
	 * article.setPicUrl(iconURL); // ת���Է����Բ������� String goudaID1 =
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
	 * lastArticle.setTitle("���ظ�������һ�����鿴���ࡿ"); lastArticle.setDescription("");
	 * // ��ͼƬ��Ϊ�� lastArticle.setPicUrl("");
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
	 * // �������Ϊ�գ���һ�������� // type: 1Ϊ���ǽ 2Ϊ��Ըǽ 3Ϊ���԰� public static String
	 * returnNewsMsg(String reqContent, String fromUserName, String toUserName)
	 * { String respMessage = null; try { // Ĭ�ϻظ����ı���Ϣ TextMessage textMessage =
	 * new TextMessage(); textMessage.setToUserName(fromUserName);
	 * textMessage.setFromUserName(toUserName); textMessage.setCreateTime(new
	 * Date().getTime());
	 * textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	 * textMessage.setFuncFlag(0); textMessage.setContent("����б�ʧ��~"); //
	 * ���ı���Ϣ����ת����xml�ַ��� respMessage = MessageUtil.textMessageToXml(textMessage);
	 * 
	 * // ����ͼ����Ϣ NewsMessage newsMessage = new NewsMessage();
	 * newsMessage.setToUserName(fromUserName);
	 * newsMessage.setFromUserName(toUserName); newsMessage.setCreateTime(new
	 * Date().getTime());
	 * newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 * newsMessage.setFuncFlag(0);
	 * 
	 * List<Article> articleList = new ArrayList<Article>();
	 * 
	 * if ("���ǽ".equals(reqContent)) { // ��һ��ͼ�ĵ�һ����ΪͼƬ���� Article article1 = new
	 * Article(); article1.setTitle("���ǽ��"); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // ������һ��ҳ�� article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * Article article2 = new Article(); String sexImg = emoji(0x1F6BA);
	 * article2.setTitle(sexImg+"(200m)" + " " +
	 * "�Ҳ�˵���㲻������������֮�������һؼ�>_< @��Ӧ�ٶ۵�Mr��"); article2.setDescription("");
	 * article2.setPicUrl(
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * );
	 * 
	 * String weixinID2 = "xuan-xuan1992"; String content2 =
	 * "�Ҳ�˵���㲻������������֮�������һؼ�>_< @��Ӧ�ٶ۵�Mr��"; String createTime2 = "���� 14:16";
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
	 * articleList.add(article2); } else if ("��Ըǽ".equals(reqContent)) { //
	 * ��һ��ͼ�ĵ�һ����ΪͼƬ���� Article article1 = new Article();
	 * article1.setTitle("��Ըǽ��"); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // ������һ��ҳ�� article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * Article article2 = new Article(); String sexImg = emoji(0x1F6BA);
	 * article2.setTitle(sexImg+"(200m)" + " " + "����Ҫ��С�����򣬵��Է����˲���=��=");
	 * article2.setDescription(""); article2.setPicUrl(
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * );
	 * 
	 * String weixinID2 = "xuan-xuan1992"; String content2 =
	 * "����Ҫ��С�����򣬵��Է����˲���=��="; String createTime2 = "���� 14:16"; String distance2
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
	 * articleList.add(article2); } else if ("���԰�".equals(reqContent)) { //
	 * ��һ��ͼ�ĵ�һ����ΪͼƬ���� Article article1 = new Article();
	 * article1.setTitle("���԰壺"); article1.setDescription("");
	 * article1.setPicUrl(
	 * "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg"
	 * ); // ������һ��ҳ�� article1.setUrl("http://cmwechatweb.duapp.com");
	 * articleList.add(article1);
	 * 
	 * Article article2 = new Article(); String sexImg = emoji(0x1F6BA);
	 * article2.setTitle(sexImg+"(200m)" + " " +
	 * "ŵ��У԰��ÿ���˶��������Լ���Ȧ�ӣ���ʧ������ʧ�ˣ������˻�����꣬��������ǽ���������");
	 * article2.setDescription(""); article2.setPicUrl(
	 * "http://mmbiz.qpic.cn/mmbiz/s7FKtbOCfN5cxsUW26AQfLqpEqPC1y0ykJpGW3bO7bqLkLibLK2zRhtJAmWPgyRhBuHxTu8uiaXTkJ6m3ibIaU13A/0"
	 * );
	 * 
	 * String weixinID2 = "xuan-xuan1992"; String content2 =
	 * "ŵ��У԰��ÿ���˶��������Լ���Ȧ�ӣ���ʧ������ʧ�ˣ������˻�����꣬��������ǽ���������"; String createTime2 =
	 * "���� 14:16"; String distance2 = "200m"; String distance3 = "0.2"; String
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

	// ���ݽ�������ɸ�������newsMsg
	public static String rsTopersonnalCenter(ArrayList<ResultSet> resultArr,
			String fromUserName, String toUserName, String weixinID, int sex,
			String iconURL, String locationLabel) {
		String respMessage = null;
		try {
			// Ĭ�ϻظ����ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			textMessage.setContent("��ȡ�б�ʧ��~");
			// ���ı���Ϣ����ת����xml�ַ���
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// ����ͼ����Ϣ
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsMessage.setFuncFlag(0);

			List<Article> articleList = new ArrayList<Article>();

			// ��һ��ͼ�ĵ�һ����ΪͼƬ����
			Article article1 = new Article();
			article1.setTitle("�������ģ�");
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl("http://2.cmwechatweb.duapp.com");
			articleList.add(article1);

			// �ڶ�����Ϊ�û���Ϣ
			String sexImg = "";
			if (sex == 0) {
				sexImg = emoji(0x1F6BA);
			} else {
				sexImg = emoji(0x1F6B9);
			}
			Article article2 = new Article();
			article2.setTitle(sexImg + weixinID + "\n" + "�������λ�ã�"+ locationLabel);
			article2.setDescription("");
			article2.setPicUrl(iconURL);
			article2.setUrl("http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+fromUserName+"'&ifSelf=true");
			articleList.add(article2);

			// ��ý����
			ResultSet biaobaiRs = resultArr.get(0);
			ResultSet xinyuanRs = resultArr.get(1);
			ResultSet liuyanRs = resultArr.get(2);

//			// ��������Ϊ˽����
//			Article article3 = new Article();
//			article3.setTitle("\n>˽����");
//			article3.setDescription("");
//			article3.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fsixin_icon.jpg");
//			// article3.setUrl("");
//			articleList.add(article3);
//
//			// ��������Ϊ�����ı��
//			Article article4 = new Article();
//			article4.setTitle("\n>�ҵı��");
//			article4.setDescription("");
//			article4.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fbiaobai_icon.jpg");
//			// article4.setUrl("");
//			articleList.add(article4);
//
//			// ��������Ϊ��������Ը
//			if (sex == 0) {
//				Article article5 = new Article();
//				article5.setTitle("\n>�ҵ���Ը");
//				article5.setDescription("");
//				article5.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fxuyuan_icon.png");
//				// article5.setUrl("");
//				articleList.add(article5);
//			}
//
//			// ��������Ϊ����������
//			Article article6 = new Article();
//			article6.setTitle("\n>�ҵ�����");
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

	// �ж�һ���ַ����Ƿ�Ϊ�ظ�����
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
	 * emoji����ת��(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}
