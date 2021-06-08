package com.example.andriodlabs;

public class Message {

    protected String message;
    protected long id;
    protected boolean sentIfTrue;

    public Message(String m, long i) {
        message = m;
        id = i;
    }

    public Message(String m, boolean s) {
        message = m;
        sentIfTrue = s;
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

    public boolean getSentIfTrue() {
        return sentIfTrue;
    }

}