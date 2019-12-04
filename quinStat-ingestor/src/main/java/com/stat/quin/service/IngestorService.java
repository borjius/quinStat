package com.stat.quin.service;

import com.stat.quin.DateHelper;
import com.stat.quin.bean.IngestRequest;
import com.stat.quin.bean.MatchBean;
import com.stat.quin.bean.RangePosition;
import com.stat.quin.bean.ResultType;
import com.stat.quin.bean.SeasonDay;
import com.stat.quin.bean.WebMatchBean;
import com.stat.quin.repository.MatchRepository;
import com.stat.quin.selenium.SeleniumConnector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngestorService {

    private static final Logger logger = LoggerFactory.getLogger(IngestorService.class);

    @Autowired
    private SeleniumConnector connector;

    @Autowired
    private MatchRepository matchRepository;

    public void ingestByLeagueRange(IngestRequest ingestRequest) {
        connector.start();
        connector.loadHome();

        List<String> seasonsList = DateHelper.getListYears(ingestRequest.getFrom(), ingestRequest.getTo());

        seasonsList.stream().forEach(season -> {
            connector.clickOnLeagueSeason(ingestRequest.getLeague(), season);
            List<Integer> days = connector.getDays();
            days.stream().forEach(day -> {
                connector.clickOnSeasonDay(day);
                Map<String, RangePosition> teamRangeList = getListTeamRange();
                List<WebMatchBean> webResults = connector.getWebResults(day);
                List<MatchBean> matchBeanList = calculateMatchBean(teamRangeList, webResults, day, ingestRequest.getLeague());

                matchBeanList.stream().forEach(match -> matchRepository.save(match)
                );

            });
            logger.info("Season {} loaded", season);
            connector.loadHome();
        });
    }

    private List<MatchBean> calculateMatchBean(Map<String, RangePosition> teamRangeList,
        List<WebMatchBean> webResults, Integer day, int league) {

        return webResults.stream().map(webResult ->
            new MatchBean(SeasonDay.whoAmI(day),
                league,
                teamRangeList.get(webResult.getHomeTeam()),
                teamRangeList.get(webResult.getAwayTeam()),
                webResult.getHomeGoals(),
                webResult.getAwayGoals(),
                ResultType.whoAmI(webResult.getHomeGoals(), webResult.getAwayGoals()),
                webResult.getHomeTeam(),
                webResult.getAwayTeam()))
            .collect(Collectors.toList());

    }

    private Map<String, RangePosition> getListTeamRange() {
        Map<String, RangePosition> mapPositions = new HashMap<>();
        List<String> teamsOrdered = connector.getTablePositions();
        IntStream.range(0, teamsOrdered.size()).forEach(i ->
            mapPositions.put(teamsOrdered.get(i), RangePosition.whoAmI(i))
        );
        return mapPositions;
    }
}
