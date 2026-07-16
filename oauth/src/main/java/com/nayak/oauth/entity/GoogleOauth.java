package com.nayak.oauth.entity;

import lombok.Data;

import java.util.List;

@Data
public class GoogleOauth {

    private String clientId;
    private String clientSecret;
    private List<String> scope;
}
