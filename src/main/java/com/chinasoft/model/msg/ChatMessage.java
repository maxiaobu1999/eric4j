package com.chinasoft.model.msg;

//{"type":"chat","data":{"fromUser":"100","toUser":"101","content":"100发给101的消息内容"}}
public class ChatMessage {
    private String fromUser;
    private String toUser;
    private String content;

    private String stamp;

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public ChatMessage(){

    }

    public ChatMessage(String fromUser, String toUser, String content) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
