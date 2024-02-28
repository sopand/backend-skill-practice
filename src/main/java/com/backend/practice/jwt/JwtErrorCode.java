package com.backend.practice.jwt;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
	
	TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,101,"토큰 시간이 만료되었습니다 재로그인해주세요"),
	ACCESS_DENIED(HttpStatus.UNAUTHORIZED,105,"알수 없는 이유로 요청이 거절되었습니다."),
	;
	
	private HttpStatus status;
	
	private Integer code;
	
	private String message;
}
