package com.jobapp.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("company-service",r  -> r
                    .path("/companies/**")
                    .uri("lb://COMPANY-SERVICE"))
                .route("job-service",r  -> r
                        .path("/jobs/**")
                        .uri("lb://JOB-SERVICE"))
                .route("review-service",r  -> r
                        .path("/companyreviews/**")
                        .uri("lb://REVIEW-SERVICE"))
                .route("eureka-sever",r  -> r
                        .path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main","/"))
                        .uri("http://localhost:8761"))
                .route("eureka-server-static",r  -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();


    }
}
