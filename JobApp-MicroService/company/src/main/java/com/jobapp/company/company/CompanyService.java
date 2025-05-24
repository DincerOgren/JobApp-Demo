package com.jobapp.company.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    String addCompany(Company company);

    Company getCompanyWithId(Long id);

    boolean updateCompanyWithId(Long id, Company updatedCompany);

    boolean deleteCompany(Long id);
}
