package com.jobapp.review.review;

import com.jobapp.review.clients.httpinterface.CompanyServiceClient;
import com.jobapp.review.clients.restclient.CompanyRestClient;
import com.jobapp.review.models.Company;
import com.jobapp.review.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CompanyServiceClient companyInterface;

//    @Autowired
//    private CompanyRestClient restClient;
    //
//    @Autowired
//    private CompanyRepository companyRepository;

    @Override
    public List<Review> getAllCompanyReviews(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);

        return reviews;
    }

    @Override
    public Boolean addReviewToCompany(Long companyId, Review review) {

        try {
           Company company = companyInterface.getCompanyDetails(companyId);
            if (company == null)
                return false;

            review.setCompanyId(company.getId());
            reviewRepository.save(review);


            return true;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found", e);
        }


//        Company company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new EntityNotFoundException("Company not found with id:"+companyId));


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
