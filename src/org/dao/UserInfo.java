package org.dao;

/**
 * @author rickie
 * 封装一个用户的个人信息
 * 用来显示在个人主页上的信息
 */

public class UserInfo {
	private String iconURL = null;
	private String weixinID = null;
	private int hobby;
	private String school = null;
	private String profession = null;
	private String grade = null;
	private String shuoshuo = null;
	private String lovestate = null;
	private String weixinNum = null;
	private int sex = 3;
	
	
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getLovestate() {
		return lovestate;
	}
	public void setLovestate(String lovestate) {
		this.lovestate = lovestate;
	}
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
	public int getHobby() {
		return hobby;
	}
	public void setHobby(int hobby) {
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
