package com.nayak.oauth.service;

import com.nayak.oauth.entity.UserEntity;
import com.nayak.oauth.repsitery.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {

        OAuth2User oauthUser = super.loadUser(userRequest);

        String name = oauthUser.getAttribute("name");
        String email = oauthUser.getAttribute("email");
        String picture = oauthUser.getAttribute("picture");
        String sub = oauthUser.getAttribute("sub");
        Boolean emailVerified = oauthUser.getAttribute("email_verified");

        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(UserEntity::new);
        user.setName(name);
        user.setEmail(email);
        user.setPicture(picture);
        user.setSub(sub);
        user.setLoginThrough("GOOGLE");
        user.setEmailVerified(emailVerified);

        userRepository.save(user);

        return oauthUser;
    }
}
