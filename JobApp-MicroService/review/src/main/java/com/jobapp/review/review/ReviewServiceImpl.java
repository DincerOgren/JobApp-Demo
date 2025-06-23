package com.jobapp.review.review;

import com.jobapp.review.clients.httpinterface.CompanyServiceClient;
import com.jobapp.review.exceptions.CompanyNotFoundException;
import com.jobapp.review.exceptions.ReviewNotFoundException;
import com.jobapp.review.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{


    private final ReviewRepository reviewRepository;


    private final CompanyServiceClient companyInterface;

    private final ModelMapper modelMapper;



    @Override
    public ReviewResponse getAllReviews(Integer pageNumber, Integer pageSize, String sortBy,
                                        String sortOrder, String keyword) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Review> reviewPage;
        if (keyword != null && !keyword.isEmpty()) {
            reviewPage = reviewRepository.findAllByReviewTitleContainingIgnoreCase(keyword, pageDetails);
        } else {
            reviewPage = reviewRepository.findAll(pageDetails);
        }

        List<Review> reviews = reviewPage.getContent();
        if (reviews.isEmpty()){
            log.warn("Review list is empty");
            return null;
        }


        List<ReviewResponseDTO> reviewDTOs = reviews.stream().map(review -> modelMapper
                .map(review, ReviewResponseDTO.class)).toList();

        return new ReviewResponse(
                reviewDTOs,
                reviewPage.getNumber(),
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.isLast()

        );
    }

    @Override
    public ReviewResponse getAllCompanyReviews(Integer pageNumber, Integer pageSize, String sortBy,
                                             String sortOrder, String keyword,Long companyId) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Review> reviewPage;
        if (keyword != null && !keyword.isEmpty()) {
            reviewPage = reviewRepository.findByCompanyIdAndReviewTitleContainingIgnoreCase(companyId, keyword, pageDetails);
        } else {
            reviewPage = reviewRepository.findByCompanyId(companyId, pageDetails);
        }

        List<Review> reviews = reviewPage.getContent();
        if (reviews.isEmpty()){
            log.warn("reviews list is empty for companyId: {}", companyId);
            return null;
        }

        List<ReviewResponseDTO> responseDTOS = reviews.stream()
                .map(review -> modelMapper.map(review, ReviewResponseDTO.class))
                .toList();

        return new ReviewResponse(
                responseDTOS,
                reviewPage.getNumber(),
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.isLast()
        );
    }


    @Override
    public ReviewResponseDTO addReviewToCompany(Long companyId, ReviewRequestDTO reviewReqDTO) {

        CompanyResponseDTO companyDTO = companyInterface.getCompanyDetails(companyId);
        if (companyDTO == null) {
            log.warn("company is null when adding review");
            throw new CompanyNotFoundException(companyId);
        }
        reviewReqDTO.setCompanyId(companyDTO.getId());
        Review reviewToSave = modelMapper.map(reviewReqDTO, Review.class);
        Review savedReview = reviewRepository.save(reviewToSave);

        log.info("review added successfully");

        return modelMapper.map(savedReview,ReviewResponseDTO.class);

    }



    @Override
    public ReviewResponseDTO getReview(Long companyId, Long reviewId) {
        Review review =  reviewRepository.findByIdAndCompanyId(reviewId, companyId);
        if (review == null)
            throw new ReviewNotFoundException(reviewId,companyId);
        return modelMapper.map(review,ReviewResponseDTO.class);
    }

    @Override
    public ReviewResponseDTO updateReview(Long companyId, Long reviewId, ReviewRequestDTO reviewReqDTO)
    {
        Review reviewToUpdate = reviewRepository.findByIdAndCompanyId(reviewId, companyId);
        if (reviewToUpdate == null)
            throw new ReviewNotFoundException(reviewId,companyId);

        reviewToUpdate.setReviewTitle(reviewReqDTO.getReviewTitle());
        reviewToUpdate.setRating(reviewReqDTO.getRating());
        reviewToUpdate.setDescription(reviewReqDTO.getDescription());

        Review savedReview = reviewRepository.save(reviewToUpdate);
        log.info("review updated successfully with id: {}", reviewId);

        return modelMapper.map(savedReview,ReviewResponseDTO.class);
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        validateCompanyOrThrow(companyId);

        Review reviewToDelete = reviewRepository.findByIdAndCompanyId(reviewId, companyId);
        if (reviewToDelete == null)
            return false;

        reviewRepository.delete(reviewToDelete);
        log.info("review deleted successfully with id: {}", reviewId);
        return true;

    }

    public void validateCompanyOrThrow(Long companyId) {
        CompanyResponseDTO responseDTO = companyInterface.getCompanyDetails(companyId);
        if (responseDTO == null) {
            throw new CompanyNotFoundException(companyId);
        }
    }
}
