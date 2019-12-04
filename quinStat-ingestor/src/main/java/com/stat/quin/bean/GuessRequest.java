package com.stat.quin.bean;

public class GuessRequest {

    private int homePosition;
    private String homeName;
    private int awayPosition;
    private String awayName;
    private int leagueDay;
    private int league;
    private int numberOfColumns;

    public GuessRequest(int homePosition, String homeName, int awayPosition, String awayName, int leagueDay, int league, int numberOfColumns) {
        this.homePosition = homePosition;
        this.homeName = homeName;
        this.awayPosition = awayPosition;
        this.awayName = awayName;
        this.leagueDay = leagueDay;
        this.league = league;
        this.numberOfColumns = numberOfColumns;
    }

    public int getHomePosition() {
        return homePosition;
    }

    public String getHomeName() {
        return homeName;
    }

    public int getAwayPosition() {
        return awayPosition;
    }

    public String getAwayName() {
        return awayName;
    }

    public int getLeagueDay() {
        return leagueDay;
    }

    public int getLeague() {
        return league;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }
}
