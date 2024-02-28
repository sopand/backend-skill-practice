package com.backend.practice.util;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import com.backend.practice.exception.CustomException;
import com.backend.practice.exception.ErrorCode;
import com.backend.practice.jwt.JwtErrorCode;
import com.backend.practice.response.ResResult;

import jakarta.servlet.http.HttpServletResponse;

public final class CommonUtils {

	

	public static ResResult isSaveSuccessful(Long sid, String message) {

		if (sid == null)
			throw new CustomException(ErrorCode.DATA_INSERT_FAILED);

		return ResResult.builder().success(true).message(message).statusCode(200).build();
	}


	public static Timestamp stringToTimeStampFormat(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

		// LocalDateTime 객체를 Timestamp 객체로 변환
		return Timestamp.valueOf(dateTime);
	}


	public static void responseAuthenticationError(HttpServletResponse response, JwtErrorCode jwtErrorCode)
			throws RuntimeException, IOException, JSONException {
		JSONObject responseJson = new JSONObject();
		responseJson.put("message", jwtErrorCode.getMessage());
		responseJson.put("status", jwtErrorCode.getStatus());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(responseJson);
	}
	public static void responseAuthenticationError(HttpServletResponse response, String message,int status)
			throws RuntimeException, IOException, JSONException {
		JSONObject responseJson = new JSONObject();
		responseJson.put("message", message);
		responseJson.put("status", status);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.getWriter().print(responseJson);
	}
}
