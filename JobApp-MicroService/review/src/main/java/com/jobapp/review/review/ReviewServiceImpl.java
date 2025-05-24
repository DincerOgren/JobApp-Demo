package com.jobapp.review.review;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private CompanyRepository companyRepository;

    @Override
    public List<Review> getAllCompanyReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews;
    }

    @Override
    public String addReviewToCompany(Long companyId, Review review) {
//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new EntityNotFoundException("Company not found with id:"+companyId));

        review.setCompanyId(companyId);
        reviewRepository.save(review);


        return "Review successfully added to company-"+companyId;
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        return reviewRepository.findByIdAndCompanyId(reviewId, companyId);


    }

    @Override
    public String updateReview(Long companyId, Long reviewId, Review review)
    {
        Review reviewToUpdate = reviewRepository.findByIdAndCompanyId(reviewId, companyId);

        reviewToUpdate.setReview(review.getReview());
        reviewToUpdate.setRating(review.getRating());
        reviewToUpdate.setDescription(review.getDescription());
        reviewRepository.save(reviewToUpdate);
        return "Review successfully updated with id:"+reviewId;
    }

    @Override
    public String deleteReview(Long companyId, Long reviewId) {
        Review reviewToDelete = reviewRepository.findByIdAndCompanyId(reviewId, companyId);

        reviewRepository.delete(reviewToDelete);
        return "Review successfully deleted with id:"+reviewId;
    }
}
