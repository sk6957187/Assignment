package com.nayak.oauth.controller;

import com.nayak.oauth.config.ReadExternalConfig;
import com.nayak.oauth.entity.MetaDataEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class HomeController {

    private final ReadExternalConfig readExternalConfig;

    public HomeController (ReadExternalConfig read) {
        this.readExternalConfig = read;
    }


    @GetMapping("/")
    public String home() throws IOException {

        MetaDataEntity metaDataEntity= readExternalConfig.readMetadata();
        return "Welcome to " + metaDataEntity.getName() + " " + metaDataEntity.getVillage();
    }

    @GetMapping("/user")
    public Map<String,Object> user(@AuthenticationPrincipal OAuth2User user){

        return user.getAttributes();
    }

}
