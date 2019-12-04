package com.stat.quin.controller;

import com.stat.quin.bean.IngestRequest;
import com.stat.quin.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuinControllerElasticPopulatorController {

    @Autowired
    private ElasticService elasticService;

    @PostMapping("/ingestElastic")
    public void ingestElastic(@RequestBody IngestRequest ingestRequest) {
        elasticService.ingestByLeagueRange(ingestRequest);
    }

}
