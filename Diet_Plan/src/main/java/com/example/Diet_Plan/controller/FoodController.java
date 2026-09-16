package com.example.Diet_Plan.controller;

import com.example.Diet_Plan.model.Food;
import com.example.Diet_Plan.model.ResponseStructure;
import com.example.Diet_Plan.service.FoodServices;
import org.apache.catalina.connector.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/diet")
public class FoodController{

    private static final Logger logger = LoggerFactory.getLogger(FoodController.class);
    private FoodServices foodServices;

    public FoodController(FoodServices foodServices) {
        this.foodServices = foodServices;
    }



    @PostMapping
    public ResponseEntity<ResponseStructure<Food>> generateFood(@RequestBody Food food){
        logger.info("Input food: {}", food );
        return foodServices.saveFood(food);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<ArrayList<Food>>> getAllFood(){

        return foodServices.getAllFood();
    }
}