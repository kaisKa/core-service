package com.core.Core.Service.data_submission.auth;

import com.core.Core.Service.common.dto.ApiResponse;
import com.core.Core.Service.data_submission.costomer.Customer;
import com.core.Core.Service.data_submission.costomer.CustomerDto;
import com.core.Core.Service.data_submission.costomer.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutheticationController {

    private final AuthService service;

    @PostMapping("/register")
    @Operation(description = "Endpoint to create a new custloyee")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CustomerDto cust) {

        Customer user = service.create(cust);
        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(user).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }


    @PostMapping("/authenticate")
    @Operation(description = "Endpoint to create a new custloyee")
    public ResponseEntity<ApiResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {

        AuthenticationResponse user = service.authenticate(request);
        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(user).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }

    /**
     * validate token method used by other service to validate the passed token
     * @param authorizationHeader
     * @return
     */
    @Hidden
    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {

        ValidationResponse resp = service.validate(authorizationHeader);
        return new ResponseEntity<>(resp.getMessage(),
                resp.isValid() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);

    }
}
