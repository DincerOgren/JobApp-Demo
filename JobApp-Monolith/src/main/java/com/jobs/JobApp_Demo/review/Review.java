package com.jobs.JobApp_Demo.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    Long id;
    String review;
    String description;
    Double rating;

    @JsonIgnore
    @ManyToOne
    Company company;
}
