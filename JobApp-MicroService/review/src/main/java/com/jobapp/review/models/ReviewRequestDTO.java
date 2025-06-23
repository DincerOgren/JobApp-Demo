package com.jobapp.review.models;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDTO {
    @NotBlank
    @Size(max = 50, message = "Review title must be at most 50 characters")
    String reviewTitle;

    @NotBlank
    @Size(max = 300,message = "Description must be under 300 characters")
    String description;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Rating must be at least 0.0")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating must be at most 5.0")
    Double rating;

    @NotNull(message = "Company ID is required")
    @Positive(message = "Company ID must be a positive number")
    Long companyId;

}
