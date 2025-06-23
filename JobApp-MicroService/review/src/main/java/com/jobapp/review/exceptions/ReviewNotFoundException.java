package com.jobapp.review.exceptions;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long reviewId,Long companyId) {
        super("Review not found with companyId: "+companyId+" reviewId: "+reviewId);
    }
}
