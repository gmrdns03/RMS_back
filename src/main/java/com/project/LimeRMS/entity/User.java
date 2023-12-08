package com.project.LimeRMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @Column(nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    @JsonIgnore
    private Long authentication;
    // 수정 필요

    @Column(nullable = false)
    private String userEmail;

    @Column
    private String profileImg;

    @Column
    private String userNm;

    @Column
    private String password;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String isSuperUser;

    @Column
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String delYn;

    @CreatedDate
    @Column
    private LocalDateTime joinDt;

    @Column
    private String regUserId;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

}
