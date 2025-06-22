package com.jobapp.job.job;

import com.jobapp.job.clients.httpinterface.CompanyServiceClient;
import com.jobapp.job.exceptions.CompanyNotFoundException;
import com.jobapp.job.exceptions.CompanyNullException;
import com.jobapp.job.exceptions.JobNotFoundException;
import com.jobapp.job.exceptions.ServiceUnavailableException;
import com.jobapp.job.models.*;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {


    private final JobRepository jobRepository;

    private final CompanyServiceClient companyServiceInterface;

    private final RabbitTemplate rabbitTemplate;

    private final ModelMapper modelMapper;

    private int attempts = 0;

    @Override
    public JobResponse getAllJobs(Integer pageNumber, Integer pageSize, String sortBy,
                                  String sortOrder, String keyword) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Job> jobsPage = (keyword != null && !keyword.isEmpty()) ?
                jobRepository.findByTitleContainingIgnoreCase(keyword, pageDetails) :
                jobRepository.findAll(pageDetails);

        List<Job> jobs = jobsPage.getContent();
        if (jobs.isEmpty()){
            log.warn("Company list is empty");
            return null;
        }

        List<JobResponseDTO> jobDTOList = jobs.stream()
                .map(company -> modelMapper
                        .map(company,JobResponseDTO.class))
                .toList();


        return new JobResponse(
                jobDTOList,
                jobsPage.getNumber(),
                jobsPage.getSize(),
                jobsPage.getTotalElements(),
                jobsPage.getTotalPages(),
                jobsPage.isLast()
        );
    }

    //@CircuitBreaker(name = "companyService",fallbackMethod = "addJobFallback")
    @Retry(name = "jobRetryCompany",fallbackMethod = "addJobFallback")
    @Override
    public JobResponseDTO addJob(JobRequestDTO jobRequestDTO) {

        log.info("addJob retry attempts: {}", ++attempts);

        if (jobRequestDTO.getCompanyId() == null){
            log.warn("Company is null when adding new job");
            throw new CompanyNullException(jobRequestDTO.getTitle());
        }

        try {

            CompanyResponseDTO company = companyServiceInterface.getCompanyDetails(jobRequestDTO.getCompanyId());

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Company not found for id: {}", jobRequestDTO.getCompanyId());
                throw new CompanyNotFoundException(jobRequestDTO.getCompanyId());
            } else {
                throw e; // For other errors, rethrow or handle differently
            }
        }
        Job jobToSave = modelMapper.map(jobRequestDTO, Job.class);
        Job savedJob = jobRepository.save(jobToSave);
        log.info("Job added successfully with id: {}", savedJob.getId());

        rabbitTemplate.convertAndSend("job.exchange","job.tracking",
                Map.of("companyId",savedJob.getCompanyId(),"status","CREATED"));

        return modelMapper.map(savedJob, JobResponseDTO.class);
    }

    public JobResponseDTO addJobFallback(JobRequestDTO job,
                                  Exception exception) {
        System.out.println("FALLBACK CALLED EXCEPTION: "+ exception.getMessage());
        log.error("FALLBACK CALLED EXCEPTION: {}", exception.getMessage());
        throw new ServiceUnavailableException("Service is temporarily unavailable. Please try again later.");
    }

    @Override
    public JobResponseDTO getJobWithId(String id) {

        Job job = jobRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new JobNotFoundException("Job not found with ID: " + id));

        log.info("Job found with id: {}", id);

        return modelMapper.map(job, JobResponseDTO.class);

    }

    @Override
    public JobResponseDTO updateJobWithId(String id, JobRequestDTO updatedJob){
        Optional<Job> jobToUpdate = jobRepository.findById(String.valueOf(id));

        if(jobToUpdate.isPresent()){
            Job job = jobToUpdate.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setLocation(updatedJob.getLocation());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());

            log.info("Job updated successfully with id: {}", job.getId());
            return modelMapper.map(jobRepository.save(job), JobResponseDTO.class);
        }
        throw new JobNotFoundException("Job not found with ID: " + id);
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
