package com.jobapp.company.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(max = 50)
    private String companyName;

    @NotBlank(message = "Address is required")
    @Size(max = 150)
    String companyAddress;

    @NotBlank(message = "Email is required")
    @Email
    String companyEmail;

    @NotBlank(message = "Company phone is required")
    @Size(max = 20)
    String companyPhone;
}
