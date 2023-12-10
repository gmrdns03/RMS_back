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
@Table(name = "CommCd")
public class CommCd {

    @Id
    @Column(nullable = false)
    private String commCd;

    @Column(nullable = false)
    private String hiCommCd;

    @Column
    private String cdNm;

    @Column
    private String cdDesc;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String regUserId;

    @Column
    private String modfUserId;















}
