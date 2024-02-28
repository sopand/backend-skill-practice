package com.backend.practice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.practice.entity.Member;
import com.backend.practice.jwt.CustomUserDetails;
import com.backend.practice.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	
	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		// Member정보가 없다면 Error 발생
		Member memberData = memberRepository.findByEmail(email)
		        .orElseThrow(() ->  new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		
		
		// Member 정보가 있다면 해당 member 정보로 로그인한 사용자의 권한 , 아이디 , 패스워드등을 매칭
		return new CustomUserDetails(memberData);
	}

}
