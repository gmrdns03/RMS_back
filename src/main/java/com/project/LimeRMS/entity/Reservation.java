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
@Table(name = "Reservation")
public class Reservation {

//    @EmbeddedId
//    private ReservationPk reservationPk;

    @Id
    @Column(nullable = false)
    private Integer reserveId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserveUserId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId", nullable = false)
    private Content content;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime reserveDt;

    @Column(nullable = false)
    private LocalDateTime endDt;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String delYn;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
