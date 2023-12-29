package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
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
@Table(name = "BoardManager")
public class BoardManager {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId", referencedColumnName = "boardId", nullable = false)
    private Board board;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String delYn;

    @Column
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(nullable = false)
    private String regUserId;

    @Column
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
