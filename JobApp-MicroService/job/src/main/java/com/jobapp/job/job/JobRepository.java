package com.jobapp.job.job;


import com.jobapp.job.models.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
