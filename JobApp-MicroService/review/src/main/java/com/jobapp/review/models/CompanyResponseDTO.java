package com.jobapp.review.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDTO {
    Long id;
    String companyName;
    String companyAddress;
    String companyEmail;
    String companyPhone;
}
