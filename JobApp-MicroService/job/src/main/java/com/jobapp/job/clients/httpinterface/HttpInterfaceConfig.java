package com.jobapp.job.clients.httpinterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class HttpInterfaceConfig {

    private final String SERVICE_NAME = "company-service";



    @Bean
    public CompanyServiceClient jobToCompanyServiceClient(RestClient.Builder builder) {
        RestClient restClient = builder
                .baseUrl("http://" + SERVICE_NAME)
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                ((request, response) -> Optional.empty()))
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(CompanyServiceClient.class);
    }
}