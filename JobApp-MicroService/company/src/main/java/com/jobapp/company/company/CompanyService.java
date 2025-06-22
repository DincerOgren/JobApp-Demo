package com.jobapp.company.company;

import com.jobapp.company.models.Company;
import com.jobapp.company.models.CompanyRequestDTO;
import com.jobapp.company.models.CompanyResponse;
import com.jobapp.company.models.CompanyResponseDTO;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    CompanyResponse getAllCompanies(Integer pageNumber, Integer pageSize,
                                          String sortBy, String sortOrder, String keyword);

//    Optional<Company> companyExist(Long id);

    CompanyResponseDTO addCompany(CompanyRequestDTO companyRequestDTO);

    Optional<CompanyResponseDTO> getCompanyWithId(Long id);

    CompanyResponseDTO updateCompanyWithId(Long id, CompanyRequestDTO updatedCompanyDTO);

    boolean deleteCompany(Long id);
}
