package com.core.Core.Service.data_submission.submission.validation;

import jakarta.validation.ConstraintValidatorContext;
import com.core.Core.Service.data_loading.ServiceConfigurationService;
import com.core.Core.Service.data_loading.document.ServiceConfiguration;
import com.core.Core.Service.data_submission.submission.SubmissionDto;
import jakarta.validation.ConstraintValidator;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

public class SubmittedDataValidator implements ConstraintValidator<ValidSubmission, SubmissionDto> {

    private final ServiceConfigurationService service;

    public SubmittedDataValidator(ServiceConfigurationService service) {
        this.service = service;
    }


    @Override
    public boolean isValid(SubmissionDto submissionDto, ConstraintValidatorContext context) {
        //get service config --> required fields

        var ser = service.getById(submissionDto.getServiceId());

        var requiredFields = ser.getRequired_fields();

        Map<String, String> submittedData = submissionDto.getSubmittedData();

        boolean isValid = true;

        for(ServiceConfiguration.RequiredField field: requiredFields ){
            String value = submissionDto.getSubmittedData().get(field.getName()) ;

            // validate existence
            if(!StringUtils.hasText(value)){
                context.buildConstraintViolationWithTemplate(field.getValidation_error_message().get("en"))
                        .addPropertyNode(field.getName())
                        .addConstraintViolation();
                isValid = false;
            }

            //type validation
            if(!validateType(field.getType(), value)){
                context.buildConstraintViolationWithTemplate(String.format("Invalid Type %s. Expected %s",field.getName(),field.getType()))
                        .addPropertyNode(field.getName())
                        .addConstraintViolation();
                isValid = false;
            }

            var t = field.getValidation();
            // validate Regex
            if(!Pattern.matches(field.getValidation(),value)){
                context.buildConstraintViolationWithTemplate(field.getValidation_error_message().get("en"))
                        .addPropertyNode(field.getName())
                        .addConstraintViolation();
                isValid = false;
            }

            // validate length
            if (field.getMax_length() > 0 && value.length() > field.getMax_length()) {
                context.buildConstraintViolationWithTemplate(
                                String.format("%s exceeds max length of %d", field.getName(), field.getMax_length())
                        )
                        .addPropertyNode(field.getName())
                        .addConstraintViolation();
                isValid = false;
            }




        }
        return isValid;

    }

    /**
     * Validates the type of the value based on the expected type.
     *
     * @param type  The expected type (e.g., "number", "text", "boolean").
     * @param value The value to validate.
     * @return true if the value matches the expected type, false otherwise.
     */
    private boolean validateType(String type, String value) {
        try {
            switch (type.toLowerCase()) {
                case "number":
                    // Check if it's a valid number
                    Double.parseDouble(value);
                    return true;
                case "text":
                    // Allow all strings for text
                    return true;
                case "boolean":
                    // Check if it's true or false
                    return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
                default:
                    // Unknown type - consider invalid
                    return false;
            }
        } catch (NumberFormatException e) {
            return false; // Invalid number
        }
    }
}



