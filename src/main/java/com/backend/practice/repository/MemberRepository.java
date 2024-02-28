package com.backend.practice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.practice.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Boolean existsByEmail(String email);

	Optional<Member> findByEmail(String email);
}
