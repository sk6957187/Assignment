package com.example.Diet_Plan.service;

import com.example.Diet_Plan.dao.FoodDao;
import com.example.Diet_Plan.exceptions.Exception;
import com.example.Diet_Plan.model.Food;
import com.example.Diet_Plan.model.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class FoodServices {

    private FoodDao foodDao;

    public FoodServices(FoodDao foodDao){
        this.foodDao = foodDao;
    }

    public ResponseEntity<ResponseStructure<Food>> saveFood(Food food) {
        ResponseStructure<Food> res = new ResponseStructure<>();

        if(food.getFoodName() == null || food.getFoodName().isBlank()){
//            res.setMsg("Food name is empty.");
//            res.setData(food);
//            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<ResponseStructure<Food>>(res, HttpStatus.BAD_REQUEST);
            throw new Exception("Food name is empty");
        }
        if(food.getCalories() <= 0){
            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
            res.setMsg("Calories value is 0.");
            res.setData(food);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        Food foodSave = foodDao.save(food);
        if(foodSave == null){
            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
            res.setMsg("Calories value is 0.");
            res.setData(food);
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMsg("Food Entity is created");
        res.setData(food);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    public ResponseEntity<ResponseStructure<ArrayList<Food>>> getAllFood() {
        ResponseStructure<ArrayList<Food>> res = new ResponseStructure<>();

        ArrayList<Food> foods = foodDao.getAllFood();

        if(foods.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food content not found in data base.");
//            return new RuntimeException("Food not available in data base.", HttpStatus.NOT_FOUND);
        }

        res.setMsg("Data found");
        res.setData(foods);
        res.setStatusCode(HttpStatus.OK.value());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
