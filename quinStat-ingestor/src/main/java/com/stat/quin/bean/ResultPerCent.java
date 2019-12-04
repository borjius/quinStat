package com.stat.quin.bean;

public class ResultPerCent {

    private String result;
    private int percentage;

    public ResultPerCent(String result, int percentage) {
        this.result = result;
        this.percentage = percentage;
    }

    public String getResult() {
        return result;
    }

    public int getPercentage() {
        return percentage;
    }

    @Override
    public String toString() {
        return "ResultPerCent{" +
            "result=" + result +
            ", percentage=" + percentage +
            '}';
    }
}
