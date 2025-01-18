package com.core.Core.Service.data_loading;

import com.core.Core.Service.data_loading.entity.ServiceConfiguration;
import com.core.Core.Service.exceptions.NotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ServiceConfigurationService {

    private final ServiceConfigurationRepository repository;


    public ServiceConfigurationService(ServiceConfigurationRepository repository) {
        this.repository = repository;
    }

    public List<ServiceConfiguration> getAll() {
        return repository.findAll();
    }

    public ServiceConfiguration getById(BigInteger id) {
        var conf = repository.findById(id);
        return conf.orElseThrow(() ->  new NotFoundException("Config does not exist"));
    }
}
