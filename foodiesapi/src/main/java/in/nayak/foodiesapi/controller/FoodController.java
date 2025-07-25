package in.nayak.foodiesapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.nayak.foodiesapi.entity.FoodEntity;
import in.nayak.foodiesapi.io.FoodRequest;
import in.nayak.foodiesapi.io.FoodResponse;
import in.nayak.foodiesapi.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/foods")
@AllArgsConstructor
public class FoodController {

    @Autowired
    public final FoodService foodService;

    @PostMapping
    public FoodResponse addFood(@RequestPart("food") String foodString,
                                @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        FoodRequest request = null;
        try{
            request = objectMapper.readValue(foodString, FoodRequest.class);
        } catch(JsonProcessingException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON formal");
        }
        FoodResponse response = foodService.addFood(request, file);
        return response;
    }

    @PutMapping("/{id}")
    public FoodResponse updateFood(@RequestPart("food") String foodSting,
                                    @RequestPart("file") MultipartFile file){
        ObjectMapper objectMapper = new ObjectMapper();
        FoodEntity request = null;
        try {
            request = objectMapper.readValue(foodSting, FoodEntity.class);
        } catch (JsonProcessingException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        }
        FoodResponse response = foodService.updateFood(request, file);
        return response;

    }

    @GetMapping
    public List<FoodResponse> readFoods(){
        return foodService.readFoods();
    }

    @GetMapping("/{id}")
    public FoodResponse readFood(@PathVariable String id){
        return foodService.readFood(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id){
        foodService.deleteFood(id);
    }


}
