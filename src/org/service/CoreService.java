package org.service;

import getfromwechat.GetFromWechat;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.dao.Biaobai;
import org.dao.StaticUserInfo;
import org.dboprate.DBoprate;
import org.message.resp.NewsMessage;
import org.message.resp.TextMessage;
import org.util.DistanceCalculate;
import org.util.MessageUtil;
import org.util.Rs2Json;

/**
 * 核心服务类
 * 
 * @author 刘星
 * @date 2013-09-15
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static String processRequest(HttpServletRequest request, boolean DPconnOk) {
		// 公众号返回给用户的消息
		String respMessage = null;
		
		try {
			// 默认返回的文本消息内容
			String respContent = "喔偶，网络不给力，稍后再试试吧〜";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			

			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			//textMessage.setCreateTime(new Date().getTime());
			long time = 114311124;
			textMessage.setCreateTime(time);
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			// sql语句
			String sql = "";
			// 存放查询结果
			ResultSet rs = null;
			// 存放该查询结果的行等属性
			ResultSetMetaData rsmd = null;

			// 如果数据库成功连接
			if (DPconnOk) {
				
				// ////////////////////////////////////////////////// 事件推送 ///////////////////////////////////////////////////////
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					// 事件类型
					String eventType = requestMap.get("Event");
					// 订阅
					if (eventType.equals("subscribe") ) {
						respContent = "欢迎加入厘米墙，先按照提示完成简单的注册吧，回复一个昵称~";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
						return respMessage;
					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						String sql1 = "delete from tb_member where open_id = '"+ fromUserName + "'";
						DBoprate.stmt.execute(sql1);
						String sql2 = "delete from tb_tempmember where open_id = '"+ fromUserName + "'";
						DBoprate.stmt.execute(sql2);
					}
				}

				// ////////////////////////////////////////////////// 文本消息 ///////////////////////////////////////////////////////
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
					
					String reqContent = requestMap.get("Content");
					
					sql = "select * from tb_member where open_id ='"+ fromUserName + "' ";
					rs = DBoprate.stmt.executeQuery(sql);
					// 如果会员表中没有该用户openid
					if (!rs.next()) {
						sql = "select * from tb_tempmember where open_id ='"+ fromUserName + "' ";
						rs = DBoprate.stmt.executeQuery(sql);
						// 如果临时表中也没有该用户openid
						if (!rs.next()) {
							// 将openid存入临时表
							sql = "insert into tb_tempmember(open_id) value('"+ fromUserName + "')";
							DBoprate.stmt.execute(sql);
							// 将微信号存入临时表
							sql = "update tb_tempmember set weixin_id = '"+ reqContent + "' where open_id = '"+ fromUserName + "' ";
							DBoprate.stmt.execute(sql);
							respContent = "童鞋你是汉子还是软妹?~\n回复“mm”或“gg”";
						}
						// 如果临时表中已经存在该用户openid
						else {
							if (reqContent.equals("mm")|| reqContent.equals("gg")|| reqContent.equals("Gg")|| reqContent.equals("GG")
								|| reqContent.equals("MM")|| reqContent.equals("Mm")|| reqContent.equals("mM")|| reqContent.equals("gG")) {
								// 存入性别
								if (reqContent.equals("mm")|| reqContent.equals("MM")|| reqContent.equals("Mm")|| reqContent.equals("mM"))
									sql = "update tb_tempmember set sex = 0 where open_id = '"+ fromUserName + "' ";
								else
									sql = "update tb_tempmember set sex = 1 where open_id = '"+ fromUserName + "' ";
								DBoprate.stmt.execute(sql);
								// 要求头像
								respContent = "性别设置成功，回复一张图片作为头像吧~\n（如果您的网络不佳，可以回复“默认”使用随机精美头像，日后再做修改）";
							} else if (reqContent.equals("默认")) {

								// 将用户头像设置为系统随机
								int randomInt = (int) (Math.random() * 13 + 1);
								String iconURL = "http://bcs.duapp.com/cmtechnology-wechat-cmwall-files/randomIcon%2F"+ randomInt + ".jpeg";

								String sql1 = "update tb_tempmember set icon_url = '"+ iconURL+ "' where open_id ='"+ fromUserName + "'";
								DBoprate.stmt.execute(sql1);

								// 将用户移动至tb_member
								sql = "insert into tb_member (open_id,weixin_id,icon_url,sex) "
										+ "select open_id,weixin_id,icon_url,sex from tb_tempmember "
										+ "where open_id = '"+ fromUserName+ "' ";
								DBoprate.stmt.execute(sql);
								sql = " delete from tb_tempmember where open_id = '"+ fromUserName + "' ";
								DBoprate.stmt.execute(sql);

								respContent = "头像设置成功，今后直接回复图片即可修改头像"
										+ "\n---------------\n"
										+ "请点击输入框旁小加号，将您的位置发送给小厘，参与附近新鲜玩法";

							} else {
								respContent = "请正确回复性别，“mm”为女，“gg”为男。\n若已设置成功，请回复一张头像吧~\n（若您的网络不佳，可以回复“默认”使用随机精美头像）";
							}
						}
					}
					

					// 如果会员表中有该openid
					else {
						sql = "select * from tb_member where open_id ='"+fromUserName+"' ";
						rs = DBoprate.stmt.executeQuery(sql);
						rs.next();
						///////////////////////// 取出该用户的信息并存入全局单例对象中 /////////////////////////
						String openID = rs.getString("open_id");
						String weixinID = rs.getString("weixin_id");
						String iconURL = rs.getString("icon_url");
						int sex = rs.getInt("sex");
						int state = rs.getInt("state");
						double locationx = rs.getDouble("locationx");
						double locationy = rs.getDouble("locationy");
						String locationLabel = rs.getString("location_label");
						int hobby = rs.getInt("hobby");
						String school = rs.getString("school");
						String profession = rs.getString("profession");
						String grade = rs.getString("grade");
						String shuoshuo = rs.getString("shuoshuo");
						String loveState = rs.getString("love_state");
						String weixinNum = rs.getString("weixin_num");
						String lastOuyuDate = String.valueOf(rs.getDate("last_ouyu_date"));
						int ouyuTime = rs.getInt("ouyu_time");
						// 初始化全局单例对象
						//StaticUserInfo.initInstance(openID, weixinID, iconURL, sex, state, locationx, locationy, locationLabel, hobby, school, profession, grade, shuoshuo, loveState, weixinNum, lastOuyuDate, ouyuTime);
						
						
						if (locationy < 1.0) {
							respContent = "没有位置，臣妾做不到呀~\n点击输入框旁的小加号，将您的位置告诉我吧>_<";
						} else {
							
							if( reqContent.equals("新鲜事") ){
								String locationX = URLEncoder.encode(String.valueOf(locationx), "UTF-8");
								String locationY = URLEncoder.encode(String.valueOf(locationy), "UTF-8");
								String currentID = URLEncoder.encode(fromUserName, "UTF-8");
								
								String url = "http://2.cmwechatweb.duapp.com/all_waterfall.jsp?locationx='"+locationX+"'&locationy='"+locationY+"'&currentID='"+currentID+"' ";

								// 生成进入all_waterfall的入口图文
								String responMsg = MessageUtil.retuenEnter(url, 3, fromUserName, toUserName);

								return responMsg;
							}
							
							else if( reqContent.equals("话题") ){
								String locationX = URLEncoder.encode(String.valueOf(locationx), "UTF-8");
								String locationY = URLEncoder.encode(String.valueOf(locationy), "UTF-8");
								String currentID = URLEncoder.encode(fromUserName, "UTF-8");
								
								String url = "http://2.cmwechatweb.duapp.com/topic_waterfall.jsp?locationx='"+locationX+"'&locationy='"+locationY+"'&currentID='"+currentID+"' ";

								// 生成进入topic_waterfall的入口图文
								String responMsg = MessageUtil.retuenEnter(url, 4, fromUserName, toUserName);

								return responMsg;
							}
							
							else if( reqContent.equals("偶遇") ){
								String responMsg = MessageUtil.getOuyuUserList(locationx,locationy,fromUserName,toUserName);
								return responMsg;
							}

							else if (reqContent.equals("个人中心")) {
					
								String responMsg = MessageUtil.rsTopersonnalCenter(fromUserName, toUserName,weixinID, sex, iconURL,locationLabel);

								return responMsg;
							}
							
							
							else if( reqContent.equals("反馈") ){
								sql = "update tb_member set state = 4 where open_id = '"+fromUserName+"'";
								DBoprate.stmt.execute(sql);
								
								respContent = "您对厘米墙的建议或在使用中遇到的不满都可以回复此条消息进行吐槽\nPS:请一次性回复所有内容";
							}
							

							else {

								if (state == 0) {

									respContent = "回复：\n“偶遇”遇见兴趣相同的Ta\n\n" + "“新鲜事”查看附近说说\n\n" + "“话题”参与附近趣味问答\n\n" + "“反馈”提建议或吐个槽\n\n" + "“个人中心”定制个性资料或查看我的新鲜事" ;

								} 
								else if( state == 4 ){
									sql = " insert into tb_advice (open_id,content) values ( '"+fromUserName+"','"+reqContent+"' ) ";
									DBoprate.stmt.execute(sql);
									
									sql = "update tb_member set state = 0 where open_id = '"+fromUserName+"'";
									DBoprate.stmt.execute(sql);
									
									respContent = "反馈已收录，感谢您的建议";
								}
								
							}
						}
					}
				}

				// ////////////////////////////////////////////////// 图片消息 ///////////////////////////////////////////////////////
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
					// respContent = "您发送的是图片消息！";
					String imgUrl = requestMap.get("PicUrl");
					/*
					 * 一。如果会员用户数据库中没有该用户open id 1.如果临时表中有该用户open
					 * id，且头像为空，填写头像路径，将该条数据填入会员用户数据表，并在临时表中删除该条记录，之后要求坐标
					 * 2.如果临时表中不存在open id，要求微信号
					 */
					sql = "select * from tb_member where open_id ='"+ fromUserName + "'";
					rs = DBoprate.stmt.executeQuery(sql);
					// 如果会员表中没有该用户openid
					if (!rs.next()) {
						sql = "select * from tb_tempmember where open_id ='"+ fromUserName + "' ";
						rs = DBoprate.stmt.executeQuery(sql);
						// 如果临时表中也没有该用户openid
						if (!rs.next()) {
							respContent = "先回复微信号~";
						} else {
							boolean success = true;
							// 临时表中存在该openid 设置头像
							try {
								sql = " update tb_tempmember set icon_url = '"+ imgUrl + "' " + "where open_id = '"+ fromUserName + "' ";
								DBoprate.stmt.execute(sql);
							} catch (Exception e) {
								success = false;
								e.printStackTrace();
								respContent = "设置头像失败，再试试吧~";
							}
							if (success) {
								// 将该用户从临时表中删除，并移至会员表
								sql = "insert into tb_member (open_id,weixin_id,icon_url,sex) "
										+ "select open_id,weixin_id,icon_url,sex from tb_tempmember "
										+ "where open_id = '"+ fromUserName+ "' ";
								DBoprate.stmt.execute(sql);
								sql = " delete from tb_tempmember where open_id = '"+ fromUserName + "' ";
								DBoprate.stmt.execute(sql);
								respContent = "你是厘米墙的一员啦，今后直接回复图片即可修改头像"
										+ "\n---------------\n"
										+ "请点击输入框旁小加号，将您的位置发送给小厘，参与附近新鲜玩法";
							}
						}
					}

					else {
						
						sql = " update tb_member set icon_url = '" + imgUrl+ "' where open_id = '" + fromUserName+ "' ";
						DBoprate.stmt.execute(sql);
						respContent = "修改头像成功~";
						
					}

				}

				// ////////////////////////////////////////////////// 地理位置消息  ///////////////////////////////////////////////////////
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
					// respContent = "您发送的是地理位置消息！";
					// String locationX = requestMap.get("Location_X");
					// ...
					double locationX = Double.valueOf(requestMap
							.get("Location_X"));
					double locationY = Double.valueOf(requestMap
							.get("Location_Y"));
					String label = requestMap.get("Label");

					sql = "select * from tb_member where open_id ='"
							+ fromUserName + "' ";
					rs = DBoprate.stmt.executeQuery(sql);
					// 如果已经是会员
					if (rs.next()) {
						sql = " update tb_member set locationx = " + locationX
								+ ", locationy = " + locationY
								+ " , location_label='" + label + "'"
								+ "where open_id = '" + fromUserName + "' ";
						DBoprate.stmt.execute(sql);
						respContent = "您的位置已更新，回复:\n" + "“偶遇”遇见兴趣相同的Ta\n\n" +"“新鲜事”查看附近说说\n\n" + "“话题”参与附近趣味问答\n\n" + "“反馈”提建议或吐个槽\n\n" + "“个人中心”定制个性资料或查看我的新鲜事";
					} else {
						respContent = "您好像还没有完成注册噢，赶紧按照提示加入我们吧~";
					}
				}

				// ////////////////////////////////////////////////// 链接消息 ///////////////////////////////////////////////////////
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
					respContent = "十分抱歉，小厘暂时还不能处理链接消息噢~";
				}

				// ////////////////////////////////////////////////// 音频消息 ///////////////////////////////////////////////////////
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
					respContent = "客官不可以~语音留言功能暂未开放";

				}

				 
				
			} else {
				// 如果数据库没连接上
				respContent = "连接数据库错误";
			}

			rs.close();
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
			// 输出错误日志
			Logger logger = Logger. getLogger("name");
			logger.log(Level.INFO, "error：\n"+e.getMessage());
		}

		return respMessage;
	}
}
