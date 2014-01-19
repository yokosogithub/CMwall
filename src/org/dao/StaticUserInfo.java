package org.dao;

/**
 * @author rickie
 * @单例模式
 * @func 在一次对服务器的request/response中存储当前用户的个人信息
 * 		 以防多次"select * from tb_member where open_id = ?"
 */

public class StaticUserInfo {
	
	// 单例对象
	private static StaticUserInfo userInfo = null;

	private String openID;
	private String weixinID;
	private String iconURL;
	private int sex;
	private int state;
	private double locationx;
	private double locationy;
	private String locationLabel;
	private int hobby;
	private String school;
	private String profession;
	private String grade;
	private String shuoshuo;
	private String loveState;
	private String weixinNum;
	private String lastOuyuDate;
	private int ouyuTime;
	
	private StaticUserInfo() {
		// 默认构造函数
	}
	
	
	
	// 获取单例对象的共有静态方法
	public static StaticUserInfo getInstance() {
	    if (userInfo == null) {
		    userInfo = new StaticUserInfo();
	    }
	    return userInfo;
	}
	
	
	public static void initInstance(String openID,String weixinID,String iconURL,int sex,int state,double locationx,double locationy,
			String locationLabel,int hobby,String school,String profession,String grade,String shuoshuo,String loveState,String weixinNum,
			String lastOuyuDate,int ouyuTime){
		if (userInfo == null) {
		    userInfo = new StaticUserInfo();
		    userInfo.setOpenID(openID);
		    userInfo.setWeixinID(weixinID);
		    userInfo.setIconURL(iconURL);
		    userInfo.setSex(sex);
		    userInfo.setState(state);
		    userInfo.setLocationx(locationx);
		    userInfo.setLocationy(locationy);
		    if( locationLabel.equals("") ){
		    	userInfo.setLocationLabel("未标示");
		    }else{
		    	userInfo.setLocationLabel(locationLabel);
		    }
		    userInfo.setHobby(hobby);
		    userInfo.setSchool(school);
		    userInfo.setProfession(profession);
		    userInfo.setGrade(grade);
		    userInfo.setShuoshuo(shuoshuo);
		    userInfo.setLoveState(loveState);
		    userInfo.setWeixinNum(weixinNum);
		    userInfo.setLastOuyuDate(lastOuyuDate);
		    userInfo.setOuyuTime(ouyuTime);
		}
	}
	


	/////////////////////////get set 方法/////////////////////////

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



	public int getState() {
		return state;
	}



	public void setState(int state) {
		this.state = state;
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



	public String getLocationLabel() {
		return locationLabel;
	}



	public void setLocationLabel(String locationLabel) {
		this.locationLabel = locationLabel;
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



	public String getShuoshuo() {
		return shuoshuo;
	}



	public void setShuoshuo(String shuoshuo) {
		this.shuoshuo = shuoshuo;
	}



	public String getLoveState() {
		return loveState;
	}



	public void setLoveState(String loveState) {
		this.loveState = loveState;
	}



	public String getWeixinNum() {
		return weixinNum;
	}



	public void setWeixinNum(String weixinNum) {
		this.weixinNum = weixinNum;
	}



	public String getLastOuyuDate() {
		return lastOuyuDate;
	}



	public void setLastOuyuDate(String lastOuyuDate) {
		this.lastOuyuDate = lastOuyuDate;
	}



	public int getOuyuTime() {
		return ouyuTime;
	}



	public void setOuyuTime(int ouyuTime) {
		this.ouyuTime = ouyuTime;
	}
}
