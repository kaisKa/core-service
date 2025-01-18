package com.core.Core.Service.data_submission.submission.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SubmittedDataValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSubmission {
    String message() default "Invalid submission data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}