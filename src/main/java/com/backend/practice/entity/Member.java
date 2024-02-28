package com.backend.practice.entity;



import com.backend.practice.enums.FlagYN;
import com.backend.practice.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@NoArgsConstructor
@Getter
@Entity
@SuperBuilder
@Table(name="tb_member")
public class Member extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_sid")
	private Long memberSid;
	
	//사용자 이메일
	@NotNull
	private String email;
	
	// 패스워드
	@NotNull
	private String password;
	
	// 사용자 성명 OR 별명
	@NotNull
	private String name;
	
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	// 아이디 삭제 여부
	@Column(name="del_yn")
	@Enumerated(EnumType.STRING)
	private FlagYN delYn;

	

}
