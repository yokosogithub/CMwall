package org.dao;

public class TopicReply {
	
	private int replyID;
	private int fatherReplyID;
	private int topicID;
	private String openID;
	private String content;
	private String createTime;
	private String weixinID;
	private String iconURL;
	private int sex;
	public int getReplyID() {
		return replyID;
	}
	public void setReplyID(int replyID) {
		this.replyID = replyID;
	}
	public int getFatherReplyID() {
		return fatherReplyID;
	}
	public void setFatherReplyID(int fatherReplyID) {
		this.fatherReplyID = fatherReplyID;
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
	
	
}
