package com.stat.quin.bean;

public enum ResultType {
    LOCAL("1"), WITHDRAW("X"), AWAY("2");

    private final String label;

    ResultType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ResultType whoAmI(int homeGoals, int awayGoals) {
        if (homeGoals > awayGoals) {
            return LOCAL;
        }
        if (homeGoals == awayGoals) {
            return WITHDRAW;
        }
        return AWAY;
    }

}
