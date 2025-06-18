package com.jobapp.job.clients.httpinterface;

import com.jobapp.job.models.Company;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CompanyServiceClient {

    @GetExchange("/companies/company-exist/{id}")
    Company getCompanyDetails(@PathVariable Long id);
}