package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContent is a Querydsl query type for Content
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContent extends EntityPathBase<Content> {

    private static final long serialVersionUID = -1144474181L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContent content = new QContent("content");

    public final BooleanPath bool1 = createBoolean("bool1");

    public final BooleanPath bool2 = createBoolean("bool2");

    public final BooleanPath bool3 = createBoolean("bool3");

    public final BooleanPath bool4 = createBoolean("bool4");

    public final BooleanPath bool5 = createBoolean("bool5");

    public final QContentCategory contentCategory;

    public final StringPath contentDesc = createString("contentDesc");

    public final StringPath contentHtml = createString("contentHtml");

    public final NumberPath<Integer> contentId = createNumber("contentId", Integer.class);

    public final StringPath contentImg = createString("contentImg");

    public final StringPath contentNm = createString("contentNm");

    public final DateTimePath<java.time.LocalDateTime> date1 = createDateTime("date1", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> date2 = createDateTime("date2", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> date3 = createDateTime("date3", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> date4 = createDateTime("date4", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> date5 = createDateTime("date5", java.time.LocalDateTime.class);

    public final StringPath delYn = createString("delYn");

    public final NumberPath<Double> float1 = createNumber("float1", Double.class);

    public final NumberPath<Double> float2 = createNumber("float2", Double.class);

    public final NumberPath<Integer> integer1 = createNumber("integer1", Integer.class);

    public final NumberPath<Integer> integer2 = createNumber("integer2", Integer.class);

    public final NumberPath<Integer> integer3 = createNumber("integer3", Integer.class);

    public final NumberPath<Integer> integer4 = createNumber("integer4", Integer.class);

    public final NumberPath<Integer> integer5 = createNumber("integer5", Integer.class);

    public final StringPath location = createString("location");

    public final DateTimePath<java.time.LocalDateTime> modfDt = createDateTime("modfDt", java.time.LocalDateTime.class);

    public final StringPath modfUserId = createString("modfUserId");

    public final StringPath noticeYn = createString("noticeYn");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public final StringPath rentalMessage = createString("rentalMessage");

    public final StringPath secretYn = createString("secretYn");

    public final StringPath text1 = createString("text1");

    public final StringPath text2 = createString("text2");

    public final StringPath text3 = createString("text3");

    public final StringPath text4 = createString("text4");

    public final StringPath text5 = createString("text5");

    public QContent(String variable) {
        this(Content.class, forVariable(variable), INITS);
    }

    public QContent(Path<? extends Content> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContent(PathMetadata metadata, PathInits inits) {
        this(Content.class, metadata, inits);
    }

    public QContent(Class<? extends Content> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contentCategory = inits.isInitialized("contentCategory") ? new QContentCategory(forProperty("contentCategory"), inits.get("contentCategory")) : null;
    }

}

