package io.mountblue.BlogApplication.dao;


import io.mountblue.BlogApplication.entity.User;
import io.mountblue.BlogApplication.repositery.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        return newUser;
    }

    public User login(User user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if (u.isPresent()) {
            User loginUser = u.get();

            if (passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
                loginUser.setPassword("******");
                return loginUser;
            }
        }
        return null;
    }
}
