package com.backend.practice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTimeEntity {

	 // 생성일 자동 설정
    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;

    // 수정일 자동 설정
    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
