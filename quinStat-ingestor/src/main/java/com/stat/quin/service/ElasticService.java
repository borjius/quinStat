package com.stat.quin.service;

import com.stat.quin.DateHelper;
import com.stat.quin.bean.IngestRequest;
import com.stat.quin.bean.WebMatchBean;
import com.stat.quin.elastic.MatchElasticRepository;
import com.stat.quin.elastic.document.MatchElastic;
import com.stat.quin.elastic.dto.TeamDto;
import com.stat.quin.selenium.SeleniumConnector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElasticService {

    @Autowired
    private SeleniumConnector connector;
    @Autowired
    private MatchElasticRepository elasticRepository;

    public void ingestByLeagueRange(IngestRequest ingestRequest) {
        connector.start();
        connector.loadHome();

        List<String> seasonsList = DateHelper.getListYears(ingestRequest.getFrom(), ingestRequest.getTo());

        seasonsList.stream().forEach(season -> {
            connector.clickOnLeagueSeason(ingestRequest.getLeague(), season);
            List<Integer> days = connector.getDays();
            days.stream().forEach(day -> {
                connector.clickOnSeasonDay(day);
                Map<String, TeamDto> teamList = getDayTeams(day);
                List<WebMatchBean> webResults = connector.getWebResults(day);
                webResults.stream().forEach(webResult -> {
                    TeamDto local = teamList.get(webResult.getHomeTeam());
                    TeamDto away = teamList.get(webResult.getAwayTeam());
                    MatchElastic elastic = new MatchElastic(local, away, webResult, ingestRequest.getLeague(), day, Integer.parseInt(season.split("-")[0]));
                    elasticRepository.save(elastic);
                });

            });
            System.out.println("Season " + season + " loaded");
            connector.loadHome();
        });
    }

    private Map<String, TeamDto> getDayTeams(Integer day) {
        Map<String, TeamDto> mapPositions = new HashMap<>();
        if (day > 1) {
            int previousDay = day - 1;
            connector.clickOnSeasonDay(previousDay);
            List<TeamDto> teams = connector.getTeamsResults();
            connector.clickOnSeasonDay(day);
            IntStream.range(0, teams.size())
                .forEach(i ->
                    mapPositions.put(teams.get(i).getTeamName(), teams.get(i))

                );
        } else {
            List<String> teamsOrdered = connector.getTablePositions();
            IntStream.range(0, teamsOrdered.size())
                .forEach(i ->
                    mapPositions.put(teamsOrdered.get(i), new TeamDto(teamsOrdered.get(i)))

                );
        }

        connector.clickOnSeasonDay(day);
        return mapPositions;
    }
}
