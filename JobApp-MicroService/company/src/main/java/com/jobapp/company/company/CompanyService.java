package com.jobapp.company.company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<Company> getAllCompanies();

    Optional<Company> companyExist(Long id);

    String addCompany(Company company);

    Company getCompanyWithId(Long id);

    boolean updateCompanyWithId(Long id, Company updatedCompany);

    boolean deleteCompany(Long id);
}
