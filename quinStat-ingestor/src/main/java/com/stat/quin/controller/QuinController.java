package com.stat.quin.controller;

import com.stat.quin.bean.GuessRequest;
import com.stat.quin.bean.GuessCalc;
import com.stat.quin.bean.GuessResponse;
import com.stat.quin.bean.GuessSmallCalc;
import com.stat.quin.bean.IngestRequest;
import com.stat.quin.bean.MatchResult;
import com.stat.quin.service.AlgebraService;
import com.stat.quin.service.ExtractorService;
import com.stat.quin.service.IngestorService;
import com.stat.quin.service.MachineLearningService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class QuinController {

    @Autowired
    private IngestorService ingestorService;
    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private AlgebraService algebraService;
    @Autowired
    private MachineLearningService machineLearningService;


    @PostMapping("/ingest")
    public void saveData(@RequestBody IngestRequest ingestRequest) {
        ingestorService.ingestByLeagueRange(ingestRequest);
    }

    @PostMapping("/guess")
    public Mono<GuessResponse> guessResult(@RequestBody GuessRequest guessRequest) {
        Mono<GuessCalc> calc = guessProcess(guessRequest);

        return calc
            .flatMap(guessCalc -> algebraService.calculateCombination(guessCalc, guessRequest.getNumberOfColumns()));
    }

    @PostMapping("/guessReduced")
    public Mono<String> guessResultReduced(@RequestBody GuessRequest guessRequest) {
        Mono<GuessCalc> calc = guessProcess(guessRequest);

        return calc
            .flatMap(guessCalc -> algebraService.calculateCombination(guessCalc, guessRequest.getNumberOfColumns())
                .map(guessResponse -> guessResponse.getCombination()));
    }

    @PostMapping("/guess/percentages")
    public Mono<GuessSmallCalc> guessOnlyPercentages(@RequestBody GuessRequest guessRequest) {
        Mono<GuessCalc> calc = guessProcess(guessRequest);

        return calc.map(
            guessCalc -> new GuessSmallCalc(guessCalc.getHomeWinPercentage(), guessCalc.getWithDrawPercentage(),
                guessCalc.getAwayPercentage()));
    }

    private Mono<GuessCalc> guessProcess(@RequestBody GuessRequest guessRequest) {
        Flux<MatchResult> directMatches = extractorService
            .getDirectMatches(guessRequest.getHomeName(), guessRequest.getAwayName());
        Flux<MatchResult> oppositeMatches = extractorService
            .getDirectMatches(guessRequest.getAwayName(), guessRequest.getHomeName());
        Flux<MatchResult> similarMatches = extractorService
            .getSimilarMatches(guessRequest.getHomePosition(), guessRequest.getAwayPosition(), guessRequest.getLeague(),
                guessRequest.getLeagueDay());
        return algebraService.calculateStatistics(directMatches, oppositeMatches, similarMatches);
    }

    @GetMapping("/teamsAvailable")
    public Mono<String> getTeamsAvailable() {
        return extractorService.getAllTeams();
    }

    @GetMapping("/savearff")
    public void generateArffReport() {
        machineLearningService.generateArffFile();
    }

    @PostMapping("/guess/learning")
    public Flux<String> guessResultLearning(@RequestBody List<GuessRequest> guessRequests) throws Exception {
        return machineLearningService.evaluateMatch(guessRequests);
    }

    public void updatePercentages(int direct, int opposite, int similar) {
        algebraService.setPercentages(direct, opposite, similar);
    }

}
