package com.core.Core.Service.kafka;

import com.core.Core.Service.data_submission.submission.Submission;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class SubmissionSerializer implements Serializer<Submission> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public SubmissionSerializer() {
        // Register the JavaTimeModule to handle Java 8 date/time types
        objectMapper.registerModule(new JavaTimeModule());
    }
    @Override
    public byte[] serialize(String topic, Submission data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing Submission object", e);
        }
    }
}
