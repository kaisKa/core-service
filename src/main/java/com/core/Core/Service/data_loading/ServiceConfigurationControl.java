package com.core.Core.Service.data_loading;


import com.core.Core.Service.common.dto.ApiResponse;
import com.core.Core.Service.data_loading.document.ServiceConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController()
@RequestMapping("/api/service-config")
public class ServiceConfigurationControl {

    private final ServiceConfigurationService service;

    public ServiceConfigurationControl(ServiceConfigurationService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        List<ServiceConfiguration> configs = service.getAll();

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(configs).build();
        System.out.println(configs);
        return new ResponseEntity<>(resp,
                resp.getStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable BigInteger id ){

        ServiceConfiguration configs = service.getById(id);

        ApiResponse resp = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Successful")
                .data(configs).build();

        return new ResponseEntity<>(resp,
                resp.getStatus());
    }
}
