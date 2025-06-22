package com.jobapp.company.company;

import com.jobapp.company.models.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyName(@NotBlank(message = "Name is required") @Size(max = 50) String companyName);

    Page<Company> findAll(Specification<Company> spec, Pageable pageDetails);
}
