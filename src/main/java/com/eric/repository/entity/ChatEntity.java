package com.eric.repository.entity;

/**
 * 账户信息
 */
public class ChatEntity {

    /**
     * 消息ID'
     */
    public String messageId;
    /**
     * 发送者ID
     */
    public String fromId;
    /**
     * 接收者ID
     */
    public String toId;
    /**
     * 消息内容
     */
    public String content;

    public long stamp;


    public String msgType;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
