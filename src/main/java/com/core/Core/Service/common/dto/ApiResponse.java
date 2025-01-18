package com.core.Core.Service.common.dto;


import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse {

    private HttpStatus status;
    private String message;
    private Object data;
}
