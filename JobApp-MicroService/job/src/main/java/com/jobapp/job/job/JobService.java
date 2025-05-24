package com.jobapp.job.job;

import java.util.List;

public interface JobService {
    List<Job> getAllJos();

    String addJob(Job job);


    Job getJobWithId(String id);


    boolean updateJobWithId(String id, Job updatedJob);


    boolean deleteJob(String id);
}
