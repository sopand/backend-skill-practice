package com.backend.practice.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.backend.practice.util.CommonUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		Exception exception = (Exception) request.getAttribute("exception");
		try {
			if (exception == null) {
				CommonUtils.responseAuthenticationError(response, JwtErrorCode.ACCESS_DENIED);
			} else {
				if (exception instanceof ExpiredJwtException) {
					CommonUtils.responseAuthenticationError(response, JwtErrorCode.TOKEN_EXPIRED);
				} else if (exception.getCause() instanceof JwtException) {
					CommonUtils.responseAuthenticationError(response, exception.getCause().getMessage(), 401);
				} else {
					CommonUtils.responseAuthenticationError(response, JwtErrorCode.ACCESS_DENIED);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
