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
import org.dao.Xinyuan;

import net.sf.json.JSONArray;

public class Rs2Json {
	
	public static JSONArray rs2Json( ResultSet rs , int type, double locationx, double locationy ) throws SQLException{
		JSONArray jArr = new JSONArray();
		
		// 如果是封装附近表白墙结果集
		if( type == 0 ){
			List<Biaobai> biaobaiList = new ArrayList<Biaobai>();
			
			while(rs.next()){
				Biaobai biaobai = new Biaobai();
				
				biaobai.setBiaobaiID(rs.getInt("biaobai_id"));
				biaobai.setOpenID(rs.getString("open_id"));
				biaobai.setWeixinID(rs.getString("weixin_id"));
				biaobai.setIconURL(rs.getString("icon_url"));
				biaobai.setSex(rs.getInt("sex"));
				biaobai.setContent(rs.getString("content"));
				biaobai.setDescription(rs.getString("description"));
				
				Timestamp time = rs.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				biaobai.setCreateTime(create_time);
				
				biaobai.setType(rs.getInt("type"));
				biaobai.setLocationX(rs.getDouble("locationx"));
				biaobai.setLocationY(rs.getDouble("locationy"));
				biaobai.setReplyCount(rs.getInt("reply_count"));
				biaobai.setZanCount(rs.getInt("zan_count"));
				biaobai.distanceCalc(locationx, locationy);
				
				biaobaiList.add(biaobai);
			}
			jArr = JSONArray.fromObject(biaobaiList);
		}
		
		// 如果是封装附近许愿墙结果集
		else if( type == 1 ){
			List<Xinyuan> xinyuanList = new ArrayList<Xinyuan>();
			
			while(rs.next()){
				Xinyuan xinyuan = new Xinyuan();
				
				xinyuan.setXinyuanID(rs.getInt("xinyuan_id"));
				xinyuan.setOpenID(rs.getString("open_id"));
				xinyuan.setWeixinID(rs.getString("weixin_id"));
				xinyuan.setIconURL(rs.getString("icon_url"));
				xinyuan.setSex(0);
				xinyuan.setContent(rs.getString("content"));
				xinyuan.setDescription(rs.getString("description"));
				
				Timestamp time = rs.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				xinyuan.setCreateTime(create_time);
				
				xinyuan.setType(rs.getInt("type"));
				xinyuan.setLocationX(rs.getDouble("locationx"));
				xinyuan.setLocationY(rs.getDouble("locationy"));
				xinyuan.setReplyCount(rs.getInt("reply_count"));
				xinyuan.setZanCount(rs.getInt("zan_count"));
				xinyuan.distanceCalc(locationx, locationy);
				
				xinyuanList.add(xinyuan);
			}
			jArr = JSONArray.fromObject(xinyuanList);
		}
		
		// 如果是封装附近留言板结果集
		else if( type == 2 ){
			List<Gouda> goudaList = new ArrayList<Gouda>();
			
			while(rs.next()){
				Gouda gouda = new Gouda();
				
				gouda.setGoudaID(rs.getInt("gouda_id"));
				gouda.setOpenID(rs.getString("open_id"));
				gouda.setWeixinID(rs.getString("weixin_id"));
				gouda.setIconURL(rs.getString("icon_url"));
				gouda.setSex(rs.getInt("sex"));
				gouda.setContent(rs.getString("content"));
				gouda.setDescription(rs.getString("description"));
				
				Timestamp time = rs.getTimestamp("create_time");
				String createTime2 = time.toString();
				String[] strs = createTime2.split("[:]");
				String create_time = strs[0] + ":" + strs[1];
				gouda.setCreateTime(create_time);
				
				gouda.setType(rs.getInt("type"));
				gouda.setLocationX(rs.getDouble("locationx"));
				gouda.setLocationY(rs.getDouble("locationy"));
				gouda.setReplyCount(rs.getInt("reply_count"));
				gouda.setZanCount(rs.getInt("zan_count"));
				gouda.distanceCalc(locationx, locationy);
				
				goudaList.add(gouda);
			}
			jArr = JSONArray.fromObject(goudaList);
		}
		// 如果是查找附近所有状态
		else if( type == 3 ){
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
				msg.setReplyCount(rs.getInt("reply_count"));
				msg.setZanCount(rs.getInt("zan_count"));
				msg.distanceCalc(locationx, locationy);
				
				msgList.add(msg);
			}
			jArr = JSONArray.fromObject(msgList);
		}
		
		return jArr;
	}

}
