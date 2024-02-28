package com.backend.practice.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReqSignUp {
	
	@Schema(description = "회원가입에 사용할 이메일정보")
	@NotEmpty(message ="이메일을 입력해주세요" )
	@Email(message = "이메일 양식이 올바르지 않습니다")
	private String email;
	
	@Schema(description = "사용할 패스워드")
	@NotEmpty(message = "유저 패스워드를 입력해주세요")
	private String password;
	
	@Schema(description = "회원가입할 유저의 성명 OR 별명")
	@NotEmpty(message = "유저의 성명 또는 별명을 입력해주세요")
	@Size(min = 2,max = 12 ,message = "최소 2  ~ 12자 까지만 입력가능합니다")
	private String name;
	

}
