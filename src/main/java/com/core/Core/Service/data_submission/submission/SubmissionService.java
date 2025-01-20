package com.core.Core.Service.data_submission.submission;

import com.core.Core.Service.data_submission.costomer.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository repository;
    private final CustomerService service;
    private final KafkaTemplate kafkaTemplate;

    private final ObjectMapper objectMapper;
    private String TOPIC = "submissions";



    /**
     * 1. load the service config <br>
     * 2. validate a gains the validation in that config will happen in a custom validator <br>
     * 3. publish an event to kafka topic <br>
     *
     * @param submissionDto
     * @return
     */
    public SubmissionDto submit(SubmissionDto submissionDto) {

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
        return convertToDto(savedSub);

    }

    public Page<SubmissionDto> getAll(Pageable page) {


        return repository.findAll(page).map(this::convertToDto);
    }

    public List<Submission> getByServiceId(BigInteger serviceId,Pageable page) {

        return repository.findByServiceId(serviceId,page);
    }

    public List<Submission> getByCustomerId(Long customerId,Pageable page) {

        return repository.findByCustomerId(customerId,page);
    }

    private SubmissionDto convertToDto(Submission submission) {
        return objectMapper.convertValue(submission, SubmissionDto.class);
    }


}




