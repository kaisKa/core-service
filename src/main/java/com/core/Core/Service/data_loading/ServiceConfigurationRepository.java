package com.core.Core.Service.data_loading;

import com.core.Core.Service.data_loading.entity.ServiceConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ServiceConfigurationRepository extends MongoRepository<ServiceConfiguration, BigInteger> {


}
