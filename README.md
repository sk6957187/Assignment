# Assignment
(1) Rule Engine with AST
Keyword
- AND, OR, >, <, =
Keword should not be part of rule string

Data storage
for rule: oracle sql
meta data: yml file
rule1 = age < 10
Tree
Node1 (age,<,10)

rule2 = age < 20 AND age > 5
Node1 (age,<,20)
Node2 (age,>,5)
Root = AND
Root->left = Node1
Root->right = Node2

Node1 = true
Node2 = true
root = AND

r = (2 + 2) * (3 + 5) = 32
               *
       +            +
     2    2      3     5

r = 2 + 2 * 3 + 5 = 13
             +
         *        5
  +          3
2   2

Stack = new Stack
Tree = root
Stack.push(root)
CurrentTree = root
Iterator on posix
(1) ( -->

(2) ) -->
(3) Operator (AND, OR) -->
if currentTree-->type == null
	currentTree-->type = str
else
	



(4) Operand (<,>,=) -->
currentTree-->type = str
currentTree-->key = key

(5) else -->
if (key == null) {
	key = str;
} else {
	currentTree-->value = str
	key = null
}


Example:

r = age > 30
if (key == null) {
	key = age;
} else {
	currentTree-->value = str
}


r = (age > 30) AND (age < 50)

Stack
0,1,2,3,4
top=4
pop = 4
top=3
pop = 3
top=2
push=5
top=3
0,1,2,5

(2) WeatherMonitoring

1. TestWeatherApi --> Fetched data with the help of api
url: http://api.openweathermap.org/data/2.5/weather?q=<City>&appid=<API_KEY>
City is user defined parameter
API_KEY is part of meta data in yml file

Store data in to oracle sql

Driver: oracle.jdbc.driver.OracleDriver
DB url: jdbc:oracle:thin:@<db_name>:<db_port>:xe
Username: <db_username>
Password: <db_password>
Sender mail: <mail>@gmail.com
Resever mail: <mail>@gmail.com



Show data and store inside table.

2. TestWeatherData --> Fetched data in table and calculate average tempurature, find minmun tempurature in hole day and find maximum tempurute of hole day.

3. TestsetAlert() --> Fetched last two data(temperature) in table with respect to city and compare both the data is above in 35° then genrate Alert message.

4. TemperatureConversion() --> Fetched last data(temperature) of respect to city and convert cellieus to kelvin and cellieus to fahrenheit.
