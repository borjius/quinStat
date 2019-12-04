package com.stat.quin.bean;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Matches")
public class MatchBean {

    @Id
    private int id;
    private SeasonDay seasonDay;
    private int league;
    private RangePosition homeRangePosition;
    private String homeName;
    private RangePosition awayRangePosition;
    private String awayName;
    private int homeGoals;
    private int awayGoals;
    private ResultType resultType;

    public MatchBean(SeasonDay seasonDay, int league, RangePosition homeRangePosition,
        RangePosition awayRangePosition, int homeGoals, int awayGoals, ResultType resultType,
        String homeName, String awayName) {
        this.seasonDay = seasonDay;
        this.league = league;
        this.homeRangePosition = homeRangePosition;
        this.awayRangePosition = awayRangePosition;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
        this.resultType = resultType;
        this.homeName = homeName;
        this.awayName = awayName;
    }

    public MatchBean() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeasonDay(SeasonDay seasonDay) {
        this.seasonDay = seasonDay;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public void setHomeRangePosition(RangePosition homeRangePosition) {
        this.homeRangePosition = homeRangePosition;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public void setAwayRangePosition(RangePosition awayRangePosition) {
        this.awayRangePosition = awayRangePosition;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public int getId() {
        return id;
    }

    public SeasonDay getSeasonDay() {
        return seasonDay;
    }

    public int getLeague() {
        return league;
    }

    public RangePosition getHomeRangePosition() {
        return homeRangePosition;
    }

    public RangePosition getAwayRangePosition() {
        return awayRangePosition;
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

    @Override
    public String toString() {
        return "MatchBean{" +
            "id=" + id +
            ", seasonDay=" + seasonDay +
            ", league=" + league +
            ", homeRangePosition=" + homeRangePosition +
            ", homeName='" + homeName + '\'' +
            ", awayRangePosition=" + awayRangePosition +
            ", awayName='" + awayName + '\'' +
            ", homeGoals=" + homeGoals +
            ", awayGoals=" + awayGoals +
            ", resultType=" + resultType +
            '}';
    }

    public String getHomeName() {
        return homeName;
    }

    public String getAwayName() {
        return awayName;
    }

}
