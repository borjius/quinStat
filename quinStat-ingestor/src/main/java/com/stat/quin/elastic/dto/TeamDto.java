package com.stat.quin.elastic.dto;

public class TeamDto {

    private String teamName;
    private int teamPos;
    private int teamPoints;
    private int globalGoals;
    private int globalAgainstGoals;
    private int wins;
    private int withdraw;
    private int lose;


    public TeamDto(String teamName, int teamPos, int teamPoints, int globalGoals, int globalAgainstGoals,
        int wins, int withdraw, int lose) {
        this.teamName = teamName;
        this.teamPos = teamPos;
        this.teamPoints = teamPoints;
        this.globalGoals = globalGoals;
        this.globalAgainstGoals = globalAgainstGoals;
        this.wins = wins;
        this.withdraw = withdraw;
        this.lose = lose;
    }

    public TeamDto(String teamName) {
        new TeamDto(teamName, 0, 0, 0, 0, 0, 0, 0);
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTeamPos(int teamPos) {
        this.teamPos = teamPos;
    }

    public void setTeamPoints(int teamPoints) {
        this.teamPoints = teamPoints;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamPos() {
        return teamPos;
    }

    public int getTeamPoints() {
        return teamPoints;
    }

    public int getGlobalGoals() {
        return globalGoals;
    }

    public void setGlobalGoals(int globalGoals) {
        this.globalGoals = globalGoals;
    }

    public int getGlobalAgainstGoals() {
        return globalAgainstGoals;
    }

    public void setGlobalAgainstGoals(int globalAgainstGoals) {
        this.globalAgainstGoals = globalAgainstGoals;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }
}
