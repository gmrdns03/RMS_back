package com.project.LimeRMS.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Like {
    @Id
    @Column(nullable = false)
    private Long likeId;

    //수정 필요
    @Column(nullable = false)
    private String likeUserId;

    //수정 필요
    @Column(nullable = false)
    private Long contentId;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String delYn;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

}
