package com.core.Core.Service.data_submission.submission;

import com.core.Core.Service.data_submission.costomer.CustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@Log4j2
public class SubmissionService {

    private final SubmissionRepository repository;

    private final CustomerService service;

    private final KafkaTemplate kafkaTemplate;

    private String TOPIC = "submissions";

    public SubmissionService(SubmissionRepository repository, CustomerService service, KafkaTemplate kafkaTemplate) {
        this.repository = repository;
        this.service = service;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * 1. load the service config <br>
     * 2. validate a gains the validation in that config will happen in a custom validator <br>
     * 3. publish an event to kafka topic <br>
     *
     * @param submissionDto
     * @return
     */
    public Submission submit(SubmissionDto submissionDto) {

        //TODO: customer id should be getting from the jwt
        var cust = service.getById(submissionDto.getCustomerId());
        Submission submission = Submission.builder().customer(cust)
                .customerId(submissionDto.getCustomerId())
                .serviceId(submissionDto.getServiceId())
                .submittedData(submissionDto.getSubmittedData())
                .submissionTimestamp(LocalDateTime.now())
                .build();
        var savedSub = repository.save(submission);

        if (savedSub.getServiceId() != null) {

            kafkaTemplate.send(TOPIC,savedSub.getServiceId().toString(),savedSub).whenComplete((d,a) -> log.info("Event published {}",savedSub));

        }
        return savedSub;

    }

    public Page<Submission> getAll(Pageable page) {
        return repository.findAll(page);
    }

    public List<Submission> getByServiceId(BigInteger serviceId,Pageable page) {

        return repository.findByServiceId(serviceId,page);
    }

    public List<Submission> getByCustomerId(Long customerId,Pageable page) {

        return repository.findByCustomerId(customerId,page);
    }



}




