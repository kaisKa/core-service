package com.core.Core.Service.data_submission.audit;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "audit")
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipAddress;
    private String method;
    private String uri;
    private Long duration;
    private Integer status;
    private LocalDateTime timestamp;
}
