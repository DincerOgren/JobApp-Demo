package com.jobapp.company.exceptions;

public class CompanyAlreadyExistException extends RuntimeException{

    public CompanyAlreadyExistException(String companyName) {
        super("Company with name " + companyName + " already exists. Please choose another company name");
    }
}
