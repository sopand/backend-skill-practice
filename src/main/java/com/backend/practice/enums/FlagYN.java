package com.backend.practice.enums;

public enum FlagYN {

	Y("Y", "삭제처리"),
	N("N", "삭제되지 않음");

	private String code;
	private String code_name;

	FlagYN(String code, String code_name) {
		this.code = code;
		this.code_name = code_name;
	}

	public String code() {
		return this.code;
	}

	public String code_name() {
		return this.code_name;
	}
}
