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
	
	

	
	// 验证偶遇功能是否CD完（今日是否已经使用过偶遇功能）
	public static boolean OuyuUsable(String lastOuyuDate){
		if( lastOuyuDate.equals("9999-09-09") ){
			return true;
		}
		
		// 与当前时间对比
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
	
	
	// 将不超过三个人的附近偶遇结果集加入articleList
	public static boolean addRsIntoArticleList(ResultSet rs, List<Article> articleList, double myLocationx, double myLocationy, int mySex){
		try{
			while( rs.next() ){
				// 获取个人信息
				String openID = rs.getString("open_id");
				String weixinID = rs.getString("weixin_id");
				String iconURL = rs.getString("icon_url");
				int sex = rs.getInt("sex");
				double locationx = rs.getDouble("locationx");
				double locationy = rs.getDouble("locationy");
				// 计算距离
				int intDistance = (int) DistanceCalculate.getDistance(myLocationx,myLocationy, locationx, locationy);
				String distance = MessageUtil.simpleDistance2(intDistance);
				// 生成文字内容
				String itemContent = ""; 
				if( sex == 0 ){ 
					String sexImg = emoji(0x1F6BA);
					itemContent = sexImg + weixinID +"\n" + distance + "km以内" ;
				}else{ 
					String sexImg = emoji(0x1F6B9);
					itemContent = sexImg + weixinID +"\n" + distance + "km以内" ;
				}
				// 加入articleList
				Article article = new Article();
				article.setTitle(itemContent);
				article.setDescription("");
				article.setPicUrl(iconURL);
				article.setUrl("http://2.cmwechatweb.duapp.com/mypage_scan.jsp?taOpenID='"+openID+"'&ifSelf=false");
				articleList.add(article);
			}
			String buttomTitle = "";
			if( mySex == 1 ){
				buttomTitle = "点击头像查看资料，约Ta就加Ta微信吧>_<";
			}else{
				buttomTitle = "点击头像查看资料，加Ta微信相互了解一下吧>_<";
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
	
	
	// 将超过三个人的附近偶遇结果集随机加三个进入articleList
	public static boolean addRsIntoArticleList2(ResultSet rs, List<Article> articleList, double myLocationx, double myLocationy, int rsSize, int mySex){
		// 获取随机的三人下标
		ArrayList<Integer> intRandomArr = CalculateUtil.getRandomList(rsSize);
		
		try{
			while( rs.next() ){
				int rowNow = rs.getRow();
				// 如果是随机获取的下标
				if( rowNow == intRandomArr.get(0) || rowNow == intRandomArr.get(1) || rowNow == intRandomArr.get(2) ){
					// 获取个人信息
					String openID = rs.getString("open_id");
					String weixinID = rs.getString("weixin_id");
					String iconURL = rs.getString("icon_url");
					int sex = rs.getInt("sex");
					double locationx = rs.getDouble("locationx");
					double locationy = rs.getDouble("locationy");
					// 计算距离
					int intDistance = (int) DistanceCalculate.getDistance(myLocationx,myLocationy, locationx, locationy);
					String distance = MessageUtil.simpleDistance2(intDistance);
					// 生成文字内容
					String itemContent = ""; 
					if( sex == 0 ){ 
						String sexImg = emoji(0x1F6BA);
						itemContent = sexImg + weixinID +"\n" + distance + "km以内" ;
					}else{ 
						String sexImg = emoji(0x1F6B9);
						itemContent = sexImg + weixinID +"\n" + distance + "km以内" ;
					}
					// 加入articleList
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
				buttomTitle = "点击头像查看资料，约Ta就加Ta微信吧>_<";
			}else{
				buttomTitle = "点击头像查看资料，加Ta微信相互了解一下吧>_<";
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

	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////// 获取附近的偶遇结果news
	public static String getOuyuUserList(double locationx, double locationy, String fromUserName, String toUserName){
		String respMessage = null;
		
		// 默认回复此文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		//textMessage.setContent("error");
		// 将文本消息对象转换成xml字符串
		//respMessage = MessageUtil.textMessageToXml(textMessage);
		
		// 创建图文消息
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
			// 首先获取该用户的个人信息
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
			
			// 先判断用户资料是否完善
			if( hobby == 0 || weixinNum.equals("保密") ){
				if( weixinNum.equals("保密") ){
					textMessage.setContent("请先回复“个人中心”完善个人资料、填写微信号再使用此功能\n（“微信号”与“想和Ta”为必填项）");
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
				textMessage.setContent("请先回复“个人中心”完善个人资料再使用此功能\n（“微信号”与“想和Ta”为必填项）");
				respMessage = MessageUtil.textMessageToXml(textMessage);
				return respMessage;
			}
			// 验证偶遇功能是否CD中 
			if( !fromUserName.equals("o1szwtyIpKu2ZqZlE53BCDMOzNbk") && !fromUserName.equals("o1szwt3aDCJetCBhUNDxLHLJ1CVU") && !OuyuUsable(lastOuyuDate) ){
				if( ouyuTime < 3 ){
					// 如果今天曾使用过偶遇但还没达到三次
					String sql3 = " update tb_member set ouyu_time = ouyu_time+1 where open_id = '"+fromUserName+"' ";
					DBoprate.stmt.execute(sql3);
				}else{
					textMessage.setContent("您今天已经成功偶遇过3次了噢，明天再试试吧(每日零点更新开放)");
					respMessage = MessageUtil.textMessageToXml(textMessage);
					return respMessage;
				}
			}else{
				// 如果是今天第一次使用偶遇
				String sql3 = " update tb_member set ouyu_time = 1 where open_id = '"+fromUserName+"' ";
				DBoprate.stmt.execute(sql3);
			}
			// 判断hobby
			String hobbyStr = "";
			switch(hobby){
			case 1:
				hobbyStr = "看电影";
				break;
			case 2:
				hobbyStr = "吃美食";
				break;
			case 3:
				hobbyStr = "闲聊";
				break;
			case 4:
				hobbyStr = "逛街";
				break;
			case 5:
				hobbyStr = "上自习";
				break;
			case 6:
				hobbyStr = "约会";
				break;
			case 7:
				hobbyStr = "运动";
				break;
			}
			
			// 生成标题图文
			Article article1 = new Article();
			String titleStr = "";
			if( sex == 0 ){
				titleStr = "附近的他也想去"+hobbyStr+"：";
			}else{
				titleStr = "附近的她也想去"+hobbyStr+"：";
			}
			article1.setTitle(titleStr);
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl("http://2.cmwechatweb.duapp.com");
			articleList.add(article1);
			
			// 查询数据库获取附近hobby相同的异性
			double[] around;
			around = DistanceCalculate.getAround(locationx, locationy, 10000);// 获得以5000米为范围的经纬度范围
			double minLat = around[0];// 最小纬度、经度，最大纬度、经度
			double minLon = around[1];
			double maxLat = around[2];
			double maxLon = around[3];
			
			String sql2 = "select * from tb_member where locationx <> 0  "
					+ " and locationy > '" + minLon + "' "
					+ " and locationy < '" + maxLon + "' "
					+ " and locationx > '" + minLat + "' "
					+ " and locationx < '" + maxLat + "' "
					+ " and sex <> "+sex+" and hobby = "+hobby+" and weixin_num <> '保密' ";
			ResultSet rs2 = DBoprate.stmt.executeQuery(sql2);
	
			// 获取结果集行数
			rs2.last();
			int rsSize = rs2.getRow();
			rs2.beforeFirst();
			// 随机选取3条记录加入newsmsg
			if( rsSize <= 3 ){
				if( rsSize == 0 ){
					rs2.close();
					textMessage.setContent("附近好像没有想去"+hobbyStr+"的Ta噢，去个人中心换一个“想和Ta”选项再试试吧>_<");
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}else{
					// 不足3条，全部加入articleList
					if( addRsIntoArticleList(rs2,articleList,locationx,locationy,sex) ){
						//如果成功插入
						rs2.close();
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);
						// 使偶遇功能进入冷却时间
						Date date =new Date();
						SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");//完整的时间
						String dateNow = sdf.format(date);
						String sql3 = " update tb_member set last_ouyu_date = '"+dateNow+"' where open_id = '"+fromUserName+"' ";
						DBoprate.stmt.execute(sql3);
					}else{
						rs2.close();
						textMessage.setContent("努力为您寻找中，一会再试试吧~");
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}
				}
			}else{
				// 超过3条，随机选取其中3条加入articleList
				if( addRsIntoArticleList2(rs2,articleList,locationx,locationy,rsSize,sex) ){
					//如果成功插入
					rs2.close();
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					// 使偶遇功能进入冷却时间
					Date date =new Date();
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");//完整的时间
					String dateNow = sdf.format(date);
					String sql3 = " update tb_member set last_ouyu_date = '"+dateNow+"' where open_id = '"+fromUserName+"' ";
					DBoprate.stmt.execute(sql3);
				}else{
					rs2.close();
					textMessage.setContent("努力为您寻找中，一会再试试吧~");
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			textMessage.setContent("努力为您寻找中，一会再试试吧~");
			respMessage = MessageUtil.textMessageToXml(textMessage);
		}
		
		return respMessage;
	}
	
	

	///////////////////////////////////////////////////////////////////////////// 生成新鲜事、话题入口
	public static String retuenEnter(String url, int type, String fromUserName, String toUserName) {
=======
	/**
	 * 结果集转化为图文消息
	 */

	// 生成表白墙、许愿墙、留言板入口
	public static String retuenEnter(String url, int type, String fromUserName,
			String toUserName) {
>>>>>>> remotes/CMwall/master
		String respMessage = null;

		// 默认回复此文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
<<<<<<< HEAD
=======
		textMessage.setFuncFlag(0);
>>>>>>> remotes/CMwall/master
		textMessage.setContent("rsToNewsMsg方法");
		// 将文本消息对象转换成xml字符串
		respMessage = MessageUtil.textMessageToXml(textMessage);

		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
<<<<<<< HEAD

		List<Article> articleList = new ArrayList<Article>();

		// all_waterfall入口
		if (type == 3) {
			Article article1 = new Article();
			article1.setTitle(">>点击查看附近新鲜事");
=======
		newsMessage.setFuncFlag(0);

		List<Article> articleList = new ArrayList<Article>();

		// 表白墙入口
		if (type == 0) {
			Article article1 = new Article();
			article1.setTitle(">>点击进入附近表白墙");
>>>>>>> remotes/CMwall/master
			article1.setDescription("");
			article1.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fguanzhu_title.jpg");
			article1.setUrl(url);
			articleList.add(article1);
		}
<<<<<<< HEAD
		// topic_waterfall入口
		else if (type == 4) {
			Article article1 = new Article();
			article1.setTitle(">>点击参与附近话题");
=======
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
	
	
	

	// 根据结果集生成个人中心newsMsg
	public static String rsTopersonnalCenter(String fromUserName, String toUserName, String weixinID, int sex,String iconURL, String locationLabel) {
=======
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
>>>>>>> remotes/CMwall/master
		String respMessage = null;
		try {
			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
<<<<<<< HEAD
=======
			textMessage.setFuncFlag(0);
>>>>>>> remotes/CMwall/master
			textMessage.setContent("获取列表失败~");
			// 将文本消息对象转换成xml字符串
			respMessage = MessageUtil.textMessageToXml(textMessage);

			// 创建图文消息
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

<<<<<<< HEAD

			// 第三列作为我的新鲜事
			Article article3 = new Article();
			article3.setTitle("\n>我的新鲜事");
			article3.setDescription("");
			article3.setPicUrl("http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/image%2Fliuyan_icon.jpg");
			article3.setUrl("http://2.cmwechatweb.duapp.com/my_msg.jsp?currentID='"+fromUserName+"'");
			articleList.add(article3);
=======
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
>>>>>>> remotes/CMwall/master

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
