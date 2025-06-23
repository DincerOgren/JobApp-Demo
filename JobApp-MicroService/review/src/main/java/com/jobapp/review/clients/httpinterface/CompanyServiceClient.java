package com.jobapp.review.clients.httpinterface;

import com.jobapp.review.models.CompanyResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CompanyServiceClient {

    @GetExchange("/api/companies/{id}")
    CompanyResponseDTO getCompanyDetails(@PathVariable Long id);
}
