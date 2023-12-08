package com.project.LimeRMS.entity;

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
public class Comment {
    @Id
    @Column(nullable = false)
    private Long commendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId", nullable = false)
    @JsonIgnore
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commendId", nullable = false)
    @JsonIgnore
    private Comment hiComment;

    @Column
    private String comment;

    @Column(columnDefinition = "TINYINT default 0")
    private Long score;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column
    private String image;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String delYn;
}
