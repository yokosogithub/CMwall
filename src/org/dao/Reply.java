package org.dao;

/**
 * 
 * @author rickie �����װһ�������ݿ���ȡ����reply
 */

public class Reply {
	// �����ظ���ID
	private int replyID = 0;
	// �����ظ���Ӧ��״̬ID
	private int msgID = 0;
	// ���ظ��Ļظ���ID,���Ϊ0˵��Ϊ���ظ�
	private int fatherReplyID = 0;
	// �����ظ��Ļظ���open_id
	private String openID = null;
	// �ظ��˵��ǳ�
	private String weixinID = null;
	// �ظ���ͷ��
	private String iconURL = null;
	// �ظ�����
	private String content = null;
	// �ظ�ʱ��
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
