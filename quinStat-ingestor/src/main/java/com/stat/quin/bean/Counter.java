package com.stat.quin.bean;

public class Counter {

    private int homeVict = 0;
    private int awayVict = 0;
    private int withdraw = 0;

    public int getHomeVict() {
        return homeVict;
    }

    public void incrementHomeVict() {
        this.homeVict = homeVict + 1;
    }

    public int getAwayVict() {
        return awayVict;
    }

    public void incrementAwayVict() {
        this.awayVict = awayVict + 1;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void incrementWithdraw() {
        this.withdraw = withdraw + 1;
    }
}
