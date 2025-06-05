package com.jobapp.review.clients.restclient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String SERVICE_NAME="company-service";

//    @Bean
//    @LoadBalanced
//    public RestClient.Builder eurekaRestClientBuilder() {
//        return RestClient.builder();
//    }


    @Bean
    public RestClient restClient(RestClient.Builder builder)
    {
        return builder
                .baseUrl("http://"+SERVICE_NAME)
                .build();
    }
}
