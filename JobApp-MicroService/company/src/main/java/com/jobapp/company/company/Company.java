package com.jobapp.company.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

}
