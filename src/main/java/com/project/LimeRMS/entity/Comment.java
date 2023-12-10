package com.project.LimeRMS.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "Comment")
public class Comment {

//    @EmbeddedId
//    private CommentPk commentPk;

    @Id
    @Column(nullable = false)
    private Integer commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contentId", nullable = false)
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCommentId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> subComment = new ArrayList<>();

    @Column
    private String comment;

    @Column(columnDefinition = "TINYINT default 0", nullable = false)
    private Integer score;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime regDt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modfDt;

    @Column
    private String modfUserId;

    @Column
    private String image;

    @Column(columnDefinition = "VARCHAR(1) default 'N'", nullable = false)
    private String delYn;
}
