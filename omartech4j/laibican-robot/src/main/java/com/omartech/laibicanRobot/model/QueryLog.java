package com.omartech.laibicanRobot.model;

/**
 * Created by omar on 15-1-5.
 */
public class QueryLog {

    private long id;
    private String uid;
    private String query;
    private String source;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
