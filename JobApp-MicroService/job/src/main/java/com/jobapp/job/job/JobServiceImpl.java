package com.jobapp.job.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;


    @Override
    public List<Job> getAllJos() {
        return jobRepository.findAll();
    }

    @Override
    public Boolean addJob(Job job) {

        if (job.getCompanyId() == null){
            log.warn("Company is null when adding new job");
            return false;
        }
        Job savedJob = jobRepository.save(job);
        log.info("Job added successfully with id: {}", savedJob.getId());
        return true;
    }

    @Override
    public Job getJobWithId(String id) {

        Job job = jobRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));

        log.info("Job found with id: {}", id);
        return job;

    }

    @Override
    public boolean updateJobWithId(String id, Job updatedJob){
        Optional<Job> jobToUpdate = jobRepository.findById(String.valueOf(id));
        if(jobToUpdate.isPresent()){
            Job job = jobToUpdate.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setLocation(updatedJob.getLocation());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            jobRepository.save(job);
            log.info("Job updated successfully with id: {}", updatedJob.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteJob(String id) {
        Optional<Job> jobToDelete = jobRepository.findById(String.valueOf(id));
        if(jobToDelete.isPresent()){
            jobRepository.deleteById(String.valueOf(id));
            log.info("Job deleted successfully with id: {}", id);
            return true;
        }
        log.warn("Job not found with id: {}", id);
        return false;
    }

}
