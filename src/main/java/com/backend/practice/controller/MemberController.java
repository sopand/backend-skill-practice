package com.backend.practice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.practice.request.member.ReqSignUp;
import com.backend.practice.response.ResResult;
import com.backend.practice.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@Tag(name = "회원 관련 API", description = "회원 정보 Controller")
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberSRV;

	@PostMapping("/signup")
	@Operation(summary = "유저 회원가입 API")
	@ApiResponse(responseCode = "200", description = "유저 회원가입 성공")
	@ApiResponse(responseCode = "400", description = "유저 회원가입 실패")
	public ResponseEntity<ResResult> signUp(@Valid @RequestBody ReqSignUp reqData) {

		ResResult result = memberSRV.signUp(reqData);

		return ResponseEntity.status(result.getStatusCode()).body(result);
	}
	
	
	
}
