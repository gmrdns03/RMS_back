package com.project.LimeRMS.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @JoinColumn(name = "boardTypeId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Board board;

    @Column
    private String boardNm;

    @Column
    private String boardDesc;

    @Column
    private String boardImg;

    @Column
    private String boardStat;

    @Column
    private Long boardSn;

    @Column
    private Long rentalPeriod;

    @Column
    private Long extensionLimit;

    @Column(columnDefinition = "TINYINT default 9")
    private Long viewAuth;

    @Column(columnDefinition = "TINYINT default 9")
    private Long writeAuth;

    @Column(columnDefinition = "TINYINT default 9")
    private Long commentAuth;

    @Column(columnDefinition = "TINYINT default 9")
    private Long modifyAuth;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String scoreYn;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String commentYn;

    @Column
    private Long listNumLimit;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String commentImgYn;

    @Column
    private String regUserId;

    @LastModifiedDate
    @Column
    private String modfDt;

    @Column
    private String modfUserId;
}
