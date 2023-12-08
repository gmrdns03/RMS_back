package com.project.LimeRMS.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class ContentAttr {
    @Column(nullable = false)
    @Id
    private Long contentAttrId;

    @JoinColumn(name = "boardId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ContentAttr ContentAttr;

    @Column
    private String logicalAttr;

    @Column
    private String physicalAttr;

    @Column(columnDefinition = "VARCHAR(1) default 'N'")
    private String mustYn;

    @Column
    private Long attrOrder;

    @Column
    private String attrType;

    @CreatedDate
    @Column
    private LocalDateTime regDt;

    @Column
    private String regUserId;

    @LastModifiedDate
    @Column
    private String modfDt;

    @Column
    private String modfUserId;
}
