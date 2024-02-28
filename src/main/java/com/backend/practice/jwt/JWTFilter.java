package com.backend.practice.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.practice.entity.Member;
import com.backend.practice.enums.Role;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter { // 요청에 대해 1번만 작동하는 필터

	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		try {
		// Request에서 Authorization 헤더를 찾음
		String authorization = request.getHeader("Authorization");

		// Authorization 헤더 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) { // null OR 접두사에 이상이 있을경우
			throw new ServletException(new JwtException("토큰을 찾을 수 없습니다."));
		}
		// Bearer 부분 제거 후 순수 토큰만 획득
		String token = authorization.split(" ")[1];
		// 토큰 소멸시간 검증
		
		if (jwtUtil.isExpired(token)) {
			log.error("토큰 인증시간이 만료되었습니다 재로그인해주세요.");
			filterChain.doFilter(request, response);
			return;
		}
		// 토큰에서 username과 role 획득
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);

		// Member Entity생성하여 값 set
		Member memberData = Member.builder().email(username).role(Role.fromString(role)).password("adsadasd").build();

		// UserDetail에 회원정보 객체담기
		CustomUserDetails customUserDetails = new CustomUserDetails(memberData);
		// 스프링 시큐리티 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
				customUserDetails.getAuthorities());
		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
		}catch(Exception e) {
			request.setAttribute("exception",e);
		}
		filterChain.doFilter(request, response);

	}

}
