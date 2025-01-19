package com.core.Core.Service.data_loading;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class Loader implements CommandLineRunner {

    private final ObjectMapper objectMapper;

    private final ServiceConfigurationRepository repository;
    public Loader(ObjectMapper objectMapper, ServiceConfigurationRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {

        String dataPath = "/data/data.json";
        try(InputStream inputStream = TypeReference.class.getResourceAsStream(dataPath)){
            var response = objectMapper.readValue(inputStream, Services.class);
            this.repository.saveAll(response.services);
        }catch (IOException ex){
            ex.printStackTrace();
            throw  new RuntimeException("Failed to read json data");
        }
    }
}
