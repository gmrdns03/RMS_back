package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardType is a Querydsl query type for BoardType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardType extends EntityPathBase<BoardType> {

    private static final long serialVersionUID = -1527166974L;

    public static final QBoardType boardType = new QBoardType("boardType");

    public final StringPath boardTypeDesc = createString("boardTypeDesc");

    public final NumberPath<Integer> boardTypeId = createNumber("boardTypeId", Integer.class);

    public final StringPath boardTypeNm = createString("boardTypeNm");

    public final DateTimePath<java.time.LocalDateTime> modfDt = createDateTime("modfDt", java.time.LocalDateTime.class);

    public final StringPath modfUserId = createString("modfUserId");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public QBoardType(String variable) {
        super(BoardType.class, forVariable(variable));
    }

    public QBoardType(Path<? extends BoardType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardType(PathMetadata metadata) {
        super(BoardType.class, metadata);
    }

}

