package com.stat.quin.service;

import com.stat.quin.bean.GuessRequest;
import com.stat.quin.bean.MatchBean;
import com.stat.quin.repository.MatchRepository;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

@Service
public class MachineLearningService {

    private static final Logger logger = LoggerFactory.getLogger(MachineLearningService.class);

    private static final String ARFF = "../matches.arff";
    private static final String MODElPATH = "model.bin";

    @Autowired
    private MatchRepository matchRepository;

    public void generateArffFile() {
        FastVector vector = new FastVector(5);

        vector.add(new Attribute("seasonDay"));
        vector.add(new Attribute("league"));
        vector.add(new Attribute("homeRangePosition"));
        vector.add(new Attribute("awayRangePosition"));
        ArrayList classVal = new ArrayList();
        classVal.add("1");
        classVal.add("X");
        classVal.add("2");

        vector.add(new Attribute("class", classVal));

        final Flux<MatchBean> allMatches = matchRepository.findAll();

        final Mono<Long> numInstances = allMatches.count();

        final Mono<Instances> dataset = numInstances.map(number -> new Instances("Dataset", vector, number.intValue()));

        final Mono<List<Instance>> instanceFlux = allMatches.zipWith(dataset, (match, set) -> {
            Instance instance = new DenseInstance(5);
            instance.setDataset(set);
            instance.setValue(0, match.getSeasonDay().ordinal());
            instance.setValue(1, match.getLeague());

            instance.setValue(2, match.getHomeRangePosition().ordinal());
            instance.setValue(3, match.getAwayRangePosition().ordinal());
            instance.setValue(4, match.getResultType().getLabel());
            set.add(instance);
            return instance;
        }).collectList();

        Mono.zip(instanceFlux, dataset, (i, d) -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARFF))) {
                writer.write(dataset.toString());
                writer.flush();
            } catch (IOException e) {
                logger.error("Error writing arff file", e);
            }
            return true;
        });


    }

    public Flux<String> evaluateMatch(List<GuessRequest> guessRequests) throws Exception {
        Instances instances = loadDataset(ARFF);

        Filter filter = new Normalize();

        // divide dataset to train dataset 80% and test dataset 20%
        int trainSize = (int) Math.round(instances.numInstances() * 0.95);
        int testSize = instances.numInstances() - trainSize;

        instances.randomize(
            new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%

        //Normalize dataset
        filter.setInputFormat(instances);
        Instances datasetnor = Filter.useFilter(instances, filter);

        Instances traindataset = new Instances(datasetnor, 0, trainSize);
        Instances testdataset = new Instances(datasetnor, trainSize, testSize);

        // build classifier with train dataset
        MultilayerPerceptron ann = (MultilayerPerceptron) buildClassifier(traindataset);

        // Evaluate classifier with test dataset
        String evalsummary = evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);

        //Save model
        saveModel(ann, MODElPATH);

        //classifiy a single instance
        ModelClasiffier cls = new ModelClasiffier();

        return Flux.fromStream(guessRequests.stream().map(match -> {
            try {
                String resultMatch = cls.classifiy(Filter.useFilter(cls.createInstance(match), filter), MODElPATH);
                logger.info("The result for the match {} - {} should be {}",
                    match.getHomeName(),
                    match.getAwayName(),
                    resultMatch);
                return resultMatch;
            } catch (Exception e) {
                logger.error("Error analyzing result match", e);
                return "E";
            }
        }));

    }

    private Instances loadDataset(String path) {
        Instances dataset = null;
        try {
            dataset = DataSource.read(path);
            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception ex) {
            logger.error("Error loading dataset", ex);
        }

        return dataset;
    }

    public void saveModel(Classifier model, String modelpath) {

        try {
            SerializationHelper.write(modelpath, model);
        } catch (Exception ex) {
            logger.error("Error saving model", ex);
        }
    }

    private String evaluateModel(Classifier model, Instances traindataset, Instances testdataset) {
        Evaluation eval = null;
        try {
            // Evaluate classifier with test dataset
            eval = new Evaluation(traindataset);
            eval.evaluateModel(model, testdataset);
        } catch (Exception ex) {
            logger.error("Error evaluating model", ex);
        }
        return eval.toSummaryString("", true);
    }

    public Classifier buildClassifier(Instances traindataset) {
        MultilayerPerceptron m = new MultilayerPerceptron();

        try {
            m.buildClassifier(traindataset);

        } catch (Exception ex) {
            logger.error("Error building classifier", ex);
        }
        return m;
    }
}
