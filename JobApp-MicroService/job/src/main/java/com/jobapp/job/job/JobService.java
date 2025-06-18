package com.jobapp.job.job;

import com.jobapp.job.models.Job;

import java.util.List;

public interface JobService {
    List<Job> getAllJos();

    Boolean addJob(Job job);


    Job getJobWithId(String id);


    boolean updateJobWithId(String id, Job updatedJob);


    boolean deleteJob(String id);
}
