package org.dao;

/**
 * 
 * @author rickie 该类封装一条从数据库中取出的reply
 */

public class Reply {
	// 该条回复的ID
	private int replyID = 0;
	// 该条回复对应的状态ID
	private int msgID = 0;
	// 被回复的回复的ID,如果为0说明为父回复
	private int fatherReplyID = 0;
	// 该条回复的回复人open_id
	private String openID = null;
	// 回复人的昵称
	private String weixinID = null;
	// 回复人头像
	private String iconURL = null;
	// 回复内容
	private String content = null;
	// 回复时间
	private String createTime = null;

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

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public int getMsgID() {
		return msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}
}
