package com.project.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.model.WeatherDTO;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.*;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class WeatherRepository {
    private static final Logger logger = LoggerFactory.getLogger(WeatherRepository.class);
    private static final String API_KEY = "3d66b4662f9e189700ece9721f3d1d85";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@Sumit11:1521:xe";
    private static final String USER = "system";
    private static final String PASS = "tiger";
    private static final String host = "192.168.0.1";
    private static final String recipient = "sk6957187@gmail.com";
    private static final String sender = "sumitkumarmk32@gmail.com";
    private ArrayList<Double> temperatureHistory = new ArrayList<>();

    public WeatherDTO fetchWeather(String city) {
        city = city.toUpperCase();
        try {
            String url = BASE_URL + city + "&appid=" + API_KEY;
            String response = Request.Get(url).execute().returnContent().asString();
            return parseWeather(response, city);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WeatherDTO parseWeather(String response, String city) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            double tempKelvin = root.get("main").get("temp").asDouble();
            double feelsLikeKelvin = root.get("main").get("feels_like").asDouble();
            String condition = root.get("weather").get(0).get("main").asText();
            long timestamp = root.get("dt").asLong();
            double tempCelsius = tempKelvin - 273.15;
            double feelsLikeCelsius = feelsLikeKelvin - 273.15;
            WeatherDTO weatherDTO = new WeatherDTO(tempCelsius, feelsLikeCelsius, condition, timestamp);
            storeWeatherData(weatherDTO, city);
            return weatherDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void storeWeatherData(WeatherDTO weatherDTO, String city) {

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "INSERT INTO WeatherData (city, temp_celsius, feels_like_celsius, condition, Added_time)\n" +
                    "VALUES (?, ?, ?, ?, TO_TIMESTAMP('1970-01-01', 'YYYY-MM-DD') + NUMTODSINTERVAL(?, 'SECOND'))";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, city);
            stmt.setDouble(2, weatherDTO.getTemp());
            stmt.setDouble(3, weatherDTO.getFeelsLike());
            stmt.setString(4, weatherDTO.getCondition());
            stmt.setLong(5, weatherDTO.getTimestamp());
            stmt.executeUpdate();
            stmt = conn.prepareStatement("commit");
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTemp(String city){
        double temp = 0.0;
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT TEMP_CELSIUS FROM weatherData WHERE city = '"+city+"' ORDER BY ADDED_TIME DESC FETCH FIRST 1 ROWS ONLY";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                temp = (rs.getDouble("TEMP_CELSIUS"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    public ArrayList<ArrayList<String>> getDailySummary(String city) {
        city = city.toUpperCase();
        ArrayList<ArrayList<String>> summaries = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "SELECT AVG(TEMP_CELSIUS) AS avg_temp, MAX(TEMP_CELSIUS) AS max_temp, MIN(TEMP_CELSIUS) AS min_temp, CONDITION FROM weatherData" +
                    " WHERE CITY ='" +city+ "' AND Added_time >= TRUNC(SYSDATE) GROUP BY CONDITION";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            if(rs != null){
                logger.info("data avail.");
            }
            while (rs.next()) {
                ArrayList<String> summary = new ArrayList<>();
                summary.add(String.valueOf(rs.getDouble(1)));
                summary.add(String.valueOf(rs.getDouble(2)));
                summary.add(String.valueOf(rs.getDouble(3)));
                summary.add(rs.getString(4));

                summaries.add(summary);
            }
            logger.info(summaries.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summaries;
    }

    public String setAlert(String city, double thresholdTemp) {
        city = city.toUpperCase();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String query = "SELECT TEMP_CELSIUS FROM weatherData WHERE city = '"+city+"' ORDER BY ADDED_TIME DESC FETCH FIRST 2 ROWS ONLY";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                temperatureHistory.add(rs.getDouble("TEMP_CELSIUS"));
            }
            if (temperatureHistory.size() >= 2) {
                double latestTemp = temperatureHistory.get(0);
                double previousTemp = temperatureHistory.get(1);
                if (latestTemp > thresholdTemp && previousTemp > thresholdTemp) {
                    String alert = "Alert: Temperature in " + city + " has exceeded " + thresholdTemp + "°C for two consecutive updates.";
                    sendEmail(alert, city);
                    return alert;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private void sendEmail(String alertMessage, String city) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        if(session != null){
            logger.info(" connected... ");
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Temperature Alert for " + city);
            message.setText(alertMessage);
            Transport.send(message);
            logger.info("Alert email sent!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
