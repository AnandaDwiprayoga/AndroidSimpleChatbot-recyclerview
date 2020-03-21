package com.anandadwiprayoga.recyclerviewchatbot.model;

public class MessageModel {
    private String message, time, type;

    public static final String TYPE_SENDER = "sender";
    public static final String TYPE_BOT = "bot";

    public MessageModel(String message, String time, String type) {
        this.message = message;
        this.time = time;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
