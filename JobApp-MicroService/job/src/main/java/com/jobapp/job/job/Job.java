package com.jobapp.job.job;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "jobs")
@Data
public class Job {

   @Id
    private String id;
    private String title;
    private String description;
    private String location;
    private String minSalary;
    private String maxSalary;

    Long companyId;
}
