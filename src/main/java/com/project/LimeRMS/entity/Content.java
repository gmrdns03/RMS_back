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
import java.util.Objects;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;


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

    public String getFreeField(String fieldNm) {
        if (Objects.equals(fieldNm, "text1")) {
            return this.text1;
        }  else if (Objects.equals(fieldNm, "text2")) {
            return this.text2;
        } else if (Objects.equals(fieldNm, "text3")) {
            return this.text3;
        } else if (Objects.equals(fieldNm, "text4")) {
            return this.text4;
        } else if (Objects.equals(fieldNm, "text5")) {
            return this.text5;
        } else if (Objects.equals(fieldNm, "integer1")) {
            return String.valueOf(this.integer1);
        } else if (Objects.equals(fieldNm, "integer2")) {
            return String.valueOf(this.integer2);
        } else if (Objects.equals(fieldNm, "integer3")) {
            return String.valueOf(this.integer3);
        } else if (Objects.equals(fieldNm, "integer4")) {
            return String.valueOf(this.integer4);
        } else if (Objects.equals(fieldNm, "integer5")) {
            return String.valueOf(this.integer5);
        } else if (Objects.equals(fieldNm, "date1")) {
            return String.valueOf(this.date1);
        } else if (Objects.equals(fieldNm, "date2")) {
            return String.valueOf(this.date2);
        } else if (Objects.equals(fieldNm, "date3")) {
            return String.valueOf(this.date3);
        } else if (Objects.equals(fieldNm, "date4")) {
            return String.valueOf(this.date4);
        } else if (Objects.equals(fieldNm, "date5")) {
            return String.valueOf(this.date5);
        } else if (Objects.equals(fieldNm, "bool1")) {
            return String.valueOf(this.bool1);
        } else if (Objects.equals(fieldNm, "bool2")) {
            return String.valueOf(this.bool2);
        } else if (Objects.equals(fieldNm, "bool3")) {
            return String.valueOf(this.bool3);
        } else if (Objects.equals(fieldNm, "bool4")) {
            return String.valueOf(this.bool4);
        } else if (Objects.equals(fieldNm, "bool5")) {
            return String.valueOf(this.bool5);
        } else if (Objects.equals(fieldNm, "float1")) {
            return String.valueOf(this.float1);
        } else if (Objects.equals(fieldNm, "float2")) {
            return String.valueOf(this.float2);
        } else {
            return null;
        }
    }
}
