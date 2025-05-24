package com.jobs.JobApp_Demo.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jobs.JobApp_Demo.job.Job;
import com.jobs.JobApp_Demo.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String companyName;
    String companyAddress;
    String companyEmail;
    Integer companyPhone;


    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    List<Review> reviews;



    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("company")
    List<Job> jobs;

}
