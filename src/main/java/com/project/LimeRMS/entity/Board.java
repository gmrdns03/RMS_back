package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;

    @JoinColumn(name = "boardTypeId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardType boardType;

    @Column
    private String boardNm;

    @Column
    private String boardDesc;

    @Column
    private String boardImg;

    @Column
    private String boardStat;

    @Column
    private Integer boardSn;

    @Column
    private Integer rentalPeriod;

    @Column
    private Integer extensionLimit;

    @Column(columnDefinition = "TINYINT default 9", nullable = false)
    private Integer viewAuth;

    @Column(columnDefinition = "TINYINT default 9", nullable = false)
    private Integer writeAuth;

    @Column(columnDefinition = "TINYINT default 9", nullable = false)
    private Integer commentAuth;

    @Column(columnDefinition = "TINYINT default 9", nullable = false)
    private Integer modifyAuth;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String scoreYn;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String commentYn;

    @Column
    private Integer listNumLimit;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String commentImgYn;

    @Column
    private String regUserId;

    @Column(nullable = false)
    @UpdateTimestamp
    private String modfDt;

    @Column
    private String modfUserId;
}
