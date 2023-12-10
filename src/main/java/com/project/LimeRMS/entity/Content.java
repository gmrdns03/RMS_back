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
@Table(name = "Content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer contentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateId", nullable = false)
    private ContentCategory contentCategory;

    @Column
    private String contentNm;

    @Column
    private String contentDesc;

    @Column
    private String contentImg;

    @Column
    private String contentHtml;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String delYn;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String noticeYn;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String secretYn;

    @Column
    private String location;

    @Column
    private String rentalMessage;

    @Column
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column
    private String regUserId;

    @Column
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column
    private String text1;

    @Column
    private String text2;

    @Column
    private String text3;

    @Column
    private String text4;

    @Column
    private String text5;

    @Column
    private Integer integer1;

    @Column
    private Integer integer2;

    @Column
    private Integer integer3;

    @Column
    private Integer integer4;

    @Column
    private Integer integer5;

    @Column
    private LocalDateTime date1;

    @Column
    private LocalDateTime date2;

    @Column
    private LocalDateTime date3;

    @Column
    private LocalDateTime date4;

    @Column
    private LocalDateTime date5;

    @Column
    private Boolean bool1;

    @Column
    private Boolean bool2;

    @Column
    private Boolean bool3;

    @Column
    private Boolean bool4;

    @Column
    private Boolean bool5;

    @Column
    private Double float1;

    @Column
    private Double float2;
}
