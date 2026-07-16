package com.nayak.oauth.config;

import com.nayak.oauth.entity.GoogleOauth;
import com.nayak.oauth.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final ReadExternalConfig readExternalConfig;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, ReadExternalConfig readExternalConfig) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.readExternalConfig = readExternalConfig;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() throws IOException {

        GoogleOauth oauth = readExternalConfig.readGoogleOauth();

        ClientRegistration google =
                ClientRegistration.withRegistrationId("google")
                        .clientId(oauth.getClientId())
                        .clientSecret(oauth.getClientSecret())
                        .scope(oauth.getScope())
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                        .tokenUri("https://oauth2.googleapis.com/token")
                        .userInfoUri("https://openidconnect.googleapis.com/v1/userinfo")
                        .userNameAttributeName(IdTokenClaimNames.SUB)
                        .clientName("Google")
                        .build();

        return new InMemoryClientRegistrationRepository(google);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                );

        return http.build();
    }
}