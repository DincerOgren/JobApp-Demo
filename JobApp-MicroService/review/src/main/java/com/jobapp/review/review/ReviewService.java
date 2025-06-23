package com.jobapp.review.review;

import com.jobapp.review.models.ReviewRequestDTO;
import com.jobapp.review.models.ReviewResponse;
import com.jobapp.review.models.ReviewResponseDTO;

public interface ReviewService {
    ReviewResponse getAllReviews(Integer pageNumber, Integer pageSize, String sortBy,
                                 String sortOrder, String keyword);

    ReviewResponse getAllCompanyReviews(Integer pageNumber, Integer pageSize, String sortBy,
                                        String sortOrder, String keyword, Long companyId);

    ReviewResponseDTO addReviewToCompany(Long companyId, ReviewRequestDTO reviewRequestDTO);


    ReviewResponseDTO getReview(Long companyId, Long reviewId);

    ReviewResponseDTO updateReview(Long companyId, Long reviewId, ReviewRequestDTO reviewRequestDTO);

    boolean deleteReview(Long companyId, Long reviewId);
}
