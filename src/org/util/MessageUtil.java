package org.util;

import java.io.InputStream;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
<<<<<<< HEAD
import java.sql.SQLException;
=======
>>>>>>> remotes/CMwall/master
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

<<<<<<< HEAD
import org.dao.StaticUserInfo;
import org.dboprate.DBoprate;
=======
>>>>>>> remotes/CMwall/master
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.message.req.LinkMessage;
import org.message.resp.Article;
import org.message.resp.MusicMessage;
import org.message.resp.NewsMessage;
import org.message.resp.TextMessage;

import java.sql.Timestamp;
<<<<<<< HEAD
import java.text.SimpleDateFormat;

=======
>>>>>>> remotes/CMwall/master
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
<<<<<<< HEAD
					if ( !text.equals("114311124") ) {
=======
					if (cdata) {
>>>>>>> remotes/CMwall/master
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

<<<<<<< HEAD
	
	

	
	// ��֤ż�������Ƿ�CD�꣨�����Ƿ��Ѿ�ʹ�ù�ż�����ܣ�
	public static boolean OuyuUsable(String lastOuyuDate){
		if( lastOuyuDate.equals("9999-09-09") ){
			return true;
		}
		
		// �뵱ǰʱ��Ա�
		Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		String dateNow = sdf.format(date);
		String [] strs1 = lastOuyuDate.split("-");
		int lastDateYear = Integer.valueOf(strs1[0]);
		int lastDateMonth = Integer.valueOf(strs1[1]);
		int lastDateDay = Integer.valueOf(strs1[2]);
		String [] strs2 = dateNow.split("-");
		int nowDateYear = Integer.valueOf(strs2[0]);
		int nowDateMonth = Integer.valueOf(strs2[1]);
		int nowDateDay = Integer.valueOf(strs2[2]);
		
		if( nowDateYear == lastDateYear ){
			if( nowDateMonth == lastDateMonth ){
				if( nowDateDay == lastDateDay ){
					return false;
				}else if( nowDateDay > lastDateDay ){
					return true;
				}
				
			}else if( nowDateMonth > lastDateMonth ){
				return true;
			}
			
		}else if( nowDateYear > lastDateYear ){
			return true;
		}
		
		return true;
	}
	
	
	// �������������˵ĸ���ż�����������articleList
	public static boolean addRsIntoArticleList(ResultSet rs, List<Article> articleList, double myLocationx, double myLocationy, int mySex){
		try{
			while( rs.next() ){
				// ��ȡ������Ϣ
				String openID = rs.getString("open_id");
				String weixinID = rs.getString("weixin_id");
				String iconURL = rs.getString("icon_url");
				int sex = rs.getInt("sex");
				double locationx = rs.getDouble("locationx");
				double locationy = rs.getDouble("locationy");
				// �������
				int intDistance = (int) DistanceCalculate.getDistance(myLocationx,myLocationy, locationx, locationy);
				String distance = MessageUtil.simpleDistance2(intDistance);
				// ������������
				String itemContent = ""; 
				if( sex == 0 ){ 
					String sexImg = emoji(0x1F6BA);
					itemContent = sexImg + weixinID +"\n" + distance + "km����" ;
				}else{ 
					String sexImg = emoji(0x1F6B9);
					itemContent = sexImg + weixinID +"\n" + distance + "km����" ;
				}
				// ����articleList
				Article article = new Article();
				article.setTitle(itemContent);
				article.setDescription("");
				article.setPicUrl(iconURL);
				article.setUrl("http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+openID+"'&ifSelf=false");
				articleList.add(article);
			}
			String buttomTitle = "";
			if( mySex == 1 ){
				buttomTitle = "���ͷ��鿴���ϣ�ԼTa�ͼ�Ta΢�Ű�>_<";
			}else{
				buttomTitle = "���ͷ��鿴���ϣ���Ta΢���໥�˽�һ�°�>_<";
			}
			Article article1 = new Article();
			article1.setTitle(buttomTitle);
			article1.setDescription("");
			// article1.setPicUrl(iconURL);
			article1.setUrl("");
			articleList.add(article1);
			
		}catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	
	// �����������˵ĸ���ż��������������������articleList
	public static boolean addRsIntoArticleList2(ResultSet rs, List<Article> articleList, double myLocationx, double myLocationy, int rsSize, int mySex){
		// ��ȡ����������±�
		ArrayList<Integer> intRandomArr = CalculateUtil.getRandomList(rsSize);
		
		try{
			while( rs.next() ){
				int rowNow = rs.getRow();
				// ����������ȡ���±�
				if( rowNow == intRandomArr.get(0) || rowNow == intRandomArr.get(1) || rowNow == intRandomArr.get(2) ){
					// ��ȡ������Ϣ
					String openID = rs.getString("open_id");
					String weixinID = rs.getString("weixin_id");
					String iconURL = rs.getString("icon_url");
					int sex = rs.getInt("sex");
					double locationx = rs.getDouble("locationx");
					double locationy = rs.getDouble("locationy");
					// �������
					int intDistance = (int) DistanceCalculate.getDistance(myLocationx,myLocationy, locationx, locationy);
					String distance = MessageUtil.simpleDistance2(intDistance);
					// ������������
					String itemContent = ""; 
					if( sex == 0 ){ 
						String sexImg = emoji(0x1F6BA);
						itemContent = sexImg + weixinID +"\n" + distance + "km����" ;
					}else{ 
						String sexImg = emoji(0x1F6B9);
						itemContent = sexImg + weixinID +"\n" + distance + "km����" ;
					}
					// ����articleList
					Article article = new Article();
					article.setTitle(itemContent);
					article.setDescription("");
					article.setPicUrl(iconURL);
					article.setUrl("http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+openID+"'&ifSelf=false");
					articleList.add(article);
				}else{
					continue;
				}
			}
			String buttomTitle = "";
			if( mySex == 1 ){
				buttomTitle = "���ͷ��鿴���ϣ�ԼTa�ͼ�Ta΢�Ű�>_<";
			}else{
				buttomTitle = "���ͷ��鿴���ϣ���Ta΢���໥�˽�һ�°�>_<";
			}
			Article article1 = new Article();
			article1.setTitle(buttomTitle);
			article1.setDescription("");
			// article1.setPicUrl(iconURL);
			article1.setUrl("");
			articleList.add(article1);
			
		}catch (Exception e) {
			return false;
		}
		
		return true;
	}

	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////// ��ȡ������ż�����news
	public static String getOuyuUserList(double locationx, double locationy, String fromUserName, String toUserName){
		String respMessage = null;
		
		// Ĭ�ϻظ����ı���Ϣ
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		//textMessage.setContent("error");
		// ���ı���Ϣ����ת����xml�ַ���
		//respMessage = MessageUtil.textMessageToXml(textMessage);
		
		// ����ͼ����Ϣ
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

		List<Article> articleList = new ArrayList<Article>();
		
		try {
			String sql = "select * from tb_member where open_id = '"+fromUserName+"' ";
			ResultSet rs = DBoprate.stmt.executeQuery(sql);
			rs.next();
			// ���Ȼ�ȡ���û��ĸ�����Ϣ
			String weixinID = rs.getString("weixin_id");
			String iconURL = rs.getString("icon_url");
			int sex = rs.getInt("sex");
			int hobby = rs.getInt("hobby");
			String school = rs.getString("school");
			String profession = rs.getString("profession");
			String grade = rs.getString("grade");
			String shuoshuo = rs.getString("shuoshuo");
			String loveState = rs.getString("love_state");
			String weixinNum = rs.getString("weixin_num");
			String lastOuyuDate = String.valueOf(rs.getDate("last_ouyu_date"));
			int ouyuTime = rs.getInt("ouyu_time");
			
			rs.close();
			
			// ���ж��û������Ƿ�����
			if( hobby == 0 || weixinNum.equals("����") ){
				if( weixinNum.equals("����") ){
					textMessage.setContent("���Ȼظ����������ġ����Ƹ������ϡ���д΢�ź���ʹ�ô˹���\n����΢�źš��롰���Ta��Ϊ�����");
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
				textMessage.setContent("���Ȼظ����������ġ����Ƹ���������ʹ�ô˹���\n����΢�źš��롰���Ta��Ϊ�����");
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}
			// ��֤ż�������Ƿ�CD�� 
			if( !fromUserName.equals("o1szwtyIpKu2ZqZlE53BCDMOzNbk") && !fromUserName.equals("o1szwt3aDCJetCBhUNDxLHLJ1CVU") && !OuyuUsable(lastOuyuDate) ){
				if( ouyuTime < 3 ){
					// ���������ʹ�ù�ż������û�ﵽ����
					String sql3 = " update tb_member set ouyu_time = ouyu_time+1 where open_id = '"+fromUserName+"' ";
					DBoprate.stmt.execute(sql3);
				}else{
					textMessage.setContent("�������Ѿ��ɹ�ż����3�����ޣ����������԰�(ÿ�������¿���)");
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
			}else{
				// ����ǽ����һ��ʹ��ż��
				String sql3 = " update tb_member set ouyu_time = 1 where open_id = '"+fromUserName+"' ";
				DBoprate.stmt.execute(sql3);
			}
			// �ж�hobby
			String hobbyStr = "";
			switch(hobby){
			case 1:
				hobbyStr = "����Ӱ";
				break;
			case 2:
				hobbyStr = "����ʳ";
				break;
			case 3:
				hobbyStr = "����";
				break;
			case 4:
				hobbyStr = "���";
				break;
			case 5:
				hobbyStr = "����ϰ";
				break;
			case 6:
				hobbyStr = "Լ��";
				break;
			case 7:
				hobbyStr = "�˶�";
				break;
			}
			
			// ���ɱ���ͼ��
			Article article1 = new Article();
			String titleStr = "";
			if( sex == 0 ){
				titleStr = "��������Ҳ��ȥ"+hobbyStr+"��";
			}else{
				titleStr = "��������Ҳ��ȥ"+hobbyStr+"��";
			}
			article1.setTitle(titleStr);
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl("http://2.cmwechatweb.duapp.com");
			articleList.add(article1);
			
			// ��ѯ���ݿ��ȡ����hobby��ͬ������
			double[] around;
			around = DistanceCalculate.getAround(locationx, locationy, 10000);// �����5000��Ϊ��Χ�ľ�γ�ȷ�Χ
			double minLat = around[0];// ��Сγ�ȡ����ȣ����γ�ȡ�����
			double minLon = around[1];
			double maxLat = around[2];
			double maxLon = around[3];
			
			String sql2 = "select * from tb_member where locationx <> 0  "
					+ " and locationy > '" + minLon + "' "
					+ " and locationy < '" + maxLon + "' "
					+ " and locationx > '" + minLat + "' "
					+ " and locationx < '" + maxLat + "' "
					+ " and sex <> "+sex+" and hobby = "+hobby+" and weixin_num <> '����' ";
			ResultSet rs2 = DBoprate.stmt.executeQuery(sql2);
	
			// ��ȡ���������
			rs2.last();
			int rsSize = rs2.getRow();
			rs2.beforeFirst();
			// ���ѡȡ3����¼����newsmsg
			if( rsSize <= 3 ){
				if( rsSize == 0 ){
					rs2.close();
					textMessage.setContent("��������û����ȥ"+hobbyStr+"��Ta�ޣ�ȥ�������Ļ�һ�������Ta��ѡ�������԰�>_<");
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}else{
					// ����3����ȫ������articleList
					if( addRsIntoArticleList(rs2,articleList,locationx,locationy,sex) ){
						//����ɹ�����
						rs2.close();
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						// ʹż�����ܽ�����ȴʱ��
						Date date =new Date();
						SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");//������ʱ��
						String dateNow = sdf.format(date);
						String sql3 = " update tb_member set last_ouyu_date = '"+dateNow+"' where open_id = '"+fromUserName+"' ";
						DBoprate.stmt.execute(sql3);
					}else{
						rs2.close();
						textMessage.setContent("Ŭ��Ϊ��Ѱ���У�һ�������԰�~");
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
				}
			}else{
				// ����3�������ѡȡ����3������articleList
				if( addRsIntoArticleList2(rs2,articleList,locationx,locationy,rsSize,sex) ){
					//����ɹ�����
					rs2.close();
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					// ʹż�����ܽ�����ȴʱ��
					Date date =new Date();
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");//������ʱ��
					String dateNow = sdf.format(date);
					String sql3 = " update tb_member set last_ouyu_date = '"+dateNow+"' where open_id = '"+fromUserName+"' ";
					DBoprate.stmt.execute(sql3);
				}else{
					rs2.close();
					textMessage.setContent("Ŭ��Ϊ��Ѱ���У�һ�������԰�~");
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			textMessage.setContent("Ŭ��Ϊ��Ѱ���У�һ�������԰�~");
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}
		
		return respMessage;
	}
	
	

	///////////////////////////////////////////////////////////////////////////// ���������¡��������
	public static String retuenEnter(String url, int type, String fromUserName, String toUserName) {
=======
	/**
	 * �����ת��Ϊͼ����Ϣ
	 */

	// ���ɱ��ǽ����Ըǽ�����԰����
	public static String retuenEnter(String url, int type, String fromUserName,
			String toUserName) {
>>>>>>> remotes/CMwall/master
		String respMessage = null;

		// Ĭ�ϻظ����ı���Ϣ
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
<<<<<<< HEAD
=======
		textMessage.setFuncFlag(0);
>>>>>>> remotes/CMwall/master
		textMessage.setContent("rsToNewsMsg����");
		// ���ı���Ϣ����ת����xml�ַ���
		respMessage = MessageUtil.textMessageToXml(textMessage);

		// ����ͼ����Ϣ
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
<<<<<<< HEAD

		List<Article> articleList = new ArrayList<Article>();

		// all_waterfall���
		if (type == 3) {
			Article article1 = new Article();
			article1.setTitle(">>����鿴����������");
=======
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();

		// ���ǽ���
		if (type == 0) {
			Article article1 = new Article();
			article1.setTitle(">>������븽�����ǽ");
>>>>>>> remotes/CMwall/master
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
<<<<<<< HEAD
		// topic_waterfall���
		else if (type == 4) {
			Article article1 = new Article();
			article1.setTitle(">>������븽������");
=======
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
>>>>>>> remotes/CMwall/master
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

<<<<<<< HEAD
	
	
	

	// ���ݽ�������ɸ�������newsMsg
	public static String rsTopersonnalCenter(String fromUserName, String toUserName, String weixinID, int sex,String iconURL, String locationLabel) {
=======
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
>>>>>>> remotes/CMwall/master
		String respMessage = null;
		try {
			// Ĭ�ϻظ����ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
<<<<<<< HEAD
=======
			textMessage.setFuncFlag(0);
>>>>>>> remotes/CMwall/master
			textMessage.setContent("��ȡ�б�ʧ��~");
			// ���ı���Ϣ����ת����xml�ַ���
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// ����ͼ����Ϣ
			NewsMessage newsMessage = new NewsMessage();
			newsMessage.setToUserName(fromUserName);
			newsMessage.setFromUserName(toUserName);
			newsMessage.setCreateTime(new Date().getTime());
			newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
<<<<<<< HEAD
=======
			newsMessage.setFuncFlag(0);
>>>>>>> remotes/CMwall/master

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

<<<<<<< HEAD

			// ��������Ϊ�ҵ�������
			Article article3 = new Article();
			article3.setTitle("\n>�ҵ�������");
			article3.setDescription("");
			article3.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fliuyan_icon.jpg");
			article3.setUrl("http://2.cmwechatweb.duapp.com/my_msg.jsp?currentID='"+fromUserName+"'");
			articleList.add(article3);
=======
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
>>>>>>> remotes/CMwall/master

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
