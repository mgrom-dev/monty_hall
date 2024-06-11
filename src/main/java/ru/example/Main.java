package ru.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Main {
    private static final int COUNT_TESTS = 1000;
    private static final int COUNT_DOORS = 3;

    public static void main(String[] args) {
        Map<Integer, Results> results = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < COUNT_TESTS; i++) {
            /* создаем двери с выигрышем за одной из них */
            List<Door> doors = new ArrayList<>();
            for (int j = 0; j < COUNT_DOORS; j++)
                doors.add(new Door(j));
            doors.get(random.nextInt(COUNT_DOORS)).behindTheDoor = Results.WIN;

            /* выбираем случайную дверь и запоминаем id */
            int doorChoiceId = doors.get(random.nextInt(COUNT_DOORS)).id;

            /* открываем проигрышные двери, пока не останется 2 двери */
            while (doors.size() > 2) {
                int index = random.nextInt(doors.size());
                Door door = doors.get(index);
                if (door.behindTheDoor == Results.WIN || door.id == doorChoiceId)
                    continue;
                door.isOpen = true;
                doors.remove(door);
            }

            /* меняем выбор и открываем дверь */
            Results result = doors.get(0).id == doorChoiceId ? doors.get(1).behindTheDoor : doors.get(0).behindTheDoor;
            results.put(i, result);
        }

        printResults(results);

        SummaryStatistics summary = new SummaryStatistics();
        for (Results result : results.values()) {
            summary.addValue(result.ordinal());
        }

        double mean = summary.getMean();
        double stdDev = summary.getStandardDeviation();

        System.out.println("Mean: " + mean);
        System.out.println("Standard Deviation: " + stdDev);
    }

    private static void printResults(Map<Integer, Results> results) {
        int wins = 0;
        int losses = 0;

        for (Results result : results.values()) {
            if (result == Results.WIN) {
                wins++;
            } else {
                losses++;
            }
        }

        System.out.println("Wins: " + wins);
        System.out.println("Losses: " + losses);
    }
}