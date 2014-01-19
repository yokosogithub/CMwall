package org.message.req;

/**
 * 文本消息
 * 
 * @author 刘星
 * @date 2013-09-14
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
