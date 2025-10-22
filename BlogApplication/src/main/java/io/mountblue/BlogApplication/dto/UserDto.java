package io.mountblue.BlogApplication.dto;


import io.mountblue.BlogApplication.entity.User;
import io.mountblue.BlogApplication.repositery.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDto {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();

    }

    public User saveUser(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if(u.isPresent()){
            return null;
        }

        User newUser = userRepository.save(user);
        return newUser;
    }

    public User login(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()) {
            User loginUser = u.get();
            if (loginUser.getPassword().equals(user.getPassword())) {
                loginUser.setPassword("******");
                return loginUser;
            }
        }
        return null;
    }

}
