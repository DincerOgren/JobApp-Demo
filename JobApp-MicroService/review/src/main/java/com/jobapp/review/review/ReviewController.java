package com.jobapp.review.review;

import com.jobapp.review.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class ReviewController {

    @Autowired
    ReviewService reviewService;



    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        if (reviews.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{companyId}/reviews")
    public ResponseEntity<List<Review>> getAllReviewOfCompany(@PathVariable Long companyId){
       List<Review> reviews = reviewService.getAllCompanyReviews(companyId);
       if (reviews.isEmpty())
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       else
           return new ResponseEntity<>(reviews, HttpStatus.OK);

    }

    @GetMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId, @PathVariable Long reviewId){
        return new ResponseEntity<>(reviewService.getReview(companyId, reviewId), HttpStatus.OK);
    }



    @PostMapping("/{companyId}/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long companyId ,@RequestBody Review review){
        if (reviewService.addReviewToCompany(companyId, review))
            return new ResponseEntity<>("Review successfully added to company: "+companyId,HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Cant find related company with id: "+companyId,HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId ,@PathVariable Long reviewId,@RequestBody Review review){

        return new ResponseEntity<>(reviewService.updateReview(companyId,reviewId,review), HttpStatus.CREATED);
    }

    @DeleteMapping("/{companyId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId ,@PathVariable Long reviewId){

        return new ResponseEntity<>(reviewService.deleteReview(companyId,reviewId), HttpStatus.OK);
    }
}
