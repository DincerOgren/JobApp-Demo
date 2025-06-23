package com.jobapp.review.review;

import com.jobapp.review.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByCompanyId(Long companyId);

    Review findByIdAndCompanyId(Long id, Long companyId);


    Page<Review> findAllByReviewTitleContainingIgnoreCase(String keyword, Pageable pageDetails);

    Page<Review> findByCompanyId(Long companyId, Pageable pageable);

    Page<Review> findByCompanyIdAndReviewTitleContainingIgnoreCase(Long companyId, String keyword, Pageable pageable);
}
