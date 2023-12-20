package com.project.LimeRMS.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "User")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authId", nullable = false)
    private Authentication authentication;

    @Column(nullable = false, unique=true)
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

    @Column
    private String userStat;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }


}
