package com.jobs.JobApp_Demo.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies=companyService.getAllCompanies();
        if(companies.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id) {
        return new ResponseEntity<>(companyService.getCompanyWithId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String>  addCompany(@RequestBody Company company) {
        company.setId(null);
        return new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody Company company) {

        if(companyService.updateCompanyWithId(id, company)) {

            return new ResponseEntity<>("Company uptdated succesfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Company is not found",HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id)){
            return new ResponseEntity<>("Company deleted succesfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Company is not found",HttpStatus.NOT_FOUND);
    }

}
