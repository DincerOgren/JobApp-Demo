package com.jobapp.job.clients.httpinterface;

import com.jobapp.job.models.CompanyResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CompanyServiceClient {

    @GetExchange("/api/companies/{id}")
    CompanyResponseDTO getCompanyDetails(@PathVariable Long id);
}