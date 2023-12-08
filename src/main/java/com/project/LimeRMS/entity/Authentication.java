package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Authentication {
    @Id
    @Column(nullable = false)
    private Long authId;

    @Column
    private String authNm;

    @Column
    private String authDesc;

    @Column(columnDefinition = "INT(1) default 9")
    private Long priority;

    @Column
    private LocalDateTime regDt;

    @Column
    private String regUserId;

    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
