API
------------------------------------
http://localhost:8080/api/foods (post)
goto; body -> form-data
    key          value
    ---         -----
    food   ->     {
                    "name": "test",
                    "description": "Very sweet",
                    "category": "Ice-cream",
                    "price": 100
                }
    file    ->     photo.jpg

