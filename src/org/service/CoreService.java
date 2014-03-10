package org.service;

import getfromwechat.GetFromWechat;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.dao.Biaobai;
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
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			// sql语句
			String sql = "";
			// 存放查询结果
			ResultSet rs = null;
			// 存放该查询结果的行等属性
			ResultSetMetaData rsmd = null;

			// 如果数据库成功连接
			if (DPconnOk) {

				// ////////////////////////////////////////////////// 文本消息 ///////////////////////////////////////////////////////
				if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
					// respContent = "您发送的是文本消息！";
					String reqContent = requestMap.get("Content");
					/*
					 * 一、如果会员用户数据库中没有该用户的OPEN ID 1.如果临时表中也没有改用户OPEN ID
					 * 将此条文字消息视为微信号存入临时表，并要求性别 2.如果临时表中存在该OPEN ID
					 * (1)如果该条消息为“mm”或 “gg”，则填入sex属性，并要求头像 (2)如果该条消息不为“mm”或
					 * “gg”，要求性别 3.如果临时表中存在该OPEN ID && 性别不为空 要求图片头像
					 */
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
										+ "请点击输入框旁小加号，将您的位置发送给小厘吧~";

							} else {
								respContent = "请正确回复性别，“mm”为女，“gg”为男。\n若已设置成功，请回复一张头像吧~\n（若您的网络不佳，可以回复“默认”使用随机精美头像）";
							}
						}
					}

					/*
					 * 二。如果会员用户数据库中有该用户open id && 该用户的locationX和locationY不为空
					 * 1.如果为“表白墙” 服务器返回附近表白列表 2.如果为“心愿墙” 服务器返回 3.如果为“发表白”
					 * 要求语音或文字表白，将state置为1 4.如果为“许愿望” && sex为女
					 * 要求语音或文字愿望，将state置为2 5.如果为其他内容
					 * (1)如果该用户state为1或2，将该条文字消息存入表白或心愿库，并将state置为0
					 * (2)如果该用户state为0，提示输入“表白墙” “心愿墙” “发表白” “发心愿”
					 */
					/*
					 * 三。如果会员用户数据库中有该用户open id && 该用户对应的locationX和locationY为空
					 * 提示“没有位置，臣妾做不到呀”
					 */

					// 如果会员表中有该openid
					else {
						sql = "select * from tb_member where open_id ='"+fromUserName+"' ";
						rs = DBoprate.stmt.executeQuery(sql);
						rs.next();
						double locationx = rs.getDouble("locationx");
						double locationy = rs.getDouble("locationy");
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
							

							else if (reqContent.equals("个人中心")) {
								String sqlStr = "select * from tb_member where open_id = '"+ fromUserName + "' ";
								ResultSet rss = DBoprate.stmt.executeQuery(sqlStr);
								rss.next();
								int sex = rss.getInt("sex");
								String weixinID = rss.getString("weixin_id");
								String locationLabel = rss.getString("location_label");
								String iconURL = rss.getString("icon_url");

								rss.close();

								ArrayList<ResultSet> resultArr = DBoprate.queryPersonnalCenter(fromUserName, sex);

								String responMsg = MessageUtil.rsTopersonnalCenter(resultArr,fromUserName, toUserName,weixinID, sex, iconURL,locationLabel);

								return responMsg;
							}
							
							
							else if( reqContent.equals("反馈") ){
								sql = "update tb_member set state = 4 where open_id = '"+fromUserName+"'";
								DBoprate.stmt.execute(sql);
								
								respContent = "您对厘米墙的建议或在使用中遇到的不满都可以回复此条消息进行吐槽\nPS:请一次性回复所有内容";
							}
							

							/*
							else if (reqContent.equals("表白")) {
								// 将state更新为1
								sql = "update tb_member set state = 1 where open_id = '"+fromUserName+"'";
								DBoprate.stmt.execute(sql);

								respContent = "回复图片作为表白封面，若直接回复文字表白内容将使用随机精美封面";

							} else if (reqContent.equals("许愿")) {
								sql = "select * from tb_member where open_id ='"+fromUserName+"' ";
								rs = DBoprate.stmt.executeQuery(sql);
								rs.next();
								int sex = rs.getInt("sex");
								if (sex == 1) {
									respContent = "大叔，许愿神马的还是交给妹纸好了，回复“新鲜事”勾搭附近的许愿妹子，然后帮她们实现愿望吧！";
								} else {
									// 将state更新为2
									sql = "update tb_member set state = 2 where open_id = '"+fromUserName+"'";
									DBoprate.stmt.execute(sql);

									respContent = "回复图片作为许愿封面，若直接回复文字许愿内容将使用随机精美封面";
								}

							} else if (reqContent.equals("留言")) {
								// 将state更新为3
								sql = "update tb_member set state = 3 where open_id = '"+fromUserName+"'";
								DBoprate.stmt.execute(sql);

								respContent = "回复图片作为封面，若直接回复文字留言内容将使用随机精美封面";
							}
							*/

							// 获取用户的微信数据，同步至厘米墙个人信息
							else if (reqContent.equals("同步微信")) {
								String userInfo = GetFromWechat.getUserInfo(MessageUtil.GET_USER_INFO_URL,MessageUtil.TOKEN, fromUserName);
								respContent = "同步微信" + userInfo;
							}

							else {
								sql = "select * from tb_member where open_id ='"+fromUserName+"' ";
								ResultSet rs2 = DBoprate.stmt.executeQuery(sql);
								rs2.next();
								// 获取用户ask for 消息状态
								int state = rs2.getInt("state");

								if (state == 0) {

									respContent = "回复：\n“新鲜事”查看附近新鲜事\n" + "“个人中心”定制个性资料\n" + "“反馈”提建议或吐个槽";

								} 
								else if( state == 4 ){
									sql = " insert into tb_advice (open_id,content) values ( '"+fromUserName+"','"+reqContent+"' ) ";
									DBoprate.stmt.execute(sql);
									
									sql = "update tb_member set state = 0 where open_id = '"+fromUserName+"'";
									DBoprate.stmt.execute(sql);
									
									respContent = "反馈已收录，感谢您的建议";
								}
								
								rs2.close();
								
								/*
								else if (state == 1) {
									// 该条消息被认为是表白
									String openID = fromUserName;
									String weixinID = rs2.getString("weixin_id");
									String iconURL = rs2.getString("icon_url");
									int sex = rs2.getInt("sex");
									String description = reqContent;

									// 如果为图片表白的描述
									if (if_imgbiaobai == 1) {
										sql = " update tb_msg set description = '"+description+ "' where open_id = '"+fromUserName+ "'and type=1 order by msg_id desc limit 1 ";
										DBoprate.stmt.execute(sql);

										respContent = "表白已收到，可通过附近新鲜事查看~";

										sql = "update tb_member set if_imgbiaobai = 0 where open_id = '"+fromUserName+"'";
										DBoprate.stmt.execute(sql);

										sql = "update tb_member set state = 0 where open_id = '"+fromUserName+"'";
										DBoprate.stmt.execute(sql);
									} else {
										// 如果表白内容过于短或者为垃圾内容
										boolean isPureNumber = description.matches("[0-9]+");
										boolean isisRepeatStr = MessageUtil.isRepeatStr(description);

										if (description.length() < 4 || isPureNumber || isisRepeatStr) {
											respContent = "不管你是大侠还是软妹，表白也有点诚意嘛，多加几个字试试吧~";
										} else {
											int type = 1;
											double locationX = rs2.getDouble("locationx");
											double locationY = rs2.getDouble("locationy");
											sql = "insert into tb_msg (open_id,weixin_id,icon_url,sex,description,type,locationx,locationy) values ('"
											+openID+"','"+weixinID+"','"+iconURL+"',"+sex+",'"+description+"',"+type+","+locationX+","+locationY+") ";
											DBoprate.stmt.execute(sql);

											respContent = "表白已收到，可通过附近新鲜事查看~";

											sql = "update tb_member set state = 0 where open_id = '"+fromUserName+"'";
											DBoprate.stmt.execute(sql);
										}
									}
								} else if (state == 2) {
									// 该条消息被认为是许愿
									String openID = fromUserName;
									String weixinID = rs2.getString("weixin_id");
									String iconURL = rs2.getString("icon_url");
									int sex = rs2.getInt("sex");
									String description = reqContent;

									// 该条文字为图片许愿的描述
									if (if_imgxinyuan == 1) {
										sql = " update tb_msg set description = '"+description+"' where open_id = '"+fromUserName+"'and type=2 order by msg_id desc limit 1 ";
										DBoprate.stmt.execute(sql);

										respContent = "许愿已收到，可通过附近新鲜事查看~";

										sql = "update tb_member set if_imgxinyuan = 0 where open_id = '"+fromUserName+"'";
										DBoprate.stmt.execute(sql);

										sql = "update tb_member set state = 0 where open_id = '"+ fromUserName + "'";
										DBoprate.stmt.execute(sql);
									} else {
										// 如果表白内容过于短或者为垃圾内容
										boolean isPureNumber = description.matches("[0-9]+");
										boolean isisRepeatStr = MessageUtil.isRepeatStr(description);

										if (description.length() < 4 || isPureNumber || isisRepeatStr) {
											respContent = "妹纸，许愿也有点诚意嘛，那么多汉子看着呢，多加几个字试试吧~";
										} else {
											// 文字心愿
											int type = 2;
											double locationX = rs2.getDouble("locationx");
											double locationY = rs2.getDouble("locationy");
											sql = "insert into tb_msg (open_id,weixin_id,icon_url,description,type,locationx,locationy) values ('"
											+openID+"','"+ weixinID+ "','"+ iconURL+ "','"+ description+ "',"+ type+ ", "+ locationX+ "," + locationY + ") ";
											DBoprate.stmt.execute(sql);

											respContent = "许愿已收到，可通过附近新鲜事查看~";

											sql = "update tb_member set state = 0 where open_id = '"+ fromUserName + "'";
											DBoprate.stmt.execute(sql);
										}
									}
								} else if (state == 3) {
									// 该条消息被认为是留言
									String openID = fromUserName;
									String weixinID = rs2.getString("weixin_id");
									String iconURL = rs2.getString("icon_url");
									int sex = rs2.getInt("sex");
									String description = reqContent;

									// 该条文字为图片留言的描述
									if (if_imggouda == 1) {
										sql = " update tb_msg set description = '"+ description+ "' where open_id = '"+ fromUserName+ "'and type=3 order by msg_id desc limit 1 ";
										DBoprate.stmt.execute(sql);

										respContent = "留言成功，可通过附近新鲜事查看~";

										sql = "update tb_member set if_imggouda = 0 where open_id = '"+ fromUserName + "'";
										DBoprate.stmt.execute(sql);

										sql = "update tb_member set state = 0 where open_id = '"+ fromUserName + "'";
										DBoprate.stmt.execute(sql);
									} else {
										// 如果勾搭内容过于短或者为垃圾内容
										boolean isPureNumber = description.matches("[0-9]+");
										boolean isisRepeatStr = MessageUtil.isRepeatStr(description);

										if (description.length() < 4 || isPureNumber || isisRepeatStr) {
											respContent = "不管你是大侠还是软妹，求勾搭也有点诚意嘛，多加几个字试试吧~";
										}
										// 为文字求勾搭
										else {
											int type = 3;
											double locationX = rs2.getDouble("locationx");
											double locationY = rs2.getDouble("locationy");
											sql = "insert into tb_msg (open_id,weixin_id,icon_url,sex,description,type,locationx,locationy) values ('"
											+ openID+ "','"+ weixinID+ "','"+ iconURL+ "',"+ sex+ ",'"+ description+ "',"+ type+ ","+ locationX+ ","+ locationY + ") ";
											DBoprate.stmt.execute(sql);

											respContent = "留言成功，可通过附近新鲜事查看~";

											sql = "update tb_member set state = 0 where open_id = '"+ fromUserName + "'";
											DBoprate.stmt.execute(sql);
										}
									}
								}*/
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
										+ "请点击输入框旁小加号，将您的位置发送给小厘吧~";
							}
						}
					}

					else {
						
						sql = " update tb_member set icon_url = '" + imgUrl+ "' where open_id = '" + fromUserName+ "' ";
						DBoprate.stmt.execute(sql);
						respContent = "修改头像成功~";
						
						/*
						int state = rs.getInt("state");

						if (state == 1) {
							String weixinID = rs.getString("weixin_id");
							String iconURL = rs.getString("icon_url");
							int sex = rs.getInt("sex");
							String content = imgUrl;
							int type = 1;
							double locationX = rs.getDouble("locationx");
							double locationY = rs.getDouble("locationy");

							sql = "insert into tb_msg (open_id,weixin_id,icon_url,sex,content,type,locationx,locationy) values ('"
							+fromUserName+"','"+ weixinID+ "','"+ iconURL+ "',"+ sex+ ",'"+ content+ "',"+ type+ ","+ locationX + "," + locationY + ") ";
							DBoprate.stmt.execute(sql);

							respContent = "为图片添加一段表白吧~";

							sql = "update tb_member set if_imgbiaobai = 1 where open_id = '"+ fromUserName + "'";
							DBoprate.stmt.execute(sql);
						} else if (state == 2) {
							String weixinID = rs.getString("weixin_id");
							String iconURL = rs.getString("icon_url");
							String content = imgUrl;
							int type = 2;
							double locationX = rs.getDouble("locationx");
							double locationY = rs.getDouble("locationy");

							sql = "insert into tb_msg (open_id,weixin_id,icon_url,content,type,locationx,locationy) values ('"
							+ fromUserName+ "','"+ weixinID+ "','"+ iconURL+ "','"+ content+ "',"+ type+ ", "+ locationX + "," + locationY + ") ";
							DBoprate.stmt.execute(sql);

							respContent = "添加一段文字许愿吧~";

							sql = "update tb_member set if_imgxinyuan = 1 where open_id = '"+ fromUserName + "'";
							DBoprate.stmt.execute(sql);
						} else if (state == 3) {
							String weixinID = rs.getString("weixin_id");
							String iconURL = rs.getString("icon_url");
							int sex = rs.getInt("sex");
							String content = imgUrl;
							int type = 3;
							double locationX = rs.getDouble("locationx");
							double locationY = rs.getDouble("locationy");

							sql = "insert into tb_msg (open_id,weixin_id,icon_url,sex,content,type,locationx,locationy) values ('"
							+ fromUserName+ "','"+ weixinID+ "','"+ iconURL+ "',"+ sex+ ",'"+ content+ "',"+ type+ ","+ locationX + "," + locationY + ") ";
							DBoprate.stmt.execute(sql);

							respContent = "添加一段文字留言吧~";

							sql = "update tb_member set if_imggouda = 1 where open_id = '"+ fromUserName + "'";
							DBoprate.stmt.execute(sql);
						}*/ 

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
						respContent = "您的位置信息已更新~\n" + "回复：\n“新鲜事”查看附近新鲜事\n" + "“个人中心”定制个性资料\n" + "“反馈”提建议或吐个槽";
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

				// ////////////////////////////////////////////////// 事件推送 ///////////////////////////////////////////////////////
				else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
					// 事件类型
					String eventType = requestMap.get("Event");
					// 订阅
					if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
						respContent = "欢迎加入厘米墙，先按照提示完成简单的注册吧，回复一个昵称~";
					}
					// 取消订阅
					else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
						String sql1 = "delete from tb_member where open_id = '"+ fromUserName + "'";
						DBoprate.stmt.execute(sql1);
						String sql2 = "delete from tb_tempmember where open_id = '"+ fromUserName + "'";
						DBoprate.stmt.execute(sql2);
					}
					// 自定义菜单点击事件
					else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
						// TODO 自定义菜单权没有开放，暂不处理该类消息
					}
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
		}

		return respMessage;
	}
}
