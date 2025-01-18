package com.core.Core.Service.data_submission.costomer;

import com.core.Core.Service.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping()
    @Operation(description = "Endpoint to get all customers ")
    public ResponseEntity<ApiResponse> listAll(@RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                               @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Pageable page = PageRequest.of(pageNum, pageSize);
        var custs = this.service.getAllPaged(page);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(custs.toList())
                .status(HttpStatus.OK)
                .message("Successfully retrieved custloyees record").build();

        return new ResponseEntity<>(apiResponse,
                apiResponse.getStatus());
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint to get a custloyee by it id")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<Customer> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.service.getById(id));
    }

    @PostMapping()
    @Operation(description = "Endpoint to create a new custloyee")
    public ResponseEntity<Customer> create(@Valid @RequestBody  Customer cust) {
        return ResponseEntity.created(URI.create("/api/customer")).body(this.service.create(cust));
    }


    @PutMapping()
    @Operation(description = "Endpoint to update existing custloyee and create one in case does not exist")
    public ResponseEntity<Customer> update(@RequestBody Customer cust, @RequestParam Long id) {
        return ResponseEntity.ok().body(this.service.update(cust, id));
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint to delete a customer from the db")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") Long id) {
        return this.service.delete(id) ? new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.OK).message("custloyee deleted - custloyee ID:" + id).build(), HttpStatus.OK)
                : new ResponseEntity<>(ApiResponse.builder().status(HttpStatus.NOT_FOUND).message("custloyee " + id + " not found").build(), HttpStatus.NOT_FOUND);
    }


}
