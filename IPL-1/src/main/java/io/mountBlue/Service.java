package io.mountBlue;

import java.sql.*;
import java.util.*;

public class Service {

    private static Connection sqlConnection(){
        Connection conn = null;
        String password = "Sumit@123";
        String user = "root";
        String url = "jdbc:mysql://localhost:3306/IPL";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Connection build failed");
            e.getMessage();
        }
        return conn;
    }

    public void uploadMatchDataInMysql(List<Match> matchesData) {
        String fetchMatchId = "select match_id from matches";
        String uploadQuery = "INSERT INTO matches "
                + "(match_id, season, city, match_date, team1, team2, toss_winner, "
                + "toss_decision, result, dl_applied, winner, win_by_runs, win_by_wickets, "
                + "player_of_match, venue, umpire1, umpire2, umpire3) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = sqlConnection();

            Set<Integer> matchIds = new HashSet<>();
            PreparedStatement ps = conn.prepareStatement(fetchMatchId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                matchIds.add(rs.getInt("match_id"));
            }

            ps= conn.prepareStatement(uploadQuery);

            for (Match m : matchesData) {

                if(matchIds.contains(m.getId())){
                    continue;
                }

                ps.setInt(1, m.getId());
                ps.setInt(2, m.getSeason());
                ps.setString(3, m.getCity());
                ps.setString(4, m.getDate() == null ? null : m.getDate());
                ps.setString(5, m.getTeam1());
                ps.setString(6, m.getTeam2());
                ps.setString(7, m.getTossWinner());
                ps.setString(8, m.getTossDecision());
                ps.setString(9, m.getResult());
                ps.setInt(10, m.getDlApplied());
                ps.setString(11, m.getWinner());
                ps.setInt(12, m.getWinByRuns());
                ps.setInt(13, m.getWinByWickets());
                ps.setString(14, m.getPlayerOfMatch());
                ps.setString(15, m.getVenue());
                ps.setString(16, m.getUmpire1());
                ps.setString(17, m.getUmpire2());
                ps.setString(18, m.getUmpire3());

                ps.addBatch();
            }
            ps.executeBatch();
//            System.out.println("Matches uploaded (skipping duplicates).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void uploadDeliveriesDataInMysql(List<Deliveries> deliveriesData) {
        // Use INSERT IGNORE so duplicate primary keys are skipped
        String sql = "INSERT IGNORE INTO deliveries ("
                + "match_id, inning, batting_team, bowling_team, `over`, ball, "
                + "batsman, non_striker, bowler, is_super_over, wide_runs, bye_runs, "
                + "legbye_runs, noball_runs, penalty_runs, batsman_runs, extra_runs, "
                + "total_runs, player_dismissed, dismissal_kind, fielder) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = sqlConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.out.println("Connection build failed");
                return;
            }

            conn.setAutoCommit(false); // batch insert is faster

            for (Deliveries d : deliveriesData) {
                ps.setInt(1,  d.getMatchId());
                ps.setInt(2,  d.getInning());
                ps.setString(3, d.getBattingTeam());
                ps.setString(4, d.getBowlingTeam());
                ps.setInt(5,  d.getOver());
                ps.setInt(6,  d.getBall());
                ps.setString(7, d.getBatsman());
                ps.setString(8, d.getNonStriker());
                ps.setString(9, d.getBowler());
                ps.setInt(10, d.getSuperOver());
                ps.setInt(11, d.getWideRuns());
                ps.setInt(12, d.getByeRuns());
                ps.setInt(13, d.getLegbyeRuns());
                ps.setInt(14, d.getNoballRuns());
                ps.setInt(15, d.getPenaltyRuns());
                ps.setInt(16, d.getBatsmanRuns());
                ps.setInt(17, d.getExtraRuns());
                ps.setInt(18, d.getTotalRuns());
                ps.setString(19, d.getPlayerDismissed());
                ps.setString(20, d.getDismissalKind());
                ps.setString(21, d.getFielder());

                ps.addBatch();
            }

            int[] counts = ps.executeBatch();
            conn.commit();
            System.out.println("Inserted rows (excluding duplicates): " + counts.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Integer> findMatchYearWise(List<Match> matchesData) {
        Map<Integer, Integer> matchMap = new HashMap<>();

        if (matchesData == null || matchesData.isEmpty()) {
            return matchMap;
        }

        for (int i = 0; i < matchesData.size(); i++) {
            Match match = matchesData.get(i);
            int season = match.getSeason();
            matchMap.put(season, matchMap.getOrDefault(season, 0) + 1);
        }

        return matchMap;
    }

    public Map<String, Map<Integer, Integer>> findMatchWonTeamWise(List<Match> matchData) {
        Map<String, Map<Integer, Integer>> matchWonTeamWise = new TreeMap<>();

        if (matchData == null || matchData.isEmpty()) {
            return matchWonTeamWise;
        }

        for (int i = 0; i < matchData.size(); i++) {
            Match match = matchData.get(i);
            int season = match.getSeason();
            String winningTeam = match.getWinner();
            if (winningTeam == null || winningTeam.trim().isEmpty()) {
                continue;
            }

            Map<Integer, Integer> yearMap = matchWonTeamWise.getOrDefault(winningTeam, new TreeMap<>());
            yearMap.put(season, yearMap.getOrDefault(season, 0) + 1);
            matchWonTeamWise.put(winningTeam, yearMap);
        }
        return matchWonTeamWise;
    }

    public Map<String, Object> findMatchDataById(int id, List<Match> matchData) {
        Map<String, Object> matchDetails = new HashMap<>();

        if (matchData == null || matchData.isEmpty()) {
            return matchDetails;
        }

        for (int i = 0; i < matchData.size(); i++) {
            Match match = matchData.get(i);

            if (match.getId() == id) {
                matchDetails.put("id", match.getId());
                matchDetails.put("season", match.getSeason());
                matchDetails.put("city", match.getCity());
                matchDetails.put("date", match.getDate());
                matchDetails.put("team1", match.getTeam1());
                matchDetails.put("team2", match.getTeam2());
                matchDetails.put("toss_winner", match.getTossWinner());
                matchDetails.put("toss_decision", match.getTossDecision());
                matchDetails.put("result", match.getResult());
                matchDetails.put("winner", match.getWinner());

                break;
            }
        }

        return matchDetails;
    }

    public List<Map<String, String>> findLast_5_Match(int n, List<Match> matchData) {
        List<Map<String, String>> matchDetails = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        if (matchData == null || matchData.isEmpty()) {
            return matchDetails;
        }

        int numberOfLines = matchData.size();
        map.put("Total Matches", String.valueOf(numberOfLines - 1));
        matchDetails.add(map);

        for (int i = 1; i <= n && i < numberOfLines; i++) {
            Match match = matchData.get(numberOfLines - i);
            Map<String, String> data = new HashMap<>();

            data.put("Match id", String.valueOf(match.getId()));
            data.put("city", match.getCity());
            data.put("date", match.getDate());
            data.put("team1", match.getTeam1());
            data.put("team2", match.getTeam2());
            data.put("toss win", match.getTossWinner());
            data.put("toss decision", match.getTossDecision());
            data.put("result", match.getResult());

            matchDetails.add(data);
        }

        return matchDetails;
    }

    public Map<String, Integer> findExtraRun(int year, List<Match> matchesData, List<Deliveries> deliveriesData) {
        Map<String, Integer> extraRuns = new TreeMap<>();

        if (matchesData == null || deliveriesData == null || matchesData.isEmpty() || deliveriesData.isEmpty()) {
            return extraRuns;
        }

        Set<Integer> matchIds = new TreeSet<>();

        for (int i = 0; i < matchesData.size(); i++) {
            Match match = matchesData.get(i);

            if (match.getSeason() == year) {
                matchIds.add(match.getId());
            }
        }

        for (int i = 0; i < deliveriesData.size(); i++) {
            Deliveries deliveries = deliveriesData.get(i);
            int id = deliveries.getMatchId();
            boolean flag = matchIds.contains(id);

            if (flag) {
                String team = deliveries.getBattingTeam();
                int extraRun = deliveries.getExtraRuns();
                extraRuns.merge(team, extraRun, Integer::sum);
            }
        }
        return extraRuns;
    }

    public List<String> findBestEconomicalBowler(int year, List<Deliveries> deliveriesData, List<Match> matchesData) {

        List<String> bestEconomicalBowler = new ArrayList<>();
        if (deliveriesData == null || matchesData == null || matchesData.isEmpty() || deliveriesData.isEmpty()) {
            return bestEconomicalBowler;
        }

        Set<Integer> matchIds = new HashSet<>();
        for (int i = 0; i < matchesData.size(); i++) {
            Match match = matchesData.get(i);

            if (match.getSeason() == year) {
                matchIds.add(match.getId());
            }
        }

        if (matchIds.isEmpty()) {
            return bestEconomicalBowler;
        }

        Map<String, Integer> runsConceded = new HashMap<>();
        Map<String, Integer> ballsBowled = new HashMap<>();

        for (int i = 0; i < deliveriesData.size(); i++) {
            Deliveries deliveries = deliveriesData.get(i);
            int matchId = deliveries.getMatchId();
            if (!matchIds.contains(matchId))
                continue;

            String bowler = deliveries.getBowler();
            int totalRuns = deliveries.getTotalRuns();
            int wide = deliveries.getWideRuns();
            int noBall = deliveries.getNoballRuns();
            runsConceded.merge(bowler, totalRuns, Integer::sum);

            if (wide == 0 && noBall == 0) {
                ballsBowled.merge(bowler, 1, Integer::sum);
            }
        }

        String bestBowler = null;
        double bestEconomy = Double.MAX_VALUE;
        TreeSet<Double> bestEconomys = new TreeSet<>();

        for (String bowler : runsConceded.keySet()) {
            int runs = runsConceded.getOrDefault(bowler, 0);
            int balls = ballsBowled.getOrDefault(bowler, 0);
            if (balls <= 6)
                continue;
            double overs = balls / 6.0;
            double economy = runs / overs;
            bestEconomys.add(economy);

            if (economy < bestEconomy) {
                bestEconomy = economy;
                bestBowler = bowler;
            }
        }
//
//        System.out.println("-".repeat(40));
//        System.out.println(bestBowler + " (" + String.format("%.2f", bestEconomy) + ")");
        bestEconomicalBowler.add(bestBowler + " (" + String.format("%.2f", bestEconomy) + ")");
        return bestEconomicalBowler;
    }

    public Map<String, Integer> findPartnership(String pName, List<Deliveries> deliveriesData) {
        Map<String, Integer> partnership = new TreeMap<>();

        if (deliveriesData == null || deliveriesData.isEmpty()) {
            return partnership;
        }

        for (Deliveries deliveries : deliveriesData) {
            if (deliveries.getBatsman().equalsIgnoreCase(pName)) {
                int runs = deliveries.getBatsmanRuns();
                String nonStricker = deliveries.getNonStriker();
                String key = pName + " " + nonStricker;
                partnership.put(key, partnership.getOrDefault(key, 0) + runs);
            }
        }

        return partnership;
    }

    public Map<Integer, Map<String, String>> findTopScorePerTeamPerSession(List<Deliveries> deliveriesData, List<Match> matchData) {
        Map<Integer, Map<String, String>> topScorePerTeam = new HashMap<>();
        Map<Integer, Integer> matchIdToSeason = new HashMap<>();

        if (deliveriesData == null || matchData == null || deliveriesData.isEmpty() || matchData.isEmpty()) {
            return topScorePerTeam;
        }

        for (Match match : matchData) {
            matchIdToSeason.put(match.getId(), match.getSeason());
        }

        Map<Integer, Map<String, Map<String, Integer>>> runsMap = new HashMap<>();

        for (Deliveries d : deliveriesData) {
            Integer season = matchIdToSeason.get(d.getMatchId());
            if (season == null)
                continue;

            String battingTeam = d.getBattingTeam();
            String batsman = d.getBatsman();
            int runs = d.getBatsmanRuns();

            Map<String, Map<String, Integer>> teamMap = runsMap.get(season);
            if (teamMap == null) {
                teamMap = new HashMap<>();
                runsMap.put(season, teamMap);
            }

            Map<String, Integer> batsmanMap = teamMap.get(battingTeam);
            if (batsmanMap == null) {
                batsmanMap = new HashMap<>();
                teamMap.put(battingTeam, batsmanMap);
            }

            int totalRuns = batsmanMap.getOrDefault(batsman, 0) + runs;
            batsmanMap.put(batsman, totalRuns);
        }

        for (Map.Entry<Integer, Map<String, Map<String, Integer>>> seasonEntry : runsMap.entrySet()) {
            int season = seasonEntry.getKey();
            Map<String, String> teamTopScorers = new HashMap<>();

            for (Map.Entry<String, Map<String, Integer>> teamEntry : seasonEntry.getValue().entrySet()) {
                String team = teamEntry.getKey();
                Map<String, Integer> batsmanRuns = teamEntry.getValue();

                String topBatsman = null;
                int maxRuns = -1;

                for (Map.Entry<String, Integer> br : batsmanRuns.entrySet()) {
                    if (br.getValue() > maxRuns) {
                        maxRuns = br.getValue();
                        topBatsman = br.getKey();
                    }
                }

                if (topBatsman != null) {
                    teamTopScorers.put(team, topBatsman + " (" + maxRuns + ")");
                }
            }
            topScorePerTeam.put(season, teamTopScorers);
        }
        return topScorePerTeam;
    }

    public Map<String, String>  findPlayerHeighestRunLast5OverAgainstMI(List<Match> matchData, List<Deliveries> deliveriesData, String teamBol, int season) {

        Map<String, String> result = new HashMap<>();
        Map<String, Set<Integer>> teamMatches = new HashMap<>();

        if (matchData == null || deliveriesData == null || matchData.isEmpty() || deliveriesData.isEmpty() || teamBol == null || season == 0) {
            return result;
        }

        for (Match match : matchData) {
            if ((match.getSeason() == season) && (match.getTeam1().equalsIgnoreCase(teamBol) || match.getTeam2().equalsIgnoreCase(teamBol))) {
                String opponent = match.getTeam1().equalsIgnoreCase(teamBol) ? match.getTeam2() : match.getTeam1();

                if (!teamMatches.containsKey(opponent)) {
                    teamMatches.put(opponent, new HashSet<>());
                }

                teamMatches.get(opponent).add(match.getId());
            }
        }

        for (String team : teamMatches.keySet()) {
            Set<Integer> matchIds = teamMatches.get(team);

            Map<String, Integer> batsmanRuns = new HashMap<>();

            for (Deliveries d : deliveriesData) {
                if (matchIds.contains(d.getMatchId()) && d.getBattingTeam().equalsIgnoreCase(team) && d.getOver() >= 16) {
                    String batsman = d.getBatsman();
                    int runs = d.getBatsmanRuns();
                    int totalRuns = batsmanRuns.getOrDefault(batsman, 0) + runs;
                    batsmanRuns.put(batsman, totalRuns);
                }
            }

            String topBatsman = null;
            int maxRuns = -1;
            for (Map.Entry<String, Integer> entry : batsmanRuns.entrySet()) {
                if (entry.getValue() > maxRuns) {
                    maxRuns = entry.getValue();
                    topBatsman = entry.getKey();
                }
            }

            if (topBatsman != null) {
                result.put(team, topBatsman + " (" + maxRuns + " runs) ");
            }
        }
        return result;
    }
}
