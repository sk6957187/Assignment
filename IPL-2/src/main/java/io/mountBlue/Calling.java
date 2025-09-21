package io.mountBlue;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Calling {
    public void calling(){
        Scanner sc = new Scanner(System.in);
        Service service = new Service();
        Database database = new Database();
        List<Match> matchData = database.fetchMatchData();
        List<Deliveries> deliveriesData = database.fetchDeliveriesData();

        boolean flag = true;
        while (flag) {

            System.out.println("\n---------------------Menu------------------------------------");
            System.out.println("1. Number of matches played per year of all the years in IPL ");
            System.out.println("2. Number of matches won of all teams over all the years of IPL.");
            System.out.println("3. For the year 2016 get the extra runs conceded per team.");
            System.out.println("4. For the year 2015 get the top economical bowlers.");
            System.out.println("5. Get match details by match id.");
            System.out.println("6. Last 5 match details");
            System.out.println("7. Partnership player");
            System.out.println("8. Player highest score per team per season");
            System.out.println("9. Find player by team with highest score in last five overs");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            switch (choice) {

                case 0:
                    flag = false;
                    System.out.println("Thank you for using..!!");
                    break;

                case 1:
                    Map<Integer, Integer> result1 = service.findMatchYearWise(matchData);
                    System.out.println(result1);
                    break;

                case 2:

                    Map<String, Map<Integer, Integer>> result2 = service.findMatchWonTeamWise(matchData);

                    for (Map.Entry<String, Map<Integer, Integer>> teamEntry : result2.entrySet()) {
                        String team = teamEntry.getKey();
                        System.out.println("Team: " + team);
                        Map<Integer, Integer> yearWiseWins = teamEntry.getValue();
                        for (Map.Entry<Integer, Integer> yearEntry : yearWiseWins.entrySet()) {
                            System.out.println("   Year: " + yearEntry.getKey() + " → Wins: " + yearEntry.getValue());
                        }
                        System.out.println();
                    }
                    break;

                case 3:

                    System.out.print("Enter year: ");
                    int year = sc.nextInt();
                    Map<String, Integer> result3 = service.findExtraRun(year, matchData, deliveriesData);
                    if (result3.isEmpty()) {
                        System.out.println("Data not found..!!");
                        break;
                    }
                    for (Map.Entry<String, Integer> entry : result3.entrySet()) {
                        System.out.println("Team: " + entry.getKey() + " -> extra: " + entry.getValue());
                    }
                    break;

                case 4:
                    System.out.print("Enter year: ");
                    year = sc.nextInt();
                    List<String> result = service.findBestEconomicalBowler(year, deliveriesData, matchData);
                    if (result.isEmpty()) {
                        System.out.println("Data not found..!!");
                    } else {
                        System.out.println(result);
                    }
                    break;

                case 5:
                    System.out.print("Enter match id: ");
                    int matchId = sc.nextInt();
                    Map<String, Object> matchDataById = service.findMatchDataById(matchId, matchData);
                    if (matchDataById.isEmpty()) {
                        System.out.println("Data not found..!!");
                        break;
                    }
                    for (Map.Entry<String, Object> entry : matchDataById.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;

                case 6:
                    List<Map<String, String>> matchDetails = service.findLast_5_Match(5, matchData);
                    for (Map<String, String> match : matchDetails) {
                        System.out.println(match);
                    }
                    break;

                case 7:
                    System.out.print("Enter player name: ");
                    sc.nextLine();
                    String pName = sc.nextLine();
                    Map<String, Integer> partnership = service.findPartnership(pName, deliveriesData);
                    for (Map.Entry<String, Integer> entry : partnership.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;

                case 8:
                    Map<Integer, Map<String, String>> topScorePerTeam = service.findTopScorePerTeamPerSession(deliveriesData, matchData);
                    for (Map.Entry<Integer, Map<String, String>> sessionEntry : topScorePerTeam.entrySet()) {
                        Integer session = sessionEntry.getKey();
                        System.out.println("Season: " + session);
                        Map<String, String> teamTopScorer = sessionEntry.getValue();
                        for (Map.Entry<String, String> teamEntry : teamTopScorer.entrySet()) {
                            System.out.println(teamEntry.getKey() + " -> " + teamEntry.getValue());
                        }
                        System.out.println();
                    }
                    break;

                case 9:
                    Map<String, String> result6 = service.findPlayerHeighestRunLast5OverAgainstMI(matchData, deliveriesData
                            , "Mumbai Indians", 2017);
                    for (Map.Entry<String, String> entry : result6.entrySet()) {
                        System.out.println(entry.getKey() + "-> " + entry.getValue());
                    }
                    break;

                default:
                    System.err.println("wrong input..!!");
            }
        }

    }
}
