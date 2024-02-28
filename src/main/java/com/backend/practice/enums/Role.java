package com.backend.practice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
	
	
	USER("USER","일반 회원"),
	ADMIN("ADMIN","관리자 아이디");
	
	private String type;
	private String name;
	
	 public static Role fromString(String value) {
	        for (Role role : Role.values()) {
	            if (role.type.equals(value)) {
	                return role;
	            }
	        }
	        throw new IllegalArgumentException("No such role with type: " + value);
	    }
}
