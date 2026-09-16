package com.example.Diet_Plan.dao;

import com.example.Diet_Plan.model.Food;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class FoodDao {

    @Value("${FoodFileName}")
    private String foodFileName;

    public Food save(Food food) {

        try {
            File file = new File(foodFileName);

            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            try (FileWriter writer = new FileWriter(file, true)) {

                writer.write("Food Name: " + food.getFoodName());
                writer.write(", Calories: " + food.getCalories());
                writer.write(", Diet: " + food.getDiet());
                writer.write(System.lineSeparator());
            }

            return food;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Food> getAllFood() {

        ArrayList<Food> foods = new ArrayList<>();

        File file = new File(foodFileName);

        if (!file.exists()) {
            return foods;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                // Skip empty lines
                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split(", ");

                if (data.length != 3) {
                    continue;
                }

                String[] foodNameData = data[0].split(": ", 2);
                String[] caloriesData = data[1].split(": ", 2);
                String[] dietData = data[2].split(": ", 2);

                if (foodNameData.length != 2 ||
                        caloriesData.length != 2 ||
                        dietData.length != 2) {
                    continue;
                }

                String foodName = foodNameData[1];
                int calories = Integer.parseInt(caloriesData[1]);
                String diet = dietData[1];

                Food food = new Food();

                food.setFoodName(foodName);
                food.setCalories(calories);
                food.setDiet(diet);

                foods.add(food);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return foods;
    }
}