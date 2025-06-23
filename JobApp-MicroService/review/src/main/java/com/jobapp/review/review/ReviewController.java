package com.jobapp.review.review;

import com.jobapp.review.config.ServiceConstants;
import com.jobapp.review.exceptions.ErrorResponse;
import com.jobapp.review.models.ReviewRequestDTO;
import com.jobapp.review.models.ReviewResponse;
import com.jobapp.review.models.ReviewResponseDTO;
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
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;



    @GetMapping("/reviews")
    public ResponseEntity<ReviewResponse> getAllReviews(
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "pageNumber",defaultValue = ServiceConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = ServiceConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = ServiceConstants.SORT_REVIEWS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = ServiceConstants.SORT_DIR,required = false) String sortOrder

    ) {
        ReviewResponse response = reviewService.getAllReviews(pageNumber,pageSize,sortBy,sortOrder,
                                                                            keyword);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // GET ALL REVIEWS BY COMPANY
    @Operation(summary = "Get all reviews related to given company", description = "Returns a paginated list of all reviews to given company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Paginated list of reviews returned"),
            @ApiResponse(responseCode = "204", description = "No reviews found for given company")
    })
    @GetMapping("/{companyId}/reviews")
    public ResponseEntity<ReviewResponse> getAllReviewOfCompany(
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "pageNumber",defaultValue = ServiceConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = ServiceConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = ServiceConstants.SORT_REVIEWS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = ServiceConstants.SORT_DIR,required = false) String sortOrder,
            @PathVariable Long companyId){

        ReviewResponse response = reviewService.getAllCompanyReviews(pageNumber,pageSize,sortBy,
                                                                sortOrder,keyword,companyId);
       return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // GET COMPANY REVIEW BY ID
    @Operation(summary = "Get a company review by ID", description = "Returns a single review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review found",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReview(@PathVariable Long companyId, @PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview(companyId, reviewId), HttpStatus.OK);
    }


    // ADD REVIEW TO A COMPANY
    @Operation(summary = "Add a review to company", description = "Creates and returns a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{companyId}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long companyId ,@RequestBody ReviewRequestDTO reviewRequestDTO){
        ReviewResponseDTO addedReview = reviewService.addReviewToCompany(companyId,reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
    }

    // UPDATE REVIEW FROM COMPANY
    @Operation(summary = "Update a review", description = "Updates and returns a review related to given company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review updated",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long companyId ,
                                                          @PathVariable Long reviewId,
                                                          @Valid @RequestBody ReviewRequestDTO reviewRequestDTO){
        ReviewResponseDTO updatedReview = reviewService.updateReview(companyId,reviewId,reviewRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReview);
    }

    // DELETE COMPANY
    @Operation(summary = "Delete a review", description = "Deletes a review from a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Review deleted"),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long companyId ,@PathVariable Long reviewId){

        if (reviewService.deleteReview(companyId,reviewId))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
