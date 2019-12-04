package com.stat.quin.service;

import com.stat.quin.bean.MatchBean;
import com.stat.quin.bean.MatchResult;
import com.stat.quin.bean.RangePosition;
import com.stat.quin.bean.SeasonDay;
import com.stat.quin.repository.MatchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExtractorService {

    @Autowired
    private MatchRepository repository;

    public Mono<String> getAllTeams() {
        Mono<List<String>> teamsSorted = repository.findAll().map(MatchBean::getHomeName).distinct().collectSortedList();

        return teamsSorted.map(list -> String.join(", ", list));
    }

    public Flux<MatchResult> getDirectMatches(String homeName, String awayName) {
        Flux<MatchBean> directMatches = repository.findByHomeNameAndAwayName(homeName, awayName);

        return directMatches.map(matchBean -> new MatchResult(matchBean.getHomeGoals(), matchBean.getAwayGoals()));
    }

    public Flux<MatchResult> getSimilarMatches(int homePosition, int awayPosition, int league, int leagueDay) {
        RangePosition homeRange = RangePosition.whoAmI(homePosition - 1);
        RangePosition awayRange = RangePosition.whoAmI(awayPosition - 1);

        Flux<MatchBean> similarMatches = repository
            .findByHomeRangePositionAndAwayRangePositionAndLeague(homeRange, awayRange, league);

        return similarMatches.filter(match -> match.getSeasonDay().equals(
            SeasonDay.whoAmI(leagueDay))).map(matchBean -> new MatchResult(matchBean.getHomeGoals(), matchBean.getAwayGoals()));

    }

}
