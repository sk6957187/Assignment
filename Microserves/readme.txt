Micrservices
---------------------------------
1. Service registration Application (Eureka server) (SpringBoot Project)
run on port number:- 8761 (when not use 8761 the required to manually registered in microservices)
	eureka:
	  client:
	    service-url:
	      defaultZone: https://localhost:8761/eureka

Dependencies:-
1) Eureka discovery server

--------------------------------------------------------
2. Admin server (SpringBoot project)
run on port number: 1111
Dependencies:- 
1) Admin Server

------------------------------------------------------------
Zipkin server(Download zip)

Download zipkin file  url: https//zipkin.io/pages/quickstart.html

zipkin server run through cmd jara -jar<jar-name>
run on port number: 9411


------------------------------------------------------------
Welcome API (SpringBoot Project normal)

run on port number: 8081
Dependencies: 
1) Eureka-discovery-client
2) admin client
3) Zipkin
4) starter web
5) actuator

-----------------------------------------------
Greet API (SpringBoot project normal)

Welcome API (SpringBoot Project normal)
run on port number: 8082
Dependencies: 
1) Eureka-discovery-client
2) admin client
3) Zipkin
4) starter web
5) actuator
6) openfeign







