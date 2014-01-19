package org.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.dao.Biaobai;
import org.dao.Gouda;
import org.dao.Msg;
import org.dao.Reply;
import org.dao.Topic;
import org.dao.Xinyuan;

import net.sf.json.JSONArray;

public class Rs2Json {
	
	public static JSONArray getMyJsonArr( ResultSet rs ){
		JSONArray jArr = new JSONArray();
		
		List<Msg> msgList = new ArrayList<Msg>();
		
		try{
			while(rs.next()){
				Msg msg = new Msg();
				
				msg.setMsgID(rs.getInt("msg_id"));
				msg.setOpenID(rs.getString("open_id"));
				msg.setWeixinID(rs.getString("weixin_id"));
				msg.setIconURL(rs.getString("icon_url"));
				msg.setSex(rs.getInt("sex"));
				msg.setContent(rs.getString("content"));
				msg.setDescription(rs.getString("description"));
				
				Timestamp time = rs.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				msg.setCreateTime(create_time);
				
				msg.setType(rs.getInt("type"));
				msg.setLocationX(rs.getDouble("locationx"));
				msg.setLocationY(rs.getDouble("locationy"));
				msg.setLocationLabel(rs.getString("location_label"));
				msg.setReplyCount(rs.getInt("reply_count"));
				msg.setZanCount(rs.getInt("zan_count"));
				msg.setDistance("0");
				
				msgList.add(msg);
			}
			
			jArr = JSONArray.fromObject(msgList);
		}catch (Exception e) {
			
		}
		
		return jArr;
	}
	
	
	public static JSONArray rs2Json( ResultSet rs , int type, double locationx, double locationy ) throws SQLException{
		JSONArray jArr = new JSONArray();
		
		
		// 如果是查找附近所有状态
		if( type == 3 ){
			List<Msg> msgList = new ArrayList<Msg>();
			
			while(rs.next()){
				Msg msg = new Msg();
				
				msg.setMsgID(rs.getInt("msg_id"));
				msg.setOpenID(rs.getString("open_id"));
				msg.setWeixinID(rs.getString("weixin_id"));
				msg.setIconURL(rs.getString("icon_url"));
				msg.setSex(rs.getInt("sex"));
				msg.setContent(rs.getString("content"));
				msg.setDescription(rs.getString("description"));
				
				Timestamp time = rs.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				msg.setCreateTime(create_time);
				
				msg.setType(rs.getInt("type"));
				msg.setLocationX(rs.getDouble("locationx"));
				msg.setLocationY(rs.getDouble("locationy"));
				msg.setLocationLabel(rs.getString("location_label"));
				msg.setReplyCount(rs.getInt("reply_count"));
				msg.setZanCount(rs.getInt("zan_count"));
				msg.distanceCalc(locationx, locationy);
				
				msgList.add(msg);
			}
			jArr = JSONArray.fromObject(msgList);
		}
		// 如果是查找附近的话题
		else if( type == 4 ){
			List<Topic> topicList = new ArrayList<Topic>();
			
			while(rs.next()){
				Topic topic = new Topic();
				
				topic.setTopicID(rs.getInt("topic_id"));
				topic.setOpenID(rs.getString("open_id"));
				topic.setWeixinID(rs.getString("weixin_id"));
				topic.setSex(rs.getInt("sex"));
				topic.setContent(rs.getString("content"));
				// 处理时间格式
				Timestamp time = rs.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				topic.setCreateTime(create_time);
				topic.setLocationx(rs.getDouble("locationx"));
				topic.setLocationy(rs.getDouble("locationy"));
				topic.setHeat(rs.getInt("heat"));
				topic.distanceCalc(locationx, locationy);
				
				topicList.add(topic);
			}
			jArr = JSONArray.fromObject(topicList);
		}
		
		return jArr;
	}

}
