package com.ashokit.Filter;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Service
public class MyFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Filter() executed...");

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        Set<String> keySet = headers.keySet();

        if (!keySet.contains("Secret")) {
            throw new RuntimeException("Missing Secret header");
        }
 
        List<String> secrets = headers.get("Secret");
        if (secrets == null || secrets.isEmpty() || !secrets.get(0).equals("ashokit@123")) {
            throw new RuntimeException("Invalid Secret value");
        }

        return chain.filter(exchange);
    }
}
