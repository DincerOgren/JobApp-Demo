package com.jobs.JobApp_Demo.job;

import com.jobs.JobApp_Demo.company.Company;
import jakarta.persistence.EntityNotFoundException;
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
        Company company = job.getCompany();
        if (company == null) {
            return "Company is null";
        }
        Job savedJob = jobRepository.save(job);
        return savedJob.toString();
    }

    @Override
    public Job getJobWithId(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with ID: " + id));

        return job;

    }

    @Override
    public boolean updateJobWithId(Long id, Job updatedJob){
        Optional<Job> jobToUpdate = jobRepository.findById(id);
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
    public boolean deleteJob(Long id) {
        Optional<Job> jobToDelete = jobRepository.findById(id);
        if(jobToDelete.isPresent()){
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
