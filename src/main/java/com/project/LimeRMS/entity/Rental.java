package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Rental {
    @Id
    @Column(nullable = false)
    private Long rentalId;

    //수정 필요
    @Column(nullable = false)
    private String rentalUserId;

    //수정 필요
    @Column(nullable = false)
    private Long contentId;

    @Column
    private LocalDateTime rentalDt;

    @Column
    private LocalDateTime returnDt;

    @Column(columnDefinition = "TINYINT default 0")
    private Long extensionCnt;

    @Column
    private String rentalStat;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column(nullable = false)
    private String regUserId;
}
