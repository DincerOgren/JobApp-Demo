package com.jobapp.job.exceptions;

public class CompanyNullException extends RuntimeException {

    public CompanyNullException(String jobTitle) {
        super("Company field is empty while adding job: " +jobTitle +". Please provide a existing company id.");
    }
}
