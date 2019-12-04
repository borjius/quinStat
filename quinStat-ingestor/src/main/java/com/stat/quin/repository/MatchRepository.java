package com.stat.quin.repository;

import com.stat.quin.bean.MatchBean;
import com.stat.quin.bean.RangePosition;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MatchRepository extends ReactiveCrudRepository<MatchBean, Long> {

    Flux<MatchBean> findByHomeNameAndAwayName(String homeName, String awayName);

    Flux<MatchBean> findByHomeRangePositionAndAwayRangePositionAndLeague(RangePosition homeRange, RangePosition awayRange, int league);
}
