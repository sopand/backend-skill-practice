package com.backend.practice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.practice.jwt.CustomAccessHandler;
import com.backend.practice.jwt.CustomAuthenticationEntryPoint;
import com.backend.practice.jwt.JWTFilter;
import com.backend.practice.jwt.JWTUtil;
import com.backend.practice.jwt.LoginFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	//AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomAccessHandler customAccessHandler;
	
	private final JWTUtil jwtUtil;
	
	
	
	// LoginFilter를 사용하기위해 Manager객체를 Bean 등록
	@Bean
	public AuthenticationManager getAuthManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
	    return web -> web.ignoring().requestMatchers(
	                                                 "/favicon.ico",
	                                                 "/swagger-ui/**",
	                                                 "/swagger-resources/**",
	                                                 "/v3/api-docs/**");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// CSRF disable
		http.csrf((csrfConfig) -> csrfConfig.disable())
//				.headers(
//						(headerConfig) -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
				// 경로별 인가 작업
				.authorizeHttpRequests(auth -> auth 
						.requestMatchers("/api/member/login","/","/api/member/signup").permitAll()
						.requestMatchers("/api/auth/**").hasAnyAuthority("ADMIN","USER")
						.requestMatchers("/api/admin/**").hasAuthority("ADMIN")
						.anyRequest().authenticated());
		// http basic 인증 방식 disable
		http.httpBasic(basic -> basic.disable());
		http.exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler(customAccessHandler));
		
		// Form 로그인 disable
		http.formLogin((formLogin) -> formLogin.disable());
		
		
		
		// LoginFilter의 
		LoginFilter loginFilter = new LoginFilter(getAuthManager(authenticationConfiguration),jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/member/login");
		
		// 기본으로 설정되어있는 filter를 대체하기 위한것 UsernamePasswordAuthenticationFilter를  내가 만든 LoginFilter로 대체할것
		http.addFilterAt(loginFilter,UsernamePasswordAuthenticationFilter.class);
		//JWTFilter 등록
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		// 세션 설정
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// logout disable
		http.logout(logout -> logout.disable());
		
		return http.build();
	}

}
