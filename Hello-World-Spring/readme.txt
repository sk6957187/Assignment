mvn clean install

Start java jar file
-----------------------------
When just double click on .jar file then open at port at 8080
java -jar Hello-World-Spring-0.0.1-SNAPSHOT.jar --server.port=9090
(run at jdk17+ upto 21)

Run the JAR with the spring.config.location parameter:-
	java -jar your-app.jar --spring.config.location=file:C:/config/application.yml
	java -jar Hello-World-Spring-0.0.1-SNAPSHOT.jar --spring.config.additional-location="file:emailConfig.yml"(same location) use additional-location then read default .yml file as well as additional .yml file. When use with only .location then read only additional file


Use Multiple Configuration Files
	If you want to load multiple config files (e.g., application.yml and custom.yml), use:
	java -jar Hello-World-Spring-0.0.1-SNAPSHOT.jar --spring.config.location="file:A:/workspace/Assignment/Hello-		World-Spring/target/classes/emailConfig.yml","file:A:/workspace/Assignment/Hello-World-	Spring/target/classes/application.yml","file:A:/workspace/Assignment/Hello-World-	Spring/target/classes/application.properties" --server.port=9090

Stop the Process by Port 
-------------------------------
netstat -ano | findstr :8080
taskkill /F /PID 17031
or
ps -ef | grep your-app.jar
kill <PID>
or
ctrl+c

