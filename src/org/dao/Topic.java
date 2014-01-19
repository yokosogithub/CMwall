package org.dao;

import org.util.DistanceCalculate;
import org.util.MessageUtil;

public class Topic {
	private int topicID;
	private String openID;
	private String weixinID;
	private int sex;
	private String content;
	private String createTime;
	private double locationx;
	private double locationy;
	private String distance;
	private int heat;
	
	
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getWeixinID() {
		return weixinID;
	}

	public void setWeixinID(String weixinID) {
		this.weixinID = weixinID;
	}

	public void distanceCalc(double locationx, double locationy){
		int intDistance = (int) DistanceCalculate.getDistance(this.locationx,this.locationy, locationx, locationy);
		distance = MessageUtil.simpleDistance2(intDistance);
	}
	
	public double getLocationx() {
		return locationx;
	}
	public void setLocationx(double locationx) {
		this.locationx = locationx;
	}
	public double getLocationy() {
		return locationy;
	}
	public void setLocationy(double locationy) {
		this.locationy = locationy;
	}
	public int getTopicID() {
		return topicID;
	}
	public void setTopicID(int topicID) {
		this.topicID = topicID;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getHeat() {
		return heat;
	}
	public void setHeat(int heat) {
		this.heat = heat;
	}
}
