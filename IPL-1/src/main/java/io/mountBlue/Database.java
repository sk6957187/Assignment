package io.mountBlue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final int MATCH_ID = 0;
    private static final int MATCH_SESSION = 1;
    private static final int MATCH_CITY = 2;
    private static final int MATCH_DATE = 3;
    private static final int MATCH_TEAM1 = 4;
    private static final int MATCH_TEAM2 = 5;
    private static final int MATCH_TOSS_WINNER = 6;
    private static final int MATCH_TOSS_DECISION = 7;
    private static final int MATCH_RESULT = 8;
    private static final int MATCH_DL_APPLIED = 9;
    private static final int MATCH_WINNER = 10;
    private static final int MATCH_WIN_BY_RUNS = 11;
    private static final int MATCH_WIN_BY_WICKETS = 12;
    private static final int MATCH_PLAYER_OF_MATCH = 13;
    private static final int MATCH_VENUE = 14;
    private static final int MATCH_UMPIRE1 = 15;
    private static final int MATCH_UMPIRE2 = 16;
    private static final int MATCH_UMPIRE3 = 17;

    private static final int DELIVERIES_MATCH_ID = 0;
    private static final int DELIVERIES_INNING = 1;
    private static final int DELIVERIES_BATTING_TEAM = 2;
    private static final int DELIVERIES_BOWLING_TEAM = 3;
    private static final int DELIVERIES_OVER = 4;
    private static final int DELIVERIES_BALL = 5;
    private static final int DELIVERIES_BATSMAN = 6;
    private static final int DELIVERIES_NON_STRIKER = 7;
    private static final int DELIVERIES_BOWLER = 8;
    private static final int DELIVERIES_SUPER_OVER = 9;
    private static final int DELIVERIES_WIDE_RUNS = 10;
    private static final int DELIVERIES_BYE_RUNS = 11;
    private static final int DELIVERIES_LEG_BY_RUNS = 12;
    private static final int DELIVERIES_NO_BALL_RUNS = 13;
    private static final int DELIVERIES_PENALTY_RUNS = 14;
    private static final int DELIVERIES_BATSMAN_RUNS = 15;
    private static final int DELIVERIES_EXTRA_RUNS = 16;
    private static final int DELIVERIES_TOTAL_RUNS = 17;
    private static final int DELIVERIES_PLAYER_DISMISSED = 18;
    private static final int DELIVERIES_DISMISSAL_KIND = 19;
    private static final int DELIVERIES_FIELDER = 20;

    private static int getIntField(String[] fields, int index) {
        if (fields.length > index && !fields[index].isEmpty()) {
            return Integer.parseInt(fields[index]);
        }
        return 0;
    }

    private static String getStringField(String[] fields, int index) {
        if (fields.length > index && !fields[index].isEmpty()) {
            return fields[index];
        }
        return null;
    }

    public List<Match> fetchMatchData() {
        final String matchPath = "meta-data/matches.csv";
        List<Match> matchesData = new ArrayList<>();
        File file = new File(matchPath);

        if (!file.exists()) {
            System.out.println("File not found: " + matchPath);
            return null;
        }

        try {
            BufferedReader ref = new BufferedReader(new FileReader(file));
            String line;
            boolean isHeader = true;

            while ((line = ref.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                Match match = new Match();

                match.setId(getIntField(fields, MATCH_ID));
                match.setSeason(getIntField(fields, MATCH_SESSION));
                match.setCity(getStringField(fields, MATCH_CITY));
                match.setDate(getStringField(fields, MATCH_DATE));
                match.setTeam1(getStringField(fields, MATCH_TEAM1));
                match.setTeam2(getStringField(fields, MATCH_TEAM2));
                match.setTossWinner(getStringField(fields, MATCH_TOSS_WINNER));
                match.setTossDecision(getStringField(fields, MATCH_TOSS_DECISION));
                match.setResult(getStringField(fields, MATCH_RESULT));
                match.setDlApplied(getIntField(fields, MATCH_DL_APPLIED));
                match.setWinner(getStringField(fields, MATCH_WINNER));
                match.setWinByRuns(getIntField(fields, MATCH_WIN_BY_RUNS));
                match.setWinByWickets(getIntField(fields, MATCH_WIN_BY_WICKETS));
                match.setPlayerOfMatch(getStringField(fields, MATCH_PLAYER_OF_MATCH));
                match.setVenue(getStringField(fields, MATCH_VENUE));
                match.setUmpire1(getStringField(fields, MATCH_UMPIRE1));
                match.setUmpire2(getStringField(fields, MATCH_UMPIRE2));
                match.setUmpire3(getStringField(fields, MATCH_UMPIRE3));

                matchesData.add(match);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return matchesData;
    }

    public List<Deliveries> fetchDeliveriesData() {
        final String deliveriesPath = "meta-data/deliveries.csv";
        List<Deliveries> deliveriesData = new ArrayList<>();
        File file = new File(deliveriesPath);

        if (!file.exists()) {
            System.out.println("File not found: " + deliveriesPath);
            return null;
        }

        try {
            BufferedReader ref = new BufferedReader(new FileReader(file));
            String line;
            boolean isHeader = true;

            while ((line = ref.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] fields = line.split(",");
                Deliveries delivery = new Deliveries();

                delivery.setMatchId(getIntField(fields, DELIVERIES_MATCH_ID));
                delivery.setInning(getIntField(fields, DELIVERIES_INNING));
                delivery.setBattingTeam(getStringField(fields, DELIVERIES_BATTING_TEAM));
                delivery.setBowlingTeam(getStringField(fields, DELIVERIES_BOWLING_TEAM));
                delivery.setOver(getIntField(fields, DELIVERIES_OVER));
                delivery.setBall(getIntField(fields, DELIVERIES_BALL));
                delivery.setBatsman(getStringField(fields, DELIVERIES_BATSMAN));
                delivery.setNonStriker(getStringField(fields, DELIVERIES_NON_STRIKER));
                delivery.setBowler(getStringField(fields, DELIVERIES_BOWLER));
                delivery.setSuperOver(getIntField(fields, DELIVERIES_SUPER_OVER));
                delivery.setWideRuns(getIntField(fields, DELIVERIES_WIDE_RUNS));
                delivery.setByeRuns(getIntField(fields, DELIVERIES_BYE_RUNS));
                delivery.setLegbyeRuns(getIntField(fields, DELIVERIES_LEG_BY_RUNS));
                delivery.setNoballRuns(getIntField(fields, DELIVERIES_NO_BALL_RUNS));
                delivery.setPenaltyRuns(getIntField(fields, DELIVERIES_PENALTY_RUNS));
                delivery.setBatsmanRuns(getIntField(fields, DELIVERIES_BATSMAN_RUNS));
                delivery.setExtraRuns(getIntField(fields, DELIVERIES_EXTRA_RUNS));
                delivery.setTotalRuns(getIntField(fields, DELIVERIES_TOTAL_RUNS));
                delivery.setPlayerDismissed(getStringField(fields,DELIVERIES_PLAYER_DISMISSED));
                delivery.setDismissalKind(getStringField(fields, DELIVERIES_DISMISSAL_KIND));
                delivery.setFielder(getStringField(fields, DELIVERIES_FIELDER));

                deliveriesData.add(delivery);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return deliveriesData;
    }

}
