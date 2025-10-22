package io.mountblue.BlogApplication.controller;

import io.mountblue.BlogApplication.entity.AuthenticationRequest;
import io.mountblue.BlogApplication.entity.AuthenticationResponse;
import io.mountblue.BlogApplication.entity.User;
import io.mountblue.BlogApplication.security.AppUserDetailsService;
import io.mountblue.BlogApplication.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userDetailsService.getUserByEmail(request.getEmail());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);

        AuthenticationResponse response = new AuthenticationResponse(jwtToken, user.getName(), user.getId(), user.getEmail());

        return ResponseEntity.ok(response);
    }

}

