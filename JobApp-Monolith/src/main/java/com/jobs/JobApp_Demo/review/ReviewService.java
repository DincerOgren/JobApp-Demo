package com.jobs.JobApp_Demo.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllCompanyReviews(Long companyId);

    String addReviewToCompany(Long companyId, Review review);

    List<Review> getAllReviews();

    Review getReview(Long companyId, Long reviewId);

    String updateReview(Long companyId, Long reviewId, Review review);

    String deleteReview(Long companyId, Long reviewId);
}
