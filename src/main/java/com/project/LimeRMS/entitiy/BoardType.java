package com.project.LimeRMS.entitiy;

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
public class BoardType {

    @Id
    private Long boardTypeId;

    @Column
    private String boardTypeNm;

    @Column
    private String boardTypeDesc;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @Column
    private String regUserId;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;
}
