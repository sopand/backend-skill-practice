package com.backend.practice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.practice.entity.Member;
import com.backend.practice.enums.FlagYN;
import com.backend.practice.enums.Role;
import com.backend.practice.exception.CustomException;
import com.backend.practice.exception.ErrorCode;
import com.backend.practice.repository.MemberRepository;
import com.backend.practice.request.member.ReqSignUp;
import com.backend.practice.response.ResResult;
import com.backend.practice.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final PasswordEncoder passwordEncoder;

	private final MemberRepository memberRepository;

	public ResResult signUp(ReqSignUp reqData) {

		// 이미 존재하는 닉네임일 경우 에러발생
		if (memberRepository.existsByEmail(reqData.getEmail()))
			throw new CustomException(ErrorCode.DUPLICATE_EMAIL);

		Member saveMember = Member.builder().email(reqData.getEmail())
				.password(passwordEncoder.encode(reqData.getPassword())).name(reqData.getName()).role(Role.USER)
				.delYn(FlagYN.N).build();
		memberRepository.save(saveMember);

		// SAVE 성공여부 체크를 위한 static 메서드
		return CommonUtils.isSaveSuccessful(saveMember.getMemberSid(), "회원가입이 완료 되었습니다");

	}

}
