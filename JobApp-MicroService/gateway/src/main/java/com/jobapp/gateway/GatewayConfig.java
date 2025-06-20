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
                    .filters(f->f.circuitBreaker(config -> config
                            .setName("appBreaker")
                            .setFallbackUri("forward:/fallback/company")))
                    .uri("lb://COMPANY-SERVICE"))
                .route("job-service",r  -> r
                        .path("/jobs/**")
                        .filters(f->f.circuitBreaker(config -> config
                                .setName("appBreaker")
                                .setFallbackUri("forward:/fallback/jobs")))
                        .uri("lb://JOB-SERVICE"))
                .route("review-service",r  -> r
                        .path("/companyreviews/**")
                        .filters(f->f.circuitBreaker(config -> config
                                .setName("appBreaker")
                                .setFallbackUri("forward:/fallback/reviews")))
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
