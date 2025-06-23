package com.jobapp.job.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    @NotBlank(message = "Location is required")
    @Size(max = 100, message = "Location must be at most 100 characters")
    private String location;

    @NotNull(message = "Minimum salary is required.")
    @Min(value = 1, message = "Minimum salary must be greater than 0.")
    private Integer minSalary;

    @NotNull(message = "Maximum salary is required.")
    @Min(value = 1, message = "Maximum salary must be greater than 0.")
    private Integer maxSalary;

    @NotNull(message = "Company ID is required")
    @Positive(message = "Company ID must be a positive number")
    private Long companyId;

}
