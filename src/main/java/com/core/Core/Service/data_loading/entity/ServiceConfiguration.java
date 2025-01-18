package com.core.Core.Service.data_loading.entity;



import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
@Document(collection = "ServiceConfiguration")
public class ServiceConfiguration {

    @Id
    private BigInteger id;
    private String name;
    private List<RequiredField> required_fields;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RequiredField {

        private Map<String, String> label; // Maps language code to label text (e.g., "en" -> "Amount")
        private String name;
        private Map<String, String> placeholder; // Maps language code to placeholder text (e.g., "en" -> "Enter amount")
        private String type;
        private String validation;
        private int max_length;
        private String default_value;
        private Map<String, String> validation_error_message; // Maps language code to validation error message
         }

    }
