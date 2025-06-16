package com.jobs.JobApp_Demo.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("jobs")
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobList=jobService.getAllJos();
        if(jobList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(jobList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getAllJobs(@PathVariable Long id) {
        return new ResponseEntity<>(jobService.getJobWithId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String>  addJob(@RequestBody Job job) {
        job.setId(null);
        if (job.getCompany() == null || job.getCompany().getId() == null) {
            return new ResponseEntity<>("Can't create job without company", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jobService.addJob(job), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job) {

        if(jobService.updateJobWithId(id, job)) {

            return new ResponseEntity<>("Job uptdated succesfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Job is not found",HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        if (jobService.deleteJob(id)){
            return new ResponseEntity<>("Job deleted succesfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Job is not found",HttpStatus.NOT_FOUND);
    }

}
