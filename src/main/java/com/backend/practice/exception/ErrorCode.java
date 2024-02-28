package com.backend.practice.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,"입력 정보 유효성 인증 실패"),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND,"유저 정보를 찾을 수 없습니다."),
	DUPLICATE_EMAIL(HttpStatus.CONFLICT,"해당 이메일은 이미 등록되어 있습니다."),
	
	
	DATA_INSERT_FAILED(HttpStatus.BAD_REQUEST,"알수 없는 이유로 데이터 저장에 실패하였습니다 재시도 해주세요."),
	DATA_MODIFY_FAILED(HttpStatus.BAD_REQUEST,"알수 없는 이유로 데이터의 수정 / 삭제에 실패하였습니다 재시도 해주세요"),
	
	
	SCHEDULE_TIME_DUPLICATION(HttpStatus.CONFLICT,"기존 일정과 시간이 중복됩니다"),
	SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 일정에 대한 정보를 찾을 수 없습니다.");
	
	

	private final HttpStatus status;
	private final String message;

}
