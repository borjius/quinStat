package com.stat.quin.bean;

public class IngestRequest {

    private String from;
    private String to;
    private int league;

    public IngestRequest(String from, String to, int league) {
        this.from = from;
        this.to = to;
        this.league = league;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getLeague() {
        return league;
    }
}
