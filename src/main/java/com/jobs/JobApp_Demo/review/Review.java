package com.jobs.JobApp_Demo.review;

import com.jobs.JobApp_Demo.company.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String review;


    @ManyToOne
    Company company;
}
