package com.example.androidlabs;

public class Message {

    protected String message;
    protected long id;
    protected boolean isSent;
    protected boolean isReceived;

    public Message(String m, long i) {
        message = m;
        id = i;
    }

    public Message(String m, long i, boolean sentOrNot) {
        message = m;
        id = i;
        if (sentOrNot) {
            isSent = true;
            isReceived = false;
        } else {
            isSent = false;
            isReceived = true;
        }
    }

    public Message(String m, boolean sentOrNot) {
        message = m;
        if (sentOrNot) {
            isSent = true;
            isReceived = false;
        } else {
            isSent = false;
            isReceived = true;
        }
    }

    public Message(String m) {
        message = m;
    }

    public void update(String m) {
        message = m;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public boolean isSent() {
        return isSent;
    }

    public boolean isReceived() {
        return isReceived;
    }
}