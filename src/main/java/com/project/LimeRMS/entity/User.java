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
@Table(name = "User")
public class User {

//    @EmbeddedId
//    private UserPk userPk;
    @Id
    @Column(nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authId", nullable = false)
    private Authentication authentication;

    @Column(nullable = false)
    private String userEmail;

    @Column
    private String profileImg;

    @Column
    private String userNm;

    @Column
    private String password;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String isSuperUser;

    @Column
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String delYn;

    @Column
    @CreationTimestamp
    private LocalDateTime joinDt;

    @Column
    private String regUserId;

    @Column
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

}
