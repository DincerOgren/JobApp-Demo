package com.jobapp.job.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;


    @Override
    public List<Job> getAllJos() {
        return jobRepository.findAll();
    }

    @Override
    public String addJob(Job job) {

        Job savedJob = jobRepository.save(job);
        return savedJob.toString();
    }

    @Override
    public Job getJobWithId(String id) {

        Job job = jobRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + id));

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
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteJob(String id) {
        Optional<Job> jobToDelete = jobRepository.findById(String.valueOf(id));
        if(jobToDelete.isPresent()){
            jobRepository.deleteById(String.valueOf(id));
            return true;
        }
        return false;
    }

}
