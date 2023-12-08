package com.project.LimeRMS.entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class CommCd {

    @Id
    private String commCd;

    @Column( nullable = false)
    private String hiCommCd;

    @Column
    private String cdNm;

    @Column
    private String cdDesc;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @LastModifiedDate
    @Column
    private LocalDateTime modfDt;

    @Column
    private String regUserId;

    @Column
    private String modfUserId;















}
