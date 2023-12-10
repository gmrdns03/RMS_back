package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 507369640L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardDesc = createString("boardDesc");

    public final NumberPath<Integer> boardId = createNumber("boardId", Integer.class);

    public final StringPath boardImg = createString("boardImg");

    public final StringPath boardNm = createString("boardNm");

    public final NumberPath<Integer> boardSn = createNumber("boardSn", Integer.class);

    public final StringPath boardStat = createString("boardStat");

    public final QBoardType boardType;

    public final NumberPath<Integer> commentAuth = createNumber("commentAuth", Integer.class);

    public final StringPath commentImgYn = createString("commentImgYn");

    public final StringPath commentYn = createString("commentYn");

    public final NumberPath<Integer> extensionLimit = createNumber("extensionLimit", Integer.class);

    public final NumberPath<Integer> listNumLimit = createNumber("listNumLimit", Integer.class);

    public final StringPath modfDt = createString("modfDt");

    public final StringPath modfUserId = createString("modfUserId");

    public final NumberPath<Integer> modifyAuth = createNumber("modifyAuth", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public final NumberPath<Integer> rentalPeriod = createNumber("rentalPeriod", Integer.class);

    public final StringPath scoreYn = createString("scoreYn");

    public final NumberPath<Integer> viewAuth = createNumber("viewAuth", Integer.class);

    public final NumberPath<Integer> writeAuth = createNumber("writeAuth", Integer.class);

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardType = inits.isInitialized("boardType") ? new QBoardType(forProperty("boardType")) : null;
    }

}

