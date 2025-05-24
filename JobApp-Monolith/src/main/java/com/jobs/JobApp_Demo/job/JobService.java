package com.jobs.JobApp_Demo.job;

import java.util.List;

public interface JobService {
    List<Job> getAllJos();

    String addJob(Job job);

    Job getJobWithId(Long id);

    boolean updateJobWithId(Long id, Job updatedJob);

    boolean deleteJob(Long id);
}
