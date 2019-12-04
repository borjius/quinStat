package com.stat.quin.bean;

import java.util.List;

public class GuessCalc {

    private List<ResultPerCent> mostPossibleResults;
    private double homeWinPercentage;
    private double withDrawPercentage;
    private double awayPercentage;

    public GuessCalc(List<ResultPerCent> mostPossibleResults, double homeWinPercentage, double withDrawPercentage,
        double awayPercentage) {
        this.mostPossibleResults = mostPossibleResults;
        this.homeWinPercentage = homeWinPercentage;
        this.withDrawPercentage = withDrawPercentage;
        this.awayPercentage = awayPercentage;
    }

    public List<ResultPerCent> getMostPossibleResults() {
        return mostPossibleResults;
    }

    public double getHomeWinPercentage() {
        return homeWinPercentage;
    }

    public double getWithDrawPercentage() {
        return withDrawPercentage;
    }

    public double getAwayPercentage() {
        return awayPercentage;
    }

    @Override
    public String toString() {
        return "GuessCalc{" +
            "mostPossibleResults=" + mostPossibleResults.toString() +
            ", homeWinPercentage=" + homeWinPercentage +
            ", withDrawPercentage=" + withDrawPercentage +
            ", awayPercentage=" + awayPercentage +
            '}';
    }
}
