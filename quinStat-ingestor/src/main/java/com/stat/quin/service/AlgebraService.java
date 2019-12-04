package com.stat.quin.service;

import static com.stat.quin.bean.ResultType.AWAY;
import static com.stat.quin.bean.ResultType.LOCAL;

import com.stat.quin.bean.Counter;
import com.stat.quin.bean.GuessCalc;
import com.stat.quin.bean.GuessResponse;
import com.stat.quin.bean.MatchResult;
import com.stat.quin.bean.ResultPerCent;
import com.stat.quin.bean.ResultType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlgebraService {

    private int PERCENTAGE_DIRECT = 30;
    private int PERCENTAGE_OPPOSITE = 30;
    private int PERCENTAGE_SIMILAR = 40;

    private final Random random = new Random();

    public void setPercentages(int direct, int opposite, int similar) {
        this.PERCENTAGE_DIRECT = direct;
        this.PERCENTAGE_OPPOSITE = opposite;
        this.PERCENTAGE_SIMILAR = similar;
    }

    public Mono<GuessCalc> calculateStatistics(Flux<MatchResult> directMatches,
        Flux<MatchResult> oppositeMatches,
        Flux<MatchResult> similarMatches) {

        final Mono<GuessCalc> responseDirect = directMatches.collectList().map(directM -> calculateGuessResponse(directM));
        final Mono<GuessCalc> responseOpposite = oppositeMatches.collectList().map(oppoM -> calculateGuessResponse(oppoM));
        final Mono<GuessCalc> responseSimilar = similarMatches.collectList().map(simM -> calculateGuessResponse(simM));

        return Mono.zip(responseDirect, responseOpposite, responseSimilar)
            .flatMap(dataTuple -> Mono.just(cookStatistics(dataTuple.getT1(), dataTuple.getT2(), dataTuple.getT3())));

    }

    private GuessCalc cookStatistics(GuessCalc responseDirect, GuessCalc responseOpposite, GuessCalc responseSimilar) {
        int percentageTotal = 0;
        double homePercentage = 0;
        double awayPercentage = 0;
        double withdrawPercentage = 0;
        List<ResultPerCent> resultPerCentList = new ArrayList<>();
        if (null != responseDirect) {
            percentageTotal += PERCENTAGE_DIRECT;
            homePercentage += responseDirect.getHomeWinPercentage() * PERCENTAGE_DIRECT;
            awayPercentage += responseDirect.getWithDrawPercentage() * PERCENTAGE_DIRECT;
            withdrawPercentage += responseDirect.getWithDrawPercentage() * PERCENTAGE_DIRECT;
            responseDirect.getMostPossibleResults().stream().forEach(i -> resultPerCentList.add(i));
        }
        if (null != responseOpposite) {
            percentageTotal += PERCENTAGE_OPPOSITE;
            homePercentage += responseOpposite.getAwayPercentage() * PERCENTAGE_OPPOSITE;
            awayPercentage += responseOpposite.getHomeWinPercentage() * PERCENTAGE_OPPOSITE;
            withdrawPercentage += responseOpposite.getWithDrawPercentage() * PERCENTAGE_OPPOSITE;
            responseOpposite.getMostPossibleResults().stream().forEach(i -> resultPerCentList.add(i));
        }
        percentageTotal += PERCENTAGE_SIMILAR;
        homePercentage += responseSimilar.getHomeWinPercentage() * PERCENTAGE_SIMILAR;
        awayPercentage += responseSimilar.getAwayPercentage() * PERCENTAGE_SIMILAR;
        withdrawPercentage += responseSimilar.getWithDrawPercentage() * PERCENTAGE_SIMILAR;
        responseSimilar.getMostPossibleResults().stream().forEach(i -> resultPerCentList.add(i));

        return new GuessCalc(resultPerCentList,
            homePercentage / percentageTotal,
            withdrawPercentage / percentageTotal,
            awayPercentage / percentageTotal);
    }

    private GuessCalc calculateGuessResponse(List<MatchResult> matches) {

        Map<String, Integer> results = new HashMap<>();
        Counter counter = new Counter();
        matches.stream().forEach(match -> {
            switch (match.getResultType()) {
                case LOCAL: counter.incrementHomeVict(); break;
                case AWAY: counter.incrementAwayVict(); break;
                default: counter.incrementWithdraw();
            }
            String resultMatch = match.getHomeGoals() + "-" + match.getAwayGoals();
            if (results.containsKey(resultMatch)) {
                results.put(resultMatch, results.get(resultMatch) + 1);
            } else {
                results.put(resultMatch, 1);
            }
        });
        double homePercentage = counter.getHomeVict() * 100 / matches.size();
        double awayPercentage = counter.getAwayVict() * 100 / matches.size();
        double withdrawPercentage = counter.getWithdraw() * 100 / matches.size();
        List<ResultPerCent> resultPerCentList = results.entrySet().stream()
            .sorted(Collections.reverseOrder(Entry.comparingByValue()))
            .limit(4)
            .map(entry -> new ResultPerCent(entry.getKey(), entry.getValue() * 100 / matches.size()))
            .collect(Collectors.toList());

        return new GuessCalc(resultPerCentList, homePercentage, withdrawPercentage, awayPercentage);

    }

    public Mono<GuessResponse> calculateCombination(GuessCalc calc, int numberColumns) {
        return Mono.just(new GuessResponse(calc, calculateCombinationBase(calc, numberColumns)));
    }

    private String calculateCombinationBase(GuessCalc calc, int numberColumns) {
        double homePerc = calc.getHomeWinPercentage();
        double withdrawPerc = calc.getWithDrawPercentage();
        double awayPerc = calc.getAwayPercentage();

        List<String> valuesToRandom = new ArrayList<>();

        // 70 or more
        String maximumReached = null;
        if (homePerc > 70) {
            maximumReached = LOCAL.getLabel();
        }
        if (withdrawPerc > 70) {
            maximumReached = ResultType.WITHDRAW.getLabel();
        }
        if (awayPerc > 70) {
            maximumReached = AWAY.getLabel();
        }
        if (!StringUtils.isEmpty(maximumReached)) {
            StringBuffer buffer = new StringBuffer();
            for(int i=0; i<numberColumns; i++) {
                buffer.append(maximumReached);
            }
            return buffer.toString();
        }

        addHomeElements(valuesToRandom, homePerc);
        addWithdrawElements(valuesToRandom, withdrawPerc);
        addAwayElements(valuesToRandom, awayPerc);

        StringBuffer buffer = new StringBuffer();

        for (int i=0;i<numberColumns;i++) {
            buffer.append(valuesToRandom.get(random.nextInt(valuesToRandom.size())));
        }
        return buffer.toString();

    }

    private void addAwayElements(List<String> valuesToRandom, double awayPerc) {
        int number = 0;
        if (awayPerc >= 15) {
            number = new Double(awayPerc / 10).intValue();
            if (awayPerc % 10 > 3) {
                number++;
            }
        }
        for (int i= 0;i<number; i++) {
            valuesToRandom.add(AWAY.getLabel());
        }
    }

    private void addWithdrawElements(List<String> valuesToRandom, double withdrawPerc) {
        int number = 0;

        if (withdrawPerc >= 20) {
            number = new Double(withdrawPerc / 10).intValue();
            if (withdrawPerc % 10 > 5) {
                number++;
            }
        }
        for (int i= 0;i<number; i++) {
            valuesToRandom.add(ResultType.WITHDRAW.getLabel());
        }
    }

    private void addHomeElements(List<String> valuesToRandom, double homePerc) {
        int number = 0;

        if (homePerc >= 30) {
            number = new Double(homePerc / 10).intValue();
            if (homePerc % 10 > 3) {
                number++;
            }
        }
        for (int i= 0;i<number; i++) {
            valuesToRandom.add(LOCAL.getLabel());
        }
    }
}
