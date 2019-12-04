package com.stat.quin;


import com.stat.quin.bean.GuessRequest;
import com.stat.quin.bean.GuessSmallCalc;
import com.stat.quin.bean.ResultType;
import com.stat.quin.controller.QuinController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    private static final String matchCombination4 = "2XX21221X2121X";
    private static final String matchCombination5 = "2X1212XX2X1X22";
    private static final String matchCombination9 = "XX12X22111X1XX";
    private static final String matchCombination10 = "XXX111222XX112";

    private static final int okNumber = 12;

    @Autowired
    private QuinController controller;

    @Test
    public void getTeams() {
        System.out.println(controller.getTeamsAvailable());
    }

    // giving the percentage, now in which position is the correct one, ordering the percentages
//    @Test
    public void calculatePositionWhereValid() {
        Map<String, String> interestingValues = new HashMap<>();
        for (int direct = 1; direct < 102; direct = direct + 10) {
            for (int opposite = 1; opposite < 102; opposite = opposite + 10) {
                for (int similar = 1; similar < 102; similar = similar + 10) {
                    controller.updatePercentages(direct, opposite, similar);
                    int numberOf1 = 0;
                    int numberOf2 = 0;
                    int numberOf3 = 0;
                    List<String> combination14 = stringToList(matchCombination4);

                    combination14 = stringToList(matchCombination9);
                    List<GuessRequest> guessDay9 = getDay9(1);
                    for (int i = 0; i < combination14.size(); i++) {

                        GuessSmallCalc values = controller.guessOnlyPercentages(guessDay9.get(i)).block();


                        final String realResult = combination14.get(i);
                        int position = getPosition(values, realResult);
                        numberOf1 = increaseCounter1(numberOf1, position);
                        numberOf2 = increaseCounter2(numberOf2, position);
                        numberOf3 = increaseCounter3(numberOf3, position);
                    }
                    int total = numberOf1 + numberOf2 + numberOf3;
                    double first = new Double(numberOf1 * 100 / total);
                    double second = new Double(numberOf2 * 100 / total);
                    double third = new Double(numberOf3 * 100 / total);


                    if ((first > 70 || second > 70) && third < 15) {
                        String line = direct + "-" + opposite + "-" + similar;
                        String result = first + "-" + second + "-" + third;
                        System.out.println(line + "::::::" + result);
                        if (third < 10) {
                            System.out.println("AMAZIINNNNNNG-----------");
                        } else {
                            System.out
                                .println("GOT IT!!!");
                        }
                        interestingValues.put(line, result);
                    }



                }
            }




        }

        for (int direct = 1; direct < 102; direct = direct + 10) {
            for (int opposite = 1; opposite < 102; opposite = opposite + 10) {
                for (int similar = 1; similar < 102; similar = similar + 10) {
                    controller.updatePercentages(direct, opposite, similar);
                    int numberOf1 = 0;
                    int numberOf2 = 0;
                    int numberOf3 = 0;
                    List<String> combination14 = stringToList(matchCombination10);
                    List<GuessRequest> guessDay10 = getDay10(1);
                    for (int i = 0; i < combination14.size(); i++) {
                        GuessSmallCalc values = controller.guessOnlyPercentages(guessDay10.get(i)).block();
                        final String realResult = combination14.get(i);
                        int position = getPosition(values, realResult);
                        numberOf1 = increaseCounter1(numberOf1, position);
                        numberOf2 = increaseCounter2(numberOf2, position);
                        numberOf3 = increaseCounter3(numberOf3, position);
                    }
                    int total = numberOf1 + numberOf2 + numberOf3;
                    double first = new Double(numberOf1 * 100 / total);
                    double second = new Double(numberOf2 * 100 / total);
                    double third = new Double(numberOf3 * 100 / total);
                    if ((first > 70 || second > 70) && third < 15) {
                        String line = direct + "-" + opposite + "-" + similar;
                        String result = first + "-" + second + "-" + third;
                        System.out.println(line + "::::::" + result);
                        if (third < 10) {
                            System.out.println("AMAZIINNNNNNG-----------");
                        } else {
                            System.out
                                .println("GOT IT!!!");
                        }

                        if (interestingValues.containsKey(line)) {
                            System.out.println("MATCH!!!!!");
                            System.out.println(line + " previous " + interestingValues.get(line) + " current " + result);
                        } else {
                            interestingValues.put(line, result);
                        }


                    }
                }
            }
        }
        System.out.println("Final interesting results!!");
        interestingValues.keySet().stream().forEach(x -> System.out.println(x + " ::: " + interestingValues.get(x)));

    }

//    @Test
    public void calculateMachineLearning() throws Exception {
        List<GuessRequest> day = todaysOne(1);
        List<String> elements = controller.guessResultLearning(day).collectList().block();
        elements.stream().forEach(i -> System.out.println(i));
    }

    private int increaseCounter3(int numberOf3, int position) {
        if (position == 3) {
            numberOf3++;
        }
        return numberOf3;
    }

    private int increaseCounter2(int numberOf2, int position) {
        if (position == 2) {
            numberOf2++;
        }
        return numberOf2;
    }

    private int increaseCounter1(int numberOf1, int position) {
        if (position == 1) {
            numberOf1++;
        }
        return numberOf1;
    }

    private int getPosition(GuessSmallCalc values, String realResult) {

        if (ResultType.LOCAL.getLabel().equals(realResult)) {
            if (values.getHomeWinPercentage() >= values.getAwayPercentage() && values.getHomeWinPercentage() >= values
                .getWithDrawPercentage()) {
                return 1;
            } else {
                if (values.getHomeWinPercentage() >= values.getAwayPercentage()
                    || values.getHomeWinPercentage() >= values
                    .getWithDrawPercentage()) {
                    return 2;
                }
                return 3;
            }
        }
        if (ResultType.WITHDRAW.getLabel().equals(realResult)) {
            if (values.getWithDrawPercentage() > values.getHomeWinPercentage()
                && values.getWithDrawPercentage() >= values
                .getAwayPercentage()) {
                return 1;
            } else {
                if (values.getWithDrawPercentage() > values.getHomeWinPercentage()
                    || values.getWithDrawPercentage() >= values
                    .getAwayPercentage()) {
                    return 2;
                }
                return 3;
            }
        }
        if (ResultType.AWAY.getLabel().equals(realResult)) {
            if (values.getAwayPercentage() > values.getHomeWinPercentage() && values.getAwayPercentage() > values
                .getWithDrawPercentage()) {
                return 1;
            } else {
                if (values.getAwayPercentage() > values.getHomeWinPercentage() || values.getAwayPercentage() > values
                    .getWithDrawPercentage()) {
                    return 2;
                }
                return 3;
            }
        }
        throw new RuntimeException("Something wrong happened!");
    }

//    @Test
    public void calculateNumberOfColumnsNeededForX() {

        int columnsNeeded = 1;
        int maximum = 1;
        boolean gotIt = false;
        while (!gotIt) {

            final List<String> combination14 = stringToList(matchCombination4);
            System.out.println("Day 4 combination needed: " + combination14.toString());
            List<GuessRequest> guessDay4 = getDay4(columnsNeeded);
            List<List<String>> columns = makeIt(guessDay4);
            for (int i = 0; i < columnsNeeded; i++) {
                final List<String> column = getColumn(columns, i);

                int matches = correctOnesInColumn(combination14, column);
                System.out.println("Day 4 column " + i + " values " + column.toString() + " matches " + matches);
                if (matches >= okNumber) {
                    System.out.println("FOUND!!!");
                    gotIt = true;
                    break;
                }
            }
            columnsNeeded++;
        }
        maximum = columnsNeeded;
        columnsNeeded = 1;
        gotIt = false;
        while (!gotIt) {

            final List<String> combination14 = stringToList(matchCombination5);
            System.out.println("Day 5 combination needed: " + combination14.toString());
            List<GuessRequest> guessDay5 = getDay5(columnsNeeded);
            List<List<String>> columns = makeIt(guessDay5);
            for (int i = 0; i < columnsNeeded; i++) {
                final List<String> column = getColumn(columns, i);
                int matches = correctOnesInColumn(combination14, column);
                System.out.println("Day 5 column " + i + " values " + column.toString() + " matches " + matches);
                if (matches >= okNumber) {
                    System.out.println("FOUND!!!");
                    gotIt = true;
                    break;
                }
            }
            columnsNeeded++;
        }
        gotIt = false;
        if (columnsNeeded > maximum) {
            maximum = columnsNeeded;
        }
        columnsNeeded = 1;
        while (!gotIt) {

            final List<String> combination14 = stringToList(matchCombination9);
            System.out.println("Day 9 combination needed: " + combination14.toString());
            List<GuessRequest> guessDay9 = getDay9(columnsNeeded);
            List<List<String>> columns = makeIt(guessDay9);
            for (int i = 0; i < columnsNeeded; i++) {
                final List<String> column = getColumn(columns, i);
                int matches = correctOnesInColumn(combination14, column);
                System.out.println("Day 9 column " + i + " values " + column.toString() + " matches " + matches);
                if (matches >= okNumber) {
                    System.out.println("FOUND!!!");
                    gotIt = true;
                    break;
                }
            }
            columnsNeeded++;
        }
        if (columnsNeeded > maximum) {
            maximum = columnsNeeded;
        }

        columnsNeeded = 1;
        gotIt = false;
        while (!gotIt) {
            final List<String> combination14 = stringToList(matchCombination10);
            System.out.println("Day 10 combination needed: " + combination14.toString());
            List<GuessRequest> guessDay = getDay10(columnsNeeded);
            List<List<String>> columns = makeIt(guessDay);
            for (int i = 0; i < columnsNeeded; i++) {
                final List<String> column = getColumn(columns, i);
                int matches = correctOnesInColumn(combination14, column);
                System.out.println("Day 10 column " + i + " values " + column.toString() + " matches " + matches);
                if (matches >= okNumber) {
                    System.out.println("FOUND!!!");
                    gotIt = true;
                    break;
                }
            }
            columnsNeeded++;
        }
        System.out.println("WE NEED " + maximum + " columns");
    }

//    @Test
    public void calculateNumberOfColumnsNeededForToday() {
        List<GuessRequest> guessDayToday = todaysOne(20);
        List<List<String>> columns = makeIt(guessDayToday);
        for (int i = 0; i < 16; i++) {
            final List<String> column = getColumn(columns, i);

            System.out.println("column " + i + " values " + column.toString());

        }
    }

    private List<GuessRequest> getDay4(int columns) {
        List<GuessRequest> guessRequests = new ArrayList<>();
        guessRequests.add(new GuessRequest(8, "Real Sociedad", 1, "Barcelona", 4, 1, columns));
        guessRequests.add(new GuessRequest(17, "Valencia", 13, "Betis", 4, 1, columns));
        guessRequests.add(new GuessRequest(4, "Athletic Club", 2, "Real Madrid", 4, 1, columns));
        guessRequests.add(new GuessRequest(19, "Leganés", 18, "Villarreal", 4, 1, columns));
        guessRequests.add(new GuessRequest(7, "Espanyol", 5, "Levante", 4, 1, columns));
        guessRequests.add(new GuessRequest(16, "Valladolid", 11, "Alavés", 4, 1, columns));
        guessRequests.add(new GuessRequest(6, "Sevilla", 9, "Getafe", 4, 1, columns));
        guessRequests.add(new GuessRequest(12, "Alcorcón", 7, "Deportivo de La Coruña", 5, 2, columns));
        guessRequests.add(new GuessRequest(18, "Elche", 2, "Mallorca", 5, 2, columns));
        guessRequests.add(new GuessRequest(14, "Lugo", 13, "Oviedo", 5, 2, columns));
        guessRequests.add(new GuessRequest(20, "AD Almería", 4, "Zaragoza", 5, 2, columns));
        guessRequests.add(new GuessRequest(21, "Extremadura", 3, "Las Palmas", 5, 2, columns));
        guessRequests.add(new GuessRequest(6, "Granada", 9, "Rayo Majadahonda", 5, 2, columns));
        guessRequests.add(new GuessRequest(8, "Sporting de Gijón", 10, "Numancia", 5, 2, columns));
        return guessRequests;
    }

    private List<GuessRequest> getDay5(int columns) {
        List<GuessRequest> guessRequests = new ArrayList<>();
        guessRequests.add(new GuessRequest(16, "Rayo Vallecano", 7, "Alavés", 5, 1, columns));
        guessRequests.add(new GuessRequest(3, "Celta de Vigo", 19, "Valladolid", 5, 1, columns));
        guessRequests.add(new GuessRequest(15, "Eibar", 20, "Leganés", 5, 1, columns));
        guessRequests.add(new GuessRequest(5, "Getafe", 9, "Atlético de Madrid", 5, 1, columns));
        guessRequests.add(new GuessRequest(2, "Real Madrid", 4, "Espanyol", 5, 1, columns));
        guessRequests.add(new GuessRequest(11, "Levante", 12, "Sevilla", 5, 1, columns));
        guessRequests.add(new GuessRequest(14, "Villarreal", 18, "Valencia", 5, 1, columns));
        guessRequests.add(new GuessRequest(10, "Betis", 8, "Athletic Club", 5, 1, columns));
        guessRequests.add(new GuessRequest(4, "Mallorca", 5, "Albacete", 6, 2, columns));
        guessRequests.add(new GuessRequest(10, "Oviedo", 20, "Elche", 6, 2, columns));
        guessRequests.add(new GuessRequest(2, "Las Palmas", 1, "Málaga", 6, 2, columns));
        guessRequests.add(new GuessRequest(11, "Reus", 18, "Gimnàstic de Tarragona", 6, 2, columns));
        guessRequests.add(new GuessRequest(12, "Numancia", 17, "AD Almería", 6, 2, columns));
        guessRequests.add(new GuessRequest(13, "Cádiz", 9, "Alcorcón", 6, 2, columns));
        return guessRequests;
    }


    private List<GuessRequest> getDay9(int columns) {
        List<GuessRequest> guessRequests = new ArrayList<>();
        guessRequests.add(new GuessRequest(4, "Valencia", 17, "Leganés", 9, 1, columns));
        guessRequests.add(new GuessRequest(5, "Villarreal", 2, "Atlético de Madrid", 9, 1, columns));
        guessRequests.add(new GuessRequest(1, "Barcelona", 7, "Sevilla", 9, 1, columns));
        guessRequests.add(new GuessRequest(19, "Rayo Vallecano", 8, "Getafe", 9, 1, columns));
        guessRequests.add(new GuessRequest(9, "Eibar", 16, "Athletic Club", 9, 1, columns));
        guessRequests.add(new GuessRequest(20, "Huesca", 11, "Espanyol", 9, 1, columns));
        guessRequests.add(new GuessRequest(6, "Betis", 18, "Valladolid", 9, 1, columns));
        guessRequests.add(new GuessRequest(7, "Oviedo", 8, "Osasuna", 10, 2, columns));
        guessRequests.add(new GuessRequest(3, "Las Palmas", 6, "Numancia", 10, 2, columns));
        guessRequests.add(new GuessRequest(13, "Alcorcón", 2, "Granada", 10, 2, columns));
        guessRequests.add(new GuessRequest(16, "Córdoba", 1, "Deportivo de La Coruña", 10, 2, columns));
        guessRequests.add(new GuessRequest(12, "Lugo", 15, "Gimnàstic de Tarragona", 10, 2, columns));
        guessRequests.add(new GuessRequest(3, "Zaragoza", 11, "Tenerife", 10, 2, columns));
        guessRequests.add(new GuessRequest(9, "Cádiz", 4, "Sporting de Gijón", 10, 2, columns));
        return guessRequests;
    }

    private List<GuessRequest> getDay10(int columns) {
        List<GuessRequest> guessRequests = new ArrayList<>();
        guessRequests.add(new GuessRequest(18, "Girona", 19, "Rayo Vallecano", 9, 1, columns));
        guessRequests.add(new GuessRequest(16, "Athletic Club", 4, "Valencia", 9, 1, columns));
        guessRequests.add(new GuessRequest(13, "Celta de Vigo", 9, "Eibar", 9, 1, columns));
        guessRequests.add(new GuessRequest(15, "Levante", 17, "Leganés", 9, 1, columns));
        guessRequests.add(new GuessRequest(2, "Atlético de Madrid", 12, "Real Sociedad", 9, 1, columns));
        guessRequests.add(new GuessRequest(8, "Getafe", 6, "Betis", 9, 1, columns));
        guessRequests.add(new GuessRequest(14, "Alavés", 5, "Villarreal", 9, 1, columns));
        guessRequests.add(new GuessRequest(7, "Sevilla", 20, "Huesca", 9, 1, columns));
        guessRequests.add(new GuessRequest(2, "Granada", 9, "AD Almería", 18, 2, columns));
        guessRequests.add(new GuessRequest(20, "Mallorca", 3, "Las Palmas", 10, 2, columns));
        guessRequests.add(new GuessRequest(11, "Tenerife", 13, "Alcorcón", 10, 2, columns));
        guessRequests.add(new GuessRequest(12, "Lugo", 9, "Cádiz", 10, 2, columns));
        guessRequests.add(new GuessRequest(15, "Gimnàstic de Tarragona", 7, "Oviedo", 10, 2, columns));
        guessRequests.add(new GuessRequest(19, "Elche", 3, "Zaragoza", 10, 2, columns));
        return guessRequests;
    }



    private List<GuessRequest> todaysOne(int columns) {
        List<GuessRequest> guessRequests = new ArrayList<>();
        guessRequests.add(new GuessRequest(10, "Villarreal", 12, "Valladolid", 6, 1, columns));
        guessRequests.add(new GuessRequest(8, "Levante", 19, "Eibar", 6, 1, columns));
        guessRequests.add(new GuessRequest(2, "Atlético de Madrid", 16, "Celta de Vigo", 6, 1, columns));
        guessRequests.add(new GuessRequest(18, "Getafe", 14, "Mallorca", 6, 1, columns));
        guessRequests.add(new GuessRequest(17, "Espanyol", 7, "Real Sociedad", 6, 1, columns));
        guessRequests.add(new GuessRequest(13, "Valencia", 20, "Leganés", 6, 1, columns));
        guessRequests.add(new GuessRequest(4, "Athletic Club", 11, "Alavés", 6, 1, columns));
        guessRequests.add(new GuessRequest(1, "Sevilla", 3, "Real Madrid", 6, 1, columns));
        guessRequests.add(new GuessRequest(2, "Cádiz", 18, "Deportivo de La Coruña", 6, 2, columns));
        guessRequests.add(new GuessRequest(3, "Zaragoza", 12, "Lugo", 6, 2, columns));
         guessRequests.add(new GuessRequest(7, "Rayo Vallecano", 1, "AD Almería", 6, 2, columns));
        guessRequests.add(new GuessRequest(11, "Ponferradina", 22, "Oviedo", 6, 2, columns));
        guessRequests.add(new GuessRequest(15, "Sporting de Gijón", 17, "Racing de Santander", 6, 2, columns));
        guessRequests.add(new GuessRequest(13, "Tenerife", 5, "Fuenlabrada", 6, 2, columns));
        return guessRequests;
    }


    private List<String> stringToList(String value) {
        String[] values = value.split("");
        return Arrays.asList(values);
    }

    private List<List<String>> makeIt(List<GuessRequest> guessDay4) {
        List<List<String>> combinations = new ArrayList<>();
        for (int i = 0; i < guessDay4.size(); i++) {
            String values = controller.guessResultReduced(guessDay4.get(i)).block();
            List<String> listtt = stringToList(values);
            combinations.add(listtt);
        }
        return combinations;
    }

    private List<String> getColumn(List<List<String>> combinations, int index) {
        return combinations.stream().map(listt -> listt.get(index)).collect(Collectors.toList());
    }

    private int correctOnesInColumn(List<String> combinationNeeded, List<String> columnGuess) {
        int counter = 0;
        for (int i = 0; i < 14; i++) {
            if (combinationNeeded.get(i).equals(columnGuess.get(i))) {
                counter++;
            }
        }
        return counter;
    }

}