package com.project.LimeRMS.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRental is a Querydsl query type for Rental
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRental extends EntityPathBase<Rental> {

    private static final long serialVersionUID = -1002189918L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRental rental = new QRental("rental");

    public final QContent content;

    public final NumberPath<Integer> extensionCnt = createNumber("extensionCnt", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> modfDt = createDateTime("modfDt", java.time.LocalDateTime.class);

    public final StringPath modfUserId = createString("modfUserId");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final StringPath regUserId = createString("regUserId");

    public final DateTimePath<java.time.LocalDateTime> rentalDt = createDateTime("rentalDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> rentalId = createNumber("rentalId", Integer.class);

    public final StringPath rentalStat = createString("rentalStat");

    public final DateTimePath<java.time.LocalDateTime> returnDt = createDateTime("returnDt", java.time.LocalDateTime.class);

    public final QUser user;

    public QRental(String variable) {
        this(Rental.class, forVariable(variable), INITS);
    }

    public QRental(Path<? extends Rental> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRental(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRental(PathMetadata metadata, PathInits inits) {
        this(Rental.class, metadata, inits);
    }

    public QRental(Class<? extends Rental> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.content = inits.isInitialized("content") ? new QContent(forProperty("content"), inits.get("content")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

