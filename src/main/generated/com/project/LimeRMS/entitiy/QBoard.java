package com.project.LimeRMS.entitiy;

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

    private static final long serialVersionUID = -2042045487L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board1 = new QBoard("board1");

    public final QBoard board;

    public final StringPath boardDesc = createString("boardDesc");

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public final StringPath boardImg = createString("boardImg");

    public final StringPath boardNm = createString("boardNm");

    public final NumberPath<Long> boardSn = createNumber("boardSn", Long.class);

    public final StringPath boardStat = createString("boardStat");

    public final NumberPath<Long> commentAuth = createNumber("commentAuth", Long.class);

    public final StringPath commentImgYn = createString("commentImgYn");

    public final StringPath commentYn = createString("commentYn");

    public final NumberPath<Long> extensionLimit = createNumber("extensionLimit", Long.class);

    public final NumberPath<Long> listNumLimit = createNumber("listNumLimit", Long.class);

    public final StringPath modfDt = createString("modfDt");

    public final StringPath modfUserId = createString("modfUserId");

    public final NumberPath<Long> modifyAuth = createNumber("modifyAuth", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public final NumberPath<Long> rentalPeriod = createNumber("rentalPeriod", Long.class);

    public final StringPath scoreYn = createString("scoreYn");

    public final NumberPath<Long> viewAuth = createNumber("viewAuth", Long.class);

    public final NumberPath<Long> writeAuth = createNumber("writeAuth", Long.class);

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
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

