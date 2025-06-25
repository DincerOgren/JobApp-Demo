package com.jobapp.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5,10,1);
    }

    @Bean
    public KeyResolver hostKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress().getHostName());
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("review-service",r  -> r
                        .path("/companies/reviews/**")
                        .filters(f->f.circuitBreaker(config -> config
                                .setName("appBreaker")
                                .setFallbackUri("forward:/fallback/reviews")))
                        .uri("lb://REVIEW-SERVICE"))
                .route("company-service",r  -> r
                    .path("/companies/**")
                    .filters(f->f
                            .circuitBreaker(config -> config
                            .setName("appBreaker")
                            .setFallbackUri("forward:/fallback/company")))
                    .uri("lb://COMPANY-SERVICE"))
                .route("job-service",r  -> r
                        .path("/jobs/**")
                        .filters(f->f
                                .retry(retryConfig -> retryConfig
                                        .setRetries(5)
                                        .setMethods(HttpMethod.GET,HttpMethod.POST))
                                .requestRateLimiter(config -> config
                                        .setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(hostKeyResolver()))
                                .circuitBreaker(config -> config
                                .setName("appBreaker")
                                .setFallbackUri("forward:/fallback/jobs")))
                        .uri("lb://JOB-SERVICE"))
                .route("auth-service",r->r
                        .path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))
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
