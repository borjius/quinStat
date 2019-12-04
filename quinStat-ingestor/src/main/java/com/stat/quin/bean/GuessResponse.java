package com.stat.quin.bean;

public class GuessResponse {

    private GuessCalc guessCalc;
    private String combination;

    public GuessResponse(GuessCalc guessCalc, String combination) {
        this.guessCalc = guessCalc;
        this.combination = combination;
    }

    public void setGuessCalc(GuessCalc guessCalc) {
        this.guessCalc = guessCalc;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public GuessCalc getGuessCalc() {
        return guessCalc;
    }

    public String getCombination() {
        return combination;
    }

    @Override
    public String toString() {
        return "GuessResponse{" +
            "guessCalc=" + guessCalc.toString() +
            ", combination='" + combination + '\'' +
            '}';
    }
}
