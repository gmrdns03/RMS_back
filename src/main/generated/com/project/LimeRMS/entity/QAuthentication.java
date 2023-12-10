package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuthentication is a Querydsl query type for Authentication
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuthentication extends EntityPathBase<Authentication> {

    private static final long serialVersionUID = 1897976566L;

    public static final QAuthentication authentication = new QAuthentication("authentication");

    public final StringPath authDesc = createString("authDesc");

    public final NumberPath<Integer> authId = createNumber("authId", Integer.class);

    public final StringPath authNm = createString("authNm");

    public final DateTimePath<java.time.LocalDateTime> modfDt = createDateTime("modfDt", java.time.LocalDateTime.class);

    public final StringPath modfUserId = createString("modfUserId");

    public final NumberPath<Integer> priority = createNumber("priority", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public QAuthentication(String variable) {
        super(Authentication.class, forVariable(variable));
    }

    public QAuthentication(Path<? extends Authentication> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuthentication(PathMetadata metadata) {
        super(Authentication.class, metadata);
    }

}

