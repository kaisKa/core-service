package com.core.Core.Service.data_submission.submission;

import com.core.Core.Service.data_submission.submission.validation.ValidSubmission;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigInteger;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@ValidSubmission
public class SubmissionDto {
    @NotNull(message = "Service ID is required")
    private BigInteger serviceId;
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> submittedData;
}
