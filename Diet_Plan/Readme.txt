POST /api/v1/food/: The user can submit a food item that should contain a non-empty name with a positive calorie value.
GET /api/v1/food/: Returns all the food items stored in the database that is then used for adding items to the diet.
POST /api/v1/limit: The calorie limit for a particular day can be set using this endpoint when a positive value is sent.
POST /api/v1/limit: Fetches the limit for a given date (provided as a query parameter).
POST /api/v1/diet: Through this endpoint, the diet of a particular date is created if a list of food items is not null, each entry can have more than one quantity.
GET /api/v1/diet: Returns the diet for a given date (provided as a query parameter).
GET /api/v1/diet/calculate: Calculates and returns the total calorie intake from the food items in the diet of a given date.

Additional requirement:
If data for the date and user (as multiple users may have diets on the same day, but a user cannot have more than one on that particular day) already exists, the entry should be updated instead of creating a new entry.



wrap text in vs code
run java project in vs code