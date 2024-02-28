package com.backend.practice.jwt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.practice.util.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private static final String CONTENT_TYPE = "application/json"; // JSON 타입의 요청일 경우
	private final JWTUtil jwtUtil;
	@Value("${jwt.expiration}")
	public long tokenExpiration;
	@Value("${jwt.refresh-token.expration}")
	public long refreshTokenExpiration;


	@Getter
	@Setter
	private static class ReqLoginData { // form 로그인 / json 로그인 방식에따라 데이터 받아오는 방식이 달라서 만든객체
		private String email;
		private String password;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		ReqLoginData loginData = null;

		if (request.getContentType().equals(CONTENT_TYPE)) { // 시큐리티는 form아니면 obtain에서 못가져온다..
			ObjectMapper objectMapper = new ObjectMapper();
			try (InputStream inputStream = request.getInputStream()) {
				log.info("사용 데이터 : {}",inputStream.toString());
				loginData = objectMapper.readValue(inputStream, ReqLoginData.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// 클라이언트 요청에서 username, password 추출
			loginData.setEmail(obtainUsername(request));
			loginData.setPassword(obtainPassword(request));
		}

		log.info("로그인 사용자 이메일 : {} 패스워드 : {}", loginData.getEmail(), loginData.getPassword());
		// 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야한다.
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginData.getEmail(),
				loginData.getPassword(), null);

		// token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}

	// 로그인 성공시 실행하는 메소드 ( 여기서 JWT를 발급하면된다 )
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

		String username = customUserDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();

		String role = auth.getAuthority();

		String accessToken = jwtUtil.createJwt(username, role, tokenExpiration);
		String refreshToken = jwtUtil.createRefresh(refreshTokenExpiration); // 리프레시 토큰 생성 메소드를 호출하거나, 다른 방식으로 리프레시 토큰을 생성합니다.

		TokenInfo tokenResponse = TokenInfo.builder().accessToken(accessToken).refreshToken(refreshToken)
				.grantType("Bearer").build();
		// JSON 형태로 변환하여 응답에 쓰기
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(tokenResponse);

		// JSON 응답을 클라이언트에게 전송
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse);

		log.info("로그인 성공하였습니다");
	}

	// 로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		try {
			CommonUtils.responseAuthenticationError(response, failed.getMessage(), 401);
		} catch (RuntimeException | IOException | JSONException e) {
			e.printStackTrace();
		}

	}

}
