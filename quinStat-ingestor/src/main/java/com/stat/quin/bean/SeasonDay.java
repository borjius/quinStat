package com.stat.quin.bean;

public enum SeasonDay {

    // 2, 5
    INIT,
    // 6, 10
    POST_INIT,
    // 11, 20
    FIRST_MIDDLE,
    //21, 30
    LAST_MIDDLE,
    // 31, 35
    ALMOST_END,
    // 36, 38
    FINISH,
    // 39, 42
    SECOND_FINISH;

    public static SeasonDay whoAmI(int value) {
        switch (value) {
            case 2: case 3: case 4: case 5:return INIT;
            case 6: case 7: case 8: case 9: case 10: return POST_INIT;
            case 11: case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20: return FIRST_MIDDLE;
            case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: return LAST_MIDDLE;
            case 31: case 32: case 33: case 34: case 35: return ALMOST_END;
            case 36: case 37: case 38: return FINISH;
            default: return SECOND_FINISH;
        }
    }
}
