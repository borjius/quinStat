package com.stat.quin.elastic;


import com.stat.quin.elastic.document.MatchElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchElasticRepository extends ElasticsearchRepository<MatchElastic, Long> {



}
