package com.stat.quin.service;

import com.stat.quin.bean.GuessRequest;
import com.stat.quin.bean.RangePosition;
import com.stat.quin.bean.SeasonDay;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class ModelClasiffier {

    private static final Logger logger = LoggerFactory.getLogger(ModelClasiffier.class);

    private Attribute seasonDay;
    private Attribute league;
    private Attribute homeRangePosition;
    private Attribute awayRangePosition;


    private ArrayList attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;

    public ModelClasiffier() {
        seasonDay = new Attribute("seasonDay");
        league = new Attribute("league");
        homeRangePosition = new Attribute("homeRangePosition");
        awayRangePosition = new Attribute("awayRangePosition");


        attributes = new ArrayList();
        classVal = new ArrayList();
        classVal.add("1");
        classVal.add("X");
        classVal.add("2");

        attributes.add(seasonDay);
        attributes.add(league);
        attributes.add(homeRangePosition);
        attributes.add(awayRangePosition);


        attributes.add(new Attribute("class", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }

    public Instances createInstance(GuessRequest match) {
        dataRaw.clear();

        Instance instace = new DenseInstance(5);
        instace.setDataset(dataRaw);
        instace.setValue(0, SeasonDay.whoAmI(match.getLeagueDay()).ordinal());
        instace.setValue(1, match.getLeague());
        instace.setValue(2, RangePosition.whoAmI(match.getHomePosition()).ordinal());
        instace.setValue(3, RangePosition.whoAmI(match.getAwayPosition()).ordinal());
        instace.setValue(4, 0);
        dataRaw.add(instace);

        return dataRaw;
    }

    public String classifiy(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (MultilayerPerceptron) SerializationHelper.read(path);
            result = classVal.get((int) cls.classifyInstance(insts.firstInstance()));
        } catch (Exception ex) {
            logger.error("Error classifying instances!!!", ex);
        }
        return result;
    }
}
