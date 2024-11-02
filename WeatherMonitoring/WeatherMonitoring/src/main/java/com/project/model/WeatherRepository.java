package com.project.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.obj.OracleSqlConfig;
import com.project.obj.UiConfig;
import com.project.obj.WeatherApiConfig;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class WeatherRepository {
    private final Logger logger = LoggerFactory.getLogger(WeatherRepository.class);
    private final String API_KEY;
    private final String BASE_URL;
    private final String driver;
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private final String uiBaseApi;
    private final ArrayList<Double> temperatureHistory = new ArrayList<>();
    public WeatherRepository(WeatherApiConfig weatherApiConfig, OracleSqlConfig oracleSqlConfig, UiConfig uiConfig) {
        API_KEY = weatherApiConfig.getApiKey();
        BASE_URL = weatherApiConfig.getBaseUrl();
        driver = oracleSqlConfig.getDriver();
        DB_URL = oracleSqlConfig.getUrl();
        USER =  oracleSqlConfig.getUsername();
        PASS = oracleSqlConfig.getPassword();
        uiBaseApi = uiConfig.getUiBaseApi();
    }
    public String getUiBaseApi() {
        return uiBaseApi;
    }

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
            double pressure = root.get("main").get("pressure").asDouble();
            double humidity = root.get("main").get("humidity").asDouble();
            double seaLevel = root.get("main").has("sea_level") ? root.get("main").get("sea_level").asDouble() : 0;
            double visibility = root.get("visibility").asDouble();
            double windSpeed = root.get("wind").get("speed").asDouble();
            String condition = root.get("weather").get(0).get("main").asText();
            long timestamp = root.get("dt").asLong();

            double tempCelsius = tempKelvin - 273.15;
            double feelsLikeCelsius = feelsLikeKelvin - 273.15;

            WeatherDTO weatherDTO = new WeatherDTO(tempCelsius, feelsLikeCelsius, condition, timestamp, pressure, humidity, seaLevel, visibility, windSpeed);
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
            String query = "INSERT INTO WeatherData (city, temp_celsius, feels_like_celsius, condition, pressure, humidity, sea_level, visibility, wind_speed, added_time)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, FROM_TZ(TO_TIMESTAMP('1970-01-01', 'YYYY-MM-DD') + NUMTODSINTERVAL(?, 'SECOND'), 'UTC') AT TIME ZONE 'Asia/Kolkata')";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, city);
            stmt.setDouble(2, weatherDTO.getTemp());
            stmt.setDouble(3, weatherDTO.getFeelsLike());
            stmt.setString(4, weatherDTO.getCondition());
            stmt.setDouble(5, weatherDTO.getPressure());
            stmt.setDouble(6, weatherDTO.getHumidity());
            stmt.setDouble(7, weatherDTO.getSeaLevel());
            stmt.setDouble(8, weatherDTO.getVisibility());
            stmt.setDouble(9, weatherDTO.getWindSpeed());
            stmt.setLong(10, weatherDTO.getTimestamp());
            stmt.executeUpdate();
            stmt = conn.prepareStatement("commit");
            stmt.executeUpdate(); // Commit the transaction
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getDailySummary(String city) {
        city = city.toUpperCase();
        ArrayList<ArrayList<String>> summaries = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "SELECT ROUND(AVG(TEMP_CELSIUS),2) AS avg_temp, MAX(TEMP_CELSIUS) AS max_temp, MIN(TEMP_CELSIUS) AS min_temp, CONDITION FROM weatherData" +
                    " WHERE CITY ='" +city+ "' AND Added_time >= TRUNC(SYSDATE) GROUP BY CONDITION FETCH FIRST 1 ROW ONLY";
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

    public ArrayList<String> setAlert(String city, double thresholdTemp) {
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
                String alert ;
                String status;
                ArrayList<String> res = new ArrayList<>();
                if (latestTemp > thresholdTemp && previousTemp > thresholdTemp) {
                     alert = "Alert: Temperature in " + city + " has exceeded " + thresholdTemp + "°C for two consecutive updates.";
                     status = "NO";
//                    sendEmail(alert, city);

                }else{
                     alert = "Good";
                     status = "YES";
                }
                res.add(alert);
                res.add(status);
                return res;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private void sendEmail(String alertMessage, String city) {
        Properties properties = System.getProperties();
        String host = "192.168.0.1";
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        if(session != null){
            logger.info(" connected... ");
        }
        try {
            Message message = new MimeMessage(session);
            String sender = "sumitkumarmk32@gmail.com";
            message.setFrom(new InternetAddress(sender));
            String recipient = "sk6957187@gmail.com";
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

