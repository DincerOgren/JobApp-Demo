package com.jobapp.company.company;

import com.jobapp.company.config.AppConstants;
import com.jobapp.company.exceptions.ErrorResponse;
import com.jobapp.company.models.CompanyRequestDTO;
import com.jobapp.company.models.CompanyResponse;
import com.jobapp.company.models.CompanyResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/companies")
@RequiredArgsConstructor
@Tag(name = "Company API", description = "API for company operations")
public class CompanyController {



    private final CompanyService companyService;

    // GET ALL COMPANIES
    @Operation(summary = "Get all companies", description = "Returns a paginated list of companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of companies returned"),
            @ApiResponse(responseCode = "204", description = "No companies found")
    })
    @GetMapping
    public ResponseEntity<?> getAllCompanies(
            @RequestParam(name = "keyword",required = false) String keyword,
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_PRODUCTS_BY,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder
    ) {
//        List<CompanyResponseDTO> companiesResponse = companyService.getAllCompanies(pageNumber,pageSize,sortBy,sortOrder,keyword);
        CompanyResponse companyResponse = companyService.getAllCompanies(pageNumber,pageSize,sortBy,sortOrder,keyword);
        return ResponseEntity.ok(companyResponse);
    }


    // GET COMPANY BY ID
    @Operation(summary = "Get a company by ID", description = "Returns a single company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found",
                    content = @Content(schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompany(@PathVariable Long id) {
        return companyService.getCompanyWithId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // ADD COMPANY
    @Operation(summary = "Add a company", description = "Creates and returns a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created",
                    content = @Content(schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CompanyResponseDTO> addCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO response = companyService.addCompany(companyRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // UPDATE COMPANY
    @Operation(summary = "Update a company", description = "Updates and returns a company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated",
                    content = @Content(schema = @Schema(implementation = CompanyResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        CompanyResponseDTO response = companyService.updateCompanyWithId(id, companyRequestDTO);
        return ResponseEntity.ok(response);
    }

    // DELETE COMPANY
    @Operation(summary = "Delete a company", description = "Deletes a company by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted"),
            @ApiResponse(responseCode = "404", description = "Company not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

//    @GetMapping("/company-exist/{id}")
//    public ResponseEntity<Company> companyExist(@PathVariable Long id) {
//
//        return companyService.companyExist(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(()-> ResponseEntity.notFound().build());
//    }
}
