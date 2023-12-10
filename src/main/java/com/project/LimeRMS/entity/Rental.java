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
@Table(name = "Rental")
public class Rental {

//    @EmbeddedId
//    private RentalPk rentalPk;

    @Id
    @Column(nullable = false)
    private Integer rentalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rentalUserId", referencedColumnName = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId", nullable = false)
    private Content content;

    @Column
    private LocalDateTime rentalDt;

    @Column
    private LocalDateTime returnDt;

    @Column(name = "extensionCnt", nullable = false, columnDefinition = "int default 0")
    private Integer extensionCnt;

    @Column
    private String rentalStat;

    @Column
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column(nullable = false)
    private String regUserId;
}
