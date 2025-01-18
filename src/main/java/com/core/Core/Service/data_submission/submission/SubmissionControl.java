package com.core.Core.Service.data_submission.submission;

import com.core.Core.Service.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
public class SubmissionControl {

    private final SubmissionService service;

    public SubmissionControl(SubmissionService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(description = "Endpoint to submit a form")
    public ResponseEntity<ApiResponse> submit(@Valid @RequestBody SubmissionDto submissionDto){

        Submission sub = service.submit(submissionDto);

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(sub).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());

    }

    @GetMapping
    @Operation(description = "Endpoint to get all submissions")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        Page<Submission> submissions = service.getAll(PageRequest.of(pageNum, pageSize));

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(submissions).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }

    @GetMapping("/{serviceId}")
    @Operation(description = "Endpoint to get all submissions for a specific service")
    public ResponseEntity<ApiResponse> getByService(
            @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam @Parameter(required = false, description = "add the field name to sort by") String sortField,
            @PathVariable BigInteger serviceId){

        List<Submission> sub = service
                .getByServiceId(serviceId,PageRequest.of(pageNum, pageSize)
                        .withSort(Sort.by(Sort.Order.by(sortField))));

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(sub).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }

    @GetMapping("/customer/{customerId}")
    @Operation(description = "Endpoint to get all submissions done by a customer")
    public ResponseEntity<ApiResponse> getByCustomer(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                     @PathVariable Long customerId){
        List<Submission> sub  = service.getByCustomerId(customerId,PageRequest.of(pageNum, pageSize));

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(sub).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }
}
