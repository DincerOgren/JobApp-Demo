package com.jobapp.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class FallbackController {
    @GetMapping("/fallback/company")
    public ResponseEntity<List<String>> companyFallBack()
    {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Company service is unavailable, please try again later."));
    }
    @GetMapping("/fallback/jobs")
    public ResponseEntity<List<String>> jobsFallBack()
    {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Job service is unavailable, please try again later."));
    }
    @GetMapping("/fallback/reviews")
    public ResponseEntity<List<String>> reviewFallBack()
    {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Collections.singletonList("Review service is unavailable, please try again later."));
    }
}
