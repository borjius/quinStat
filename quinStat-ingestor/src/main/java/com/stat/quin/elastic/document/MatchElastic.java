package com.stat.quin.elastic.document;

import com.stat.quin.bean.ResultType;
import com.stat.quin.bean.WebMatchBean;
import com.stat.quin.elastic.dto.TeamDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "quinstat", type = "match")
public class MatchElastic {

    @Id
    private Long id;

    private int league;
    private int season;
    private int dayMatch;
    private String localName;
    private String awayName;
    private int localPos;
    private int awayPos;
    private int localPoints;
    private int awayPoints;
    private int localGoals;
    private int localAgainstGoals;
    private int awayGoals;
    private int awayAgainstGoals;
    private int localWins;
    private int localWithdraw;
    private int localLose;
    private int awayWins;
    private int awayWithdraw;
    private int awayLose;
    private int resultLocal;
    private int resultAway;
    private ResultType resultType;

    public MatchElastic() {}

    public MatchElastic(TeamDto local, TeamDto away, WebMatchBean result, int league, int day, int season) {
        this.id = System.currentTimeMillis();
        this.league = league;
        this.season = season;
        this.dayMatch = day;

        this.localName = local.getTeamName();
        this.localPos = local.getTeamPos();
        this.localPoints = local.getTeamPoints();
        this.localGoals = local.getGlobalGoals();
        this.localAgainstGoals = local.getGlobalAgainstGoals();
        this.localWins = local.getWins();
        this.localWithdraw = local.getWithdraw();
        this.localLose = local.getLose();

        this.awayName = away.getTeamName();
        this.awayPos = away.getTeamPos();
        this.awayPoints = away.getTeamPoints();
        this.awayGoals = away.getGlobalGoals();
        this.awayAgainstGoals = away.getGlobalAgainstGoals();
        this.awayWins = away.getWins();
        this.awayWithdraw = away.getWithdraw();
        this.awayLose = away.getLose();

        this.resultLocal = result.getHomeGoals();
        this.resultAway = result.getAwayGoals();
        this.resultType = ResultType.whoAmI(result.getHomeGoals(), result.getAwayGoals());
    }

    public int getLocalAgainstGoals() {
        return localAgainstGoals;
    }

    public void setLocalAgainstGoals(int localAgainstGoals) {
        this.localAgainstGoals = localAgainstGoals;
    }

    public int getAwayAgainstGoals() {
        return awayAgainstGoals;
    }

    public void setAwayAgainstGoals(int awayAgainstGoals) {
        this.awayAgainstGoals = awayAgainstGoals;
    }

    public int getLeague() {
        return league;
    }

    public void setLeague(int league) {
        this.league = league;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getDayMatch() {
        return dayMatch;
    }

    public void setDayMatch(int dayMatch) {
        this.dayMatch = dayMatch;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public int getLocalPos() {
        return localPos;
    }

    public void setLocalPos(int localPos) {
        this.localPos = localPos;
    }

    public int getAwayPos() {
        return awayPos;
    }

    public void setAwayPos(int awayPos) {
        this.awayPos = awayPos;
    }

    public int getLocalPoints() {
        return localPoints;
    }

    public void setLocalPoints(int localPoints) {
        this.localPoints = localPoints;
    }

    public int getAwayPoints() {
        return awayPoints;
    }

    public void setAwayPoints(int awayPoints) {
        this.awayPoints = awayPoints;
    }

    public int getLocalGoals() {
        return localGoals;
    }

    public void setLocalGoals(int localGoals) {
        this.localGoals = localGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public int getLocalWins() {
        return localWins;
    }

    public void setLocalWins(int localWins) {
        this.localWins = localWins;
    }

    public int getLocalWithdraw() {
        return localWithdraw;
    }

    public void setLocalWithdraw(int localWithdraw) {
        this.localWithdraw = localWithdraw;
    }

    public int getLocalLose() {
        return localLose;
    }

    public void setLocalLose(int localLose) {
        this.localLose = localLose;
    }

    public int getAwayWins() {
        return awayWins;
    }

    public void setAwayWins(int awayWins) {
        this.awayWins = awayWins;
    }

    public int getAwayWithdraw() {
        return awayWithdraw;
    }

    public void setAwayWithdraw(int awayWithdraw) {
        this.awayWithdraw = awayWithdraw;
    }

    public int getAwayLose() {
        return awayLose;
    }

    public void setAwayLose(int awayLose) {
        this.awayLose = awayLose;
    }

    public int getResultLocal() {
        return resultLocal;
    }

    public void setResultLocal(int resultLocal) {
        this.resultLocal = resultLocal;
    }

    public int getResultAway() {
        return resultAway;
    }

    public void setResultAway(int resultAway) {
        this.resultAway = resultAway;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "MatchElastic{" +
            "league=" + league +
            ", season=" + season +
            ", dayMatch=" + dayMatch +
            ", localName='" + localName + '\'' +
            ", awayName='" + awayName + '\'' +
            ", localPos=" + localPos +
            ", awayPos=" + awayPos +
            ", localPoints=" + localPoints +
            ", awayPoints=" + awayPoints +
            ", localGoals=" + localGoals +
            ", localAgainstGoals=" + localAgainstGoals +
            ", awayGoals=" + awayGoals +
            ", awayAgainstGoals=" + awayAgainstGoals +
            ", localWins=" + localWins +
            ", localWithdraw=" + localWithdraw +
            ", localLose=" + localLose +
            ", awayWins=" + awayWins +
            ", awayWithdraw=" + awayWithdraw +
            ", awayLose=" + awayLose +
            ", resultLocal=" + resultLocal +
            ", resultAway=" + resultAway +
            ", resultType=" + resultType +
            '}';
    }
}
