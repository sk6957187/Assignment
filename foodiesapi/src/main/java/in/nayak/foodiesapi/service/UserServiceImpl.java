package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.entity.UserEntity;
import in.nayak.foodiesapi.io.UserRequest;
import in.nayak.foodiesapi.io.UserResponse;
import in.nayak.foodiesapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public String findByUserId(){
        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity loggedInUser =
                userRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new UsernameNotFoundException("User not " +
                        "found"));
        return loggedInUser.getId();
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser = convertToEntity(request);
        Optional<UserEntity> userExist = userRepository.findByEmail(newUser.getEmail());
        if (userExist.isPresent()) {
            throw new RuntimeException("User already exists");
        }
        newUser = userRepository.save(newUser);
        return convertToResponse(newUser);
    }

    private UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    private UserResponse convertToResponse(UserEntity registeredUser) { // ✅ renamed
        return UserResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .build();
    }
}
