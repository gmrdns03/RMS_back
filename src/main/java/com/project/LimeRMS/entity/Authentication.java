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
@Table(name = "Authentication")
public class Authentication {

    @Id
    @Column(nullable = false)
    private Integer authId;

    @Column
    private String authNm;

    @Column
    private String authDesc;

    @Column(columnDefinition = "TINYINT default 9", nullable = false)
    private Integer priority;

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
