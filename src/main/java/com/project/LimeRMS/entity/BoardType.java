package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "BoardType")
public class BoardType {

    @Id
    @Column(nullable = false)
    private Integer boardTypeId;

    @Column
    private String boardTypeNm;

    @Column
    private String boardTypeDesc;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column
    private String regUserId;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
