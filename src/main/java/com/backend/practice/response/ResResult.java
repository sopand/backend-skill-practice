package com.backend.practice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResResult {
	
	@Schema(description = "해당 API 요청 성공여부 true : 성공 , false : 실패")
	private Boolean success;
	
	@Schema(description = "API 응답 메시지")
	private String message;
	
	@Schema(description = "API 응답 코드")
	private int statusCode;
	
	
	
	

}
