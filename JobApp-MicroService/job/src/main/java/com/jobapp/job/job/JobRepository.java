package com.jobapp.job.job;


import com.jobapp.job.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
    Page<Job> findByTitleContainingIgnoreCase(String keyword, Pageable pageDetails);
}
