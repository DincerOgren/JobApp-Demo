package com.jobapp.company.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Optional<Company> companyExist(Long id){
        return companyRepository.findById(id);
    }

    @Override
    public String addCompany(Company company) {
        Company savedComp = companyRepository.save(company);
        return "Company added successfully"+savedComp.toString();
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companyList =  companyRepository.findAll();
        if (companyList.isEmpty()){
            log.warn("Company list is empty");
        }
        return companyList;
    }

    @Override
    public Company getCompanyWithId(Long id) {
        Company comp = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        log.info("Found company with id " + id);
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
            log.info("Company updated successfully");
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCompany(Long id) {
        Optional<Company> companyToDelete = companyRepository.findById(id);
        if(companyToDelete.isPresent()){
            companyRepository.deleteById(id);
            log.info("Company deleted successfully");
            return true;
        }
        log.info("Company not found with id: {}",id);
        return false;
    }
}
