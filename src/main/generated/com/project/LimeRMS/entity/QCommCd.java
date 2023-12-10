package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommCd is a Querydsl query type for CommCd
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommCd extends EntityPathBase<CommCd> {

    private static final long serialVersionUID = -1422429429L;

    public static final QCommCd commCd1 = new QCommCd("commCd1");

    public final StringPath cdDesc = createString("cdDesc");

    public final StringPath cdNm = createString("cdNm");

    public final StringPath commCd = createString("commCd");

    public final StringPath hiCommCd = createString("hiCommCd");

    public final DateTimePath<java.time.LocalDateTime> modfDt = createDateTime("modfDt", java.time.LocalDateTime.class);

    public final StringPath modfUserId = createString("modfUserId");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public QCommCd(String variable) {
        super(CommCd.class, forVariable(variable));
    }

    public QCommCd(Path<? extends CommCd> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommCd(PathMetadata metadata) {
        super(CommCd.class, metadata);
    }

}

