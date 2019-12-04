package com.stat.quin;

import java.util.ArrayList;
import java.util.List;

public class DateHelper {

    /**
     * the format of introduced dates is ex: 1980-81 for both from and to.
     * The method will return all years between from 1980-81 (1980) and to 1990-91 (1990)
     * @param from initial date in string
     * @param to final date in string
     * @return list of years
     */
    public static List<String> getListYears(String from, String to) {
        int lastYear = Integer.parseInt(to.split("-")[0]);
        List<String> seasonsList = new ArrayList<>();
        seasonsList.add(from);
        int nextSeason = Integer.parseInt(from.split("-")[0]) + 1;
        while (nextSeason < lastYear) {
            String seasonString = nextSeason + "-" + String.valueOf(nextSeason + 1).substring(2);
            seasonsList.add(seasonString);
            nextSeason = nextSeason + 1;
        }
        seasonsList.add(to);
        return seasonsList;
    }

}
