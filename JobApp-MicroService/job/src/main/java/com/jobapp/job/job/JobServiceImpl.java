package com.jobapp.job.job;

import com.jobapp.job.clients.httpinterface.CompanyServiceClient;
import com.jobapp.job.models.Company;
import com.jobapp.job.models.Job;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    @Autowired
    CompanyServiceClient companyServiceInterface;

    @Override
    public List<Job> getAllJos() {
        return jobRepository.findAll();
    }

    @CircuitBreaker(name = "companyService",fallbackMethod = "addJobFallback")
    @Override
    public Boolean addJob(Job job) {

        if (job.getCompanyId() == null){
            log.warn("Company is null when adding new job");
            return false;
        }

        Company company = companyServiceInterface.getCompanyDetails(job.getCompanyId());
        if (company == null){
            log.warn("Company does not exist when adding new job with id: {}", job.getCompanyId());
            return false;
        }

        Job savedJob = jobRepository.save(job);
        log.info("Job added successfully with id: {}", savedJob.getId());
        return true;
    }

    public boolean addJobFallback(Job job,
                                  Exception exception) {
        System.out.println("FALLBACK CALLED EXCEPTION: "+ exception.getMessage());
        return false;
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
