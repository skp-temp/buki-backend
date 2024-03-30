package com.example.skptemp.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    // Entity가
    // 생성되어 저장될 때 시간이 자동 저장됩니다.
    @Column(name = "createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    // 조회한 Entity 값을 변경할 때 시간이 자동 저장됩니다.
    @Column(name = "last_updated_at")
    @LastModifiedDate
    private LocalDateTime lastUpdatedAt;
}
