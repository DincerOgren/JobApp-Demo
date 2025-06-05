package com.jobapp.review.review;

import com.jobapp.review.models.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllCompanyReviews(Long companyId);

    Boolean addReviewToCompany(Long companyId, Review review);

    List<Review> getAllReviews();

    Review getReview(Long companyId, Long reviewId);

    String updateReview(Long companyId, Long reviewId, Review review);

    String deleteReview(Long companyId, Long reviewId);
}
