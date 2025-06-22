package com.jobapp.job.job;

import com.jobapp.job.config.AppConstants;
import com.jobapp.job.exceptions.ErrorResponse;
import com.jobapp.job.exceptions.ServiceUnavailableException;
import com.jobapp.job.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {


    private final JobService jobService;



    // GET ALL JOBS
    @Operation(summary = "Get all jobs", description = "Returns a list of all jobs with related companyId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of jobs returned"),
            @ApiResponse(responseCode = "204", description = "No jobs found")
    })
    @GetMapping
    public ResponseEntity<?> getAllJobs(
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
    ) {
        JobResponse jobResponse = jobService.getAllJobs(pageNumber,pageSize,sortBy,sortOrder,keyword);
        return ResponseEntity.ok(jobResponse);
    }


    // GET JOB BY ID
    @Operation(summary = "Get a job by ID", description = "Returns a single job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job found",
                    content = @Content(schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Job not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobWithId(@PathVariable String id) {
        JobResponseDTO responseDTO = jobService.getJobWithId(id);
        return ResponseEntity.ok(responseDTO);
    }

    // ADD JOB TO A COMPANY
    @Operation(summary = "Add job to a company", description = "Creates and returns a job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created",
                    content = @Content(schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Company service unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<JobResponseDTO>  addJob(@Valid @RequestBody JobRequestDTO jobDTO) {
        JobResponseDTO jobResponseDTO = jobService.addJob(jobDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponseDTO);
    }

    // UPDATE JOB
    @Operation(summary = "Update a job", description = "Updates and returns a job")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job updated",
                    content = @Content(schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Job not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable String id,
                                                    @Valid @RequestBody JobRequestDTO jobDTO) {

       JobResponseDTO responseDTO = jobService.updateJobWithId(id, jobDTO);

       return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // DELETE JOB
    @Operation(summary = "Delete a job", description = "Deletes a job with ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted"),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        if (jobService.deleteJob(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
