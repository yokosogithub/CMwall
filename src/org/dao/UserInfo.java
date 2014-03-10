package org.dao;

/**
 * @author rickie
 * 封装一个用户的个人信息
 * 用来显示在个人主页上的信息
 */

public class UserInfo {
	private String iconURL = null;
	private String weixinID = null;
	private String hobby = null;
	private String school = null;
	private String shuoshuo = null;
	private String weixinNum = null;
	private int sex = 3;
	public String getIconURL() {
		return iconURL;
	}
	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
	public String getWeixinID() {
		return weixinID;
	}
	public void setWeixinID(String weixinID) {
		this.weixinID = weixinID;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getShuoshuo() {
		return shuoshuo;
	}
	public void setShuoshuo(String shuoshuo) {
		this.shuoshuo = shuoshuo;
	}
	public String getWeixinNum() {
		return weixinNum;
	}
	public void setWeixinNum(String weixinNum) {
		this.weixinNum = weixinNum;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	
}
