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
    private LocalDateTime rentalDate;

    @Column
    private LocalDateTime returnDate;

    @Column(columnDefinition = "TINYINT default 0")
    private Long extensionCnt;

    @Column
    private String rentalStat;

    @Column
    private LocalDateTime regDt;

    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column(nullable = false)
    private String reguserId;
}
