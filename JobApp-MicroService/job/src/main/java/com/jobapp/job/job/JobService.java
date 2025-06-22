package com.jobapp.job.job;

import com.jobapp.job.models.Job;
import com.jobapp.job.models.JobRequestDTO;
import com.jobapp.job.models.JobResponse;
import com.jobapp.job.models.JobResponseDTO;

import java.util.List;

public interface JobService {


    JobResponse getAllJobs(Integer pageNumber, Integer pageSize, String sortBy,
                           String sortOrder, String keyword);

    JobResponseDTO addJob(JobRequestDTO jobRequestDTO);


    JobResponseDTO getJobWithId(String id);


    JobResponseDTO updateJobWithId(String id, JobRequestDTO updatedJob);


    boolean deleteJob(String id);
}
