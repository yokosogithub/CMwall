package org.dao;

import org.util.DistanceCalculate;
import org.util.MessageUtil;

public class Gouda {
	private int goudaID;
	private String openID;
	private String weixinID;
	private String iconURL;
	private int sex;
	private String content;
	private String description;
	private String createTime;
	private int type;
	private double locationX;
	private double locationY;
	private int replyCount;
	private int zanCount;
	private String distance;

	public void distanceCalc(double locationx, double locationy) {
		int intDistance = (int) DistanceCalculate.getDistance(this.locationX,
				this.locationY, locationx, locationy);
		distance = MessageUtil.simpleDistance2(intDistance);
	}

	public String getDistance() {
		return distance;
	}

	public int getGoudaID() {
		return goudaID;
	}

	public void setGoudaID(int goudaID) {
		this.goudaID = goudaID;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getWeixinID() {
		return weixinID;
	}

	public void setWeixinID(String weixinID) {
		this.weixinID = weixinID;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getLocationX() {
		return locationX;
	}

	public void setLocationX(double locationX) {
		this.locationX = locationX;
	}

	public double getLocationY() {
		return locationY;
	}

	public void setLocationY(double locationY) {
		this.locationY = locationY;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public int getZanCount() {
		return zanCount;
	}

	public void setZanCount(int zanCount) {
		this.zanCount = zanCount;
	}

}
