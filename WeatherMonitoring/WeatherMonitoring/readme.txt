Edit configuration config parameter -->
server meta-data/app_env_config.yml

To build project -->
mvn clean install (java file)
npm run build (react file)


To run build jar files -->
Go to current directory and run following command
java -jar target/weather-monitoringV2-1.0.0-SNAPSHOT.jar server meta-data/app_env_config.yml

Render .ftl -->
create separate AppView class and import dropwizard-views-freemarker

after creating jar file in react copy js file in build/static/js and store create new file build-1.0.0 (in react)

In react add configuration -->
index.html,
 
react jar file add in assets


Summery:-
Developed a robust weather monitoring system using OpenWeatherMap API for real-time weather analysis. The backend is powered by Java and Oracle SQL, focusing on efficient data storage and processing. The frontend, built with React, provides an intuitive user interface to visualize weather trends, summaries, and alerts. Integrated business logic, data aggregation, and alerting mechanisms for comprehensive reporting and enhanced user experience.


