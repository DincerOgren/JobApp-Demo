package com.jobapp.company.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Standard error response")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @Schema(description = "Timestamp of error", example = "2021-06-22T19:23:11.123")
    private String timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "HTTP error phrase", example = "Not Found")
    private String error;

    @Schema(description = "Error detail message", example = "Company not found with id: 123")
    private String message;

}