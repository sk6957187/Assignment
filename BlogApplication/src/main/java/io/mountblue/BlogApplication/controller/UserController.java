package io.mountblue.BlogApplication.controller;



import io.mountblue.BlogApplication.entity.ResponseStructure;
import io.mountblue.BlogApplication.entity.User;
import io.mountblue.BlogApplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseStructure<List<User>>> getAllUser(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<User>> register(@RequestBody User user){

        return userService.saveUser(user);
    }


}
