package com.project.LimeRMS.entitiy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContentAttr is a Querydsl query type for ContentAttr
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentAttr extends EntityPathBase<ContentAttr> {

    private static final long serialVersionUID = -1205014859L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContentAttr contentAttr = new QContentAttr("contentAttr");

    public final NumberPath<Long> attrOrder = createNumber("attrOrder", Long.class);

    public final StringPath attrType = createString("attrType");

    public final QContentAttr ContentAttr;

    public final NumberPath<Long> contentAttrId = createNumber("contentAttrId", Long.class);

    public final StringPath logicalAttr = createString("logicalAttr");

    public final StringPath modfDt = createString("modfDt");

    public final StringPath modfUserId = createString("modfUserId");

    public final StringPath mustYn = createString("mustYn");

    public final StringPath physicalAttr = createString("physicalAttr");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public QContentAttr(String variable) {
        this(ContentAttr.class, forVariable(variable), INITS);
    }

    public QContentAttr(Path<? extends ContentAttr> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContentAttr(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContentAttr(PathMetadata metadata, PathInits inits) {
        this(ContentAttr.class, metadata, inits);
    }

    public QContentAttr(Class<? extends ContentAttr> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ContentAttr = inits.isInitialized("ContentAttr") ? new QContentAttr(forProperty("ContentAttr"), inits.get("ContentAttr")) : null;
    }

}

