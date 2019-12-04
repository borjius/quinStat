package com.stat.quin.bean;

public class MatchResult {

    private int homeGoals;
    private int awayGoals;
    private ResultType resultType;

    public MatchResult(int homeGoals, int awayGoals) {
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.resultType = ResultType.whoAmI(homeGoals, awayGoals);
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public ResultType getResultType() {
        return resultType;
    }
}
