package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.io.UserRequest;
import in.nayak.foodiesapi.io.UserResponse;

public interface UserService {
   UserResponse registerUser(UserRequest request);
   String findByUserId();
}
