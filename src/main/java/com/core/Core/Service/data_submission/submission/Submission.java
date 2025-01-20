package com.core.Core.Service.data_submission.submission;

import com.core.Core.Service.data_submission.costomer.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@Entity
@Table(name = "submission")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private BigInteger serviceId;
    private Long customerId;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> submittedData;
//    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime submissionTimestamp;


//    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "CUST_ID")
    private Customer customer;


//    public static SubmissionDto toDto(Submission submission) {
//        return SubmissionDto.builder()
//                .serviceId(submission.getServiceId())
//                .customerId(submission.getCustomerId())
//                .submittedData(submission.getSubmittedData())
//                .build();
//    }
}