package com.example.Diet_Plan.model;


public class Food {
    private String foodName;
    private int calories;
    private String diet;

    public void setFoodName(String foodName){
        this.foodName = foodName;
    }

    public String getFoodName(){
        return this.foodName;
    }

    public void setCalories(int calories){
        this.calories = calories;
    }

    public int getCalories(){
        return this.calories;
    }
    
    public void setDiet(String diet){
        this.diet = diet;
    }

    public String getDiet(){
        return this.diet;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodName='" + foodName + '\'' +
                ", calories=" + calories +
                ", diet='" + diet + '\'' +
                '}';
    }
}