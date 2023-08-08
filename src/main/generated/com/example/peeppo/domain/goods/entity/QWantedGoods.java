package com.example.peeppo.domain.goods.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWantedGoods is a Querydsl query type for WantedGoods
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWantedGoods extends EntityPathBase<WantedGoods> {

    private static final long serialVersionUID = -2036819392L;

    public static final QWantedGoods wantedGoods = new QWantedGoods("wantedGoods");

    public final EnumPath<com.example.peeppo.domain.goods.enums.Category> category = createEnum("category", com.example.peeppo.domain.goods.enums.Category.class);

    public final StringPath content = createString("content");

    public final StringPath title = createString("title");

    public final NumberPath<Long> wantedId = createNumber("wantedId", Long.class);

    public QWantedGoods(String variable) {
        super(WantedGoods.class, forVariable(variable));
    }

    public QWantedGoods(Path<? extends WantedGoods> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWantedGoods(PathMetadata metadata) {
        super(WantedGoods.class, metadata);
    }

}

