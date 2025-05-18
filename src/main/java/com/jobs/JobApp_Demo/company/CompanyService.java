package com.jobs.JobApp_Demo.company;

import com.jobs.JobApp_Demo.job.Job;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    String addCompany(Company company);

    Company getCompanyWithId(Long id);

    boolean updateCompanyWithId(Long id, Company updatedCompany);

    boolean deleteCompany(Long id);
}
