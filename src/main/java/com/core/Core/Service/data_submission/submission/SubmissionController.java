package com.core.Core.Service.data_submission.submission;

import com.core.Core.Service.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController()
@RequestMapping("/api/submission")
public class SubmissionController {

    private final SubmissionService service;

    public SubmissionController(SubmissionService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(description = "Endpoint to submit a form")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ApiResponse> submit(@Valid @RequestBody SubmissionDto submissionDto) {

        SubmissionDto sub = service.submit(submissionDto);

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(sub).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());

    }

    @GetMapping
    @Operation(description = "Endpoint to get all submissions")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) @Parameter(required = false, description = "add the field name to sort by") String sortField) {
        var page = PageRequest.of(pageNum, pageSize);
        if (sortField != null)
            page.withSort(Sort.by(Sort.Order.by(sortField)));

        Page<SubmissionDto> submissions = service.getAll(page);

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(submissions).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }

    @GetMapping("/{serviceId}")
    @Operation(description = "Endpoint to get all submissions for a specific service")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ApiResponse> getByService(
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(required = false) @Parameter(required = false, description = "add the field name to sort by") String sortField,
            @PathVariable BigInteger serviceId) {

        var page = PageRequest.of(pageNum, pageSize);
        if (sortField != null)
            page.withSort(Sort.by(Sort.Order.by(sortField)));

        List<SubmissionDto> sub = service
                .getByServiceId(serviceId, page);

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(sub).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(description = "Endpoint to get all submissions done by a customer")
    @SecurityRequirement(name = "BearerAuth")
    public ResponseEntity<ApiResponse> getByCustomer(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                     @PathVariable Long customerId) {
        List<SubmissionDto> sub = service.getByCustomerId(customerId, PageRequest.of(pageNum, pageSize));

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(sub).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }
}
