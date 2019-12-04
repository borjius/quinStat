package com.stat.quin.bean;

public enum RangePosition {
    // 1, 2
    CHAMPION,
    // 3, 4
    SEMI_CHAMPION,
    // 5, 8
    UEFA,
    // 9, 12
    MIDDLE,
    // 13, 16
    BAD,
    // 17, 20
    LOSER,
    // 21, 22
    WORSE_LOSER;

    public static RangePosition whoAmI(int value) {
        switch (value) {
            case 1: case 0: return CHAMPION;
            case 3: case 2: return SEMI_CHAMPION;
            case 5: case 6: case 7: case 4: return UEFA;
            case 9: case 10: case 11: case 8: return MIDDLE;
            case 13: case 14: case 15: case 12: return BAD;
            case 17: case 18: case 19: case 16: return LOSER;
            default: return WORSE_LOSER;
        }
    }
}
