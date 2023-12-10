package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "ContentCategory")
public class ContentCategory {

//    @EmbeddedId
//    private ContentCategoryPk contentCategoryPk;

    @Id
    @Column(nullable = false)
    private Integer cateId;

    @JoinColumn(name = "boardId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column
    private String cateNm;

    @Column
    private Integer highCate;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column
    private String regUserid;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
