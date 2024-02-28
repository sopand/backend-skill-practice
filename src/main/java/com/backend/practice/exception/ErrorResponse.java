package com.backend.practice.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ErrorResponse {
	
	private String message;
	private int status;
	private List<FieldError> errors;
	
	
	private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus().value();
    }

    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage() +"\n";
        for(FieldError error:errors) {
        	this.message += error.getMessage()+"\n";
        }
        this.status = code.getStatus().value();
        this.errors = errors;
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }


    public static ErrorResponse of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }
	
    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getStatus().value())
                .body(ErrorResponse.builder()
                		.status(e.getStatus().value())
                        .message(e.getMessage())
                        .build());
    }
    
    // Valid 에러감지용
	@Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String message;

        private FieldError(final String field, final String value,final String message) {
            this.field = field;
            this.value = value;
            this.message=message;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    		error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
	

}
