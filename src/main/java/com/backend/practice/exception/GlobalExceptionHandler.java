package com.backend.practice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)   // MethodArgument~~에러가 발생하면 해당 핸들러로 넘어오게됨
	public ResponseEntity<ErrorResponse> ValidErrorException(MethodArgumentNotValidException e){
		 final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());// ErrorResponse에 정의해놓은 메서드에 의해 데이터가 반환
	        return new ResponseEntity<>(response, ErrorCode.INVALID_INPUT_VALUE.getStatus()); 
	}
	
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> CustomException(CustomException e){
			return ErrorResponse.toResponseEntity(e.getErrorCode());
	}
	
}
