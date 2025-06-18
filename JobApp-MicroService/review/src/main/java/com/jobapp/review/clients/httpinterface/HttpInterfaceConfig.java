package com.jobapp.review.clients.httpinterface;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class HttpInterfaceConfig {

    public final  String SERVICE_NAME = "company-service";

//    @Bean
//    @LoadBalanced
//    public RestClient.Builder restClientBuilder() {
//        return RestClient.builder();
//    }

    @Bean
    // USING REST CLIENT
    public CompanyServiceClient reviewToCompanyInterface(RestClient.Builder builder) {
        RestClient restClient = builder
                                    .baseUrl("http://"+SERVICE_NAME)
                                    .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                                     ((request, response) -> Optional.empty()))
//                                    .defaultStatusHandler(HttpStatusCode::is4xxClientError,
//                                            (request,response) -> {
//                                                throw new RuntimeException("Client error: "+ response.getStatusCode());
//                                            })
                                    .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        CompanyServiceClient service = factory.createClient(CompanyServiceClient.class);
        return service;
    }
}
