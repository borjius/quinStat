package com.stat.quin.bean;

import java.util.List;

public class GuessSmallCalc {

    private double homeWinPercentage;
    private double withDrawPercentage;
    private double awayPercentage;

    public GuessSmallCalc(double homeWinPercentage, double withDrawPercentage,
        double awayPercentage) {
        this.homeWinPercentage = homeWinPercentage;
        this.withDrawPercentage = withDrawPercentage;
        this.awayPercentage = awayPercentage;
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
        return "GuessSmallCalc{" +
            "homeWinPercentage=" + homeWinPercentage +
            ", withDrawPercentage=" + withDrawPercentage +
            ", awayPercentage=" + awayPercentage +
            '}';
    }

}
