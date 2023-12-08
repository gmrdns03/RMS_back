package com.project.LimeRMS.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class ContentCategory {
    @Column(nullable = false)
    @Id
    private Long cateId;

    @JoinColumn(name = "boardId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Board board;

    @Column
    private String cateNm;

    @Column
    private Long highCate;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @Column
    private String regUserid;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
