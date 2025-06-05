package com.jobapp.review.review;

import com.jobapp.review.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByCompanyId(Long companyId);

    Review findByIdAndCompanyId(Long id, Long companyId);
}
