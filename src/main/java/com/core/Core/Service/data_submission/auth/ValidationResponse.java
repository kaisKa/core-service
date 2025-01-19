package com.core.Core.Service.data_submission.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidationResponse {

    private String message;
    private boolean isValid;
}
