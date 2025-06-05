package com.jobapp.review.clients.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class CompanyRestClient {
    @Autowired
    private RestClient restClient;

    public Boolean isCompanyExist(Long companyId) {
        return restClient.get()
                .uri("/companies/company-exist/"+companyId)
                .retrieve()
                .body(Boolean.class);
    }
}
