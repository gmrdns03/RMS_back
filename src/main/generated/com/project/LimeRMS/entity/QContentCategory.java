package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentCategory is a Querydsl query type for ContentCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentCategory extends EntityPathBase<ContentCategory> {

    private static final long serialVersionUID = 723381209L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentCategory contentCategory = new QContentCategory("contentCategory");

    public final QBoard board;

    public final NumberPath<Integer> cateId = createNumber("cateId", Integer.class);

    public final StringPath cateNm = createString("cateNm");

    public final NumberPath<Integer> highCate = createNumber("highCate", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modfDt = createDateTime("modfDt", java.time.LocalDateTime.class);

    public final StringPath modfUserId = createString("modfUserId");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserid = createString("regUserid");

    public QContentCategory(String variable) {
        this(ContentCategory.class, forVariable(variable), INITS);
    }

    public QContentCategory(Path<? extends ContentCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentCategory(PathMetadata metadata, PathInits inits) {
        this(ContentCategory.class, metadata, inits);
    }

    public QContentCategory(Class<? extends ContentCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

