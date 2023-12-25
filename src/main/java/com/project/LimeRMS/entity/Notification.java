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
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer notiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId", referencedColumnName = "userId", nullable = false)
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false)
    private User sender;

    @Column
    private Integer contentId;

    @Column
    private String notiType;

    @Column
    private String notiContent;

    @Column
    private String notiUrl;

    @Column
    private String notiStatus;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime joinDt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column
    private String regUserId;
}
