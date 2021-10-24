package com.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ApiClient apiClient(@Autowired RestTemplate restTemplate) {
        return new ApiClient("https://vse-rabotu.ru/", restTemplate);
    }
}
