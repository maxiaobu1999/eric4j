package com.chinasoft.model.msg;

//{"type":"chat","data":{"fromUser":"100","toUser":"101","content":"100发给101的消息内容"}}
public class ChatMsg {
    private String fromId;
    private String toId;
    private String content;


    private String msgType;

    private String stamp;

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public ChatMsg() {

    }

    public ChatMsg(String fromId, String toId, String content) {
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
