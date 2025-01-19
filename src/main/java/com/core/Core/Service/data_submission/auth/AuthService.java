package com.core.Core.Service.data_submission.auth;

import com.core.Core.Service.config.JwtService;
import com.core.Core.Service.data_submission.costomer.Customer;
import com.core.Core.Service.data_submission.costomer.CustomerDto;
import com.core.Core.Service.data_submission.costomer.CustomerRepository;
import com.core.Core.Service.data_submission.costomer.Role;
import com.core.Core.Service.exceptions.NotFoundException;
import com.core.Core.Service.exceptions.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.http.HttpTimeoutException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerRepository repository;

    @SneakyThrows
    public Customer create(CustomerDto cust) {

        if (repository.findByEmail(cust.getEmail()).isPresent())
            throw new UserAlreadyExistsException("Customer Already exist");
        var customer = Customer.builder()
                .email(cust.getEmail())
                .firstName(cust.getFirstName())
                .lastName(cust.getLastName())
                .password(passwordEncoder.encode(cust.getPassword()))
                .role(Role.USER).build();
        return this.repository.save(customer);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var oUser = repository.findByEmail(request.getEmail());
        if (oUser.isEmpty())
            throw new NotFoundException("Customer " + request.getEmail() + " Not found");
        var jwtToken = jwtService.generateToken(oUser.get());
        return AuthenticationResponse.builder()
                .Token(jwtToken)
                .build();
    }


    public ValidationResponse validate(String authorizationHeader) {
        String token = authorizationHeader.substring(7); // Remove "Bearer "
        if (jwtService.isTokenValid(token, jwtService.extractUsername(token))) {
            return ValidationResponse.builder().isValid(true).message("Token is valid").build();
        }
        return ValidationResponse.builder().isValid(true).message("Invalid token").build();
    }
}
