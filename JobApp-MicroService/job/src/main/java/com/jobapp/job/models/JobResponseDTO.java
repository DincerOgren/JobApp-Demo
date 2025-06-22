package com.jobapp.job.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDTO {
    private String id;
    private String title;
    private String description;
    private String location;
    private Integer minSalary;
    private Integer maxSalary;

    Long companyId;
}
