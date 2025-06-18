package com.game.project.GameV1.controller;


import com.game.project.GameV1.model.Car;
import com.game.project.GameV1.service.BattleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
public class BattleController {

    private final BattleService battleService = new BattleService();



    @PostMapping("/battle")
    public String startBattle(@RequestBody Car[] cars) {
        return battleService.battle(cars[0], cars[1]);
    }

    @GetMapping("/name")
    public String test() {
        return "My name is: Sumit";
    }

    public int square(int a) {
        return a * a;
    }
}

