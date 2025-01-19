package com.core.Core.Service.data_submission.costomer;

import com.core.Core.Service.config.JwtService;
import com.core.Core.Service.data_submission.auth.AuthenticationRequest;
import com.core.Core.Service.data_submission.auth.AuthenticationResponse;
import com.core.Core.Service.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public List<Customer> getAll() {
        return this.repository.findAll();
    }

    public Page<Customer> getAllPaged(Pageable page) {
        return this.repository.findAll(page);
    }

    public Customer getById(Long id) {
        var op = this.repository.findById(id);
        if (op.isEmpty())
            throw new NotFoundException("Customer " + id + " Not found");
        else return op.get();
    }

    public Customer getByEmail(String email) {
        var op = this.repository.findByEmail(email);
        if (op.isEmpty())
            throw new NotFoundException("Customer " + email + " Not found");
        else return op.get();
    }


    public Customer update(Customer cust, Long id) {
        var opEmp = this.repository.findById(id);
        if (opEmp.isEmpty())
            throw new NotFoundException("Customer " + id + " not fount ");
        // this could be done in a better way, like this null values could be set but for the sake of simplicity
        Customer employee = opEmp.get();
        employee.setFirstName(cust.getFirstName());
        employee.setLastName(cust.getLastName());
        employee.setEmail(cust.getEmail());
        Customer u = this.repository.save(employee);
        return objectMapper.convertValue(u, Customer.class);
    }

    public Boolean delete(Long id) {
        var opEmp = this.repository.findById(id);
        if (opEmp.isPresent()) {
            this.repository.delete(opEmp.get());
            return true;
        }
        return false;

    }


//    public Customer create(Customer cust) {
//
//        cust.setPassword(passwordEncoder.encode(cust.getPassword()));
//        cust.setRole(Role.USER);
//        return this.repository.save(cust);
//    }
//
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
//
//       var user = getByEmail(request.getEmail());
//       var jwtToken = jwtService.generateToken(user);
//       return AuthenticationResponse.builder()
//               .Token(jwtToken)
//               .build();
//    }
}
