package io.mountblue.BlogApplication.service;

import io.mountblue.BlogApplication.dao.UserDao;
import io.mountblue.BlogApplication.entity.ResponseStructure;
import io.mountblue.BlogApplication.entity.User;
import io.mountblue.BlogApplication.repositery.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    @Autowired
    private UserDao userDao;

    public ResponseEntity<ResponseStructure<List<User>>> getAllUsers(){
        ResponseStructure<List<User>> res = new ResponseStructure<>();
        List<User> userList = userDao.getAllUsers();
        if(userList.isEmpty()){
            res.setData(null);
            res.setMessage("User not found.");
            res.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            res.setData(userList);
            res.setMessage("User founded.");
            res.setStatus(HttpStatus.OK.value());
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    public ResponseEntity<ResponseStructure<User>> saveUser(User user) {
        ResponseStructure<User> res = new ResponseStructure<>();
        user = userDao.saveUser(user);
        if(user == null){
            res.setData(null);
            res.setMessage("User registered, Please use different email..");
            res.setStatus(HttpStatus.CONFLICT.value());
        } else {
            res.setData(null);
            res.setMessage("Success fully register.");
            res.setStatus(HttpStatus.CREATED.value());
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    public ResponseEntity<ResponseStructure<User>> login(User user) {
        ResponseStructure<User> resp = new ResponseStructure<>();
        User loginUser = userDao.login(user);

        if (loginUser == null) {
            resp.setMessage("Check credentials.");
            resp.setData(null);
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        } else {
            resp.setMessage("Login successful.");
            resp.setData(loginUser);
            resp.setStatus(HttpStatus.OK.value());
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}

