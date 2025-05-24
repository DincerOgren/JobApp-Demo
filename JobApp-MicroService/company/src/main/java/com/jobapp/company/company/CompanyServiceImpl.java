package com.jobapp.company.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public String addCompany(Company company) {
        Company savedComp = companyRepository.save(company);
        return "Company added successfully"+savedComp.toString();
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyWithId(Long id) {
        Company comp = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return comp;
    }

    @Override
    public boolean updateCompanyWithId(Long id, Company updatedCompany) {
        Optional<Company> companyToUpdate = companyRepository.findById(id);
        if(companyToUpdate.isPresent()){
            Company company = companyToUpdate.get();
            company.setCompanyName(updatedCompany.getCompanyName());
            company.setCompanyAddress(updatedCompany.getCompanyAddress());
            company.setCompanyPhone(updatedCompany.getCompanyPhone());
            company.setCompanyEmail(updatedCompany.getCompanyEmail());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCompany(Long id) {
        Optional<Company> companyToDelete = companyRepository.findById(id);
        if(companyToDelete.isPresent()){
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
