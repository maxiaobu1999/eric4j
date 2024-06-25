package com.eric.model.msg;


public class FriendInviteMsg {
    private String fromUser;
    private String toUser;
    private String content;
    private String type;

    public FriendInviteMsg() {
    }

    public FriendInviteMsg(String fromUser, String toUser, String content) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.type = "FRIEND_INVITE";
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
