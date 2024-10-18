# Assignment
(1) Rule Engine with AST
Limitation Each rule keyword shall be seprated by space

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

(4) Operand (<,>,=) -->
currentTree-->type = str
currentTree-->key = key

(5) else -->
Key
Value
if (key == null) {
	key = age;
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


r = age > 30 AND age < 50

(2) WeatherMonitoring

1. TestWeatherApi --> Fetched data with the help of api
url: http://api.openweathermap.org/data/2.5/weather?q=<City>&appid=<API_KEY>
City is user defined parameter
API_KEY is part of meta data in yml file

Store data in to oracle sql

Driver: oracle.jdbc.driver.OracleDriver
DB url: jdbc:oracle:thin:@Sumit11:1521:xe
Username: <>
Password: <>

Show data and store inside table.
2. TestWeatherData --> 
