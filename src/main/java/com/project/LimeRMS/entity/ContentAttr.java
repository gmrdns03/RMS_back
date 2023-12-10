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
@Table(name = "ContentAttr")
public class ContentAttr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer contentAttrId;

    @JoinColumn(name = "boardId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column
    private String logicalAttr;

    @Column
    private String physicalAttr;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String mustYn;

    @Column
    private Integer attrOrder;

    @Column
    private String attrType;

    @Column
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column
    private String regUserId;

    @Column
    @UpdateTimestamp
    private String modfDt;

    @Column
    private String modfUserId;
}
