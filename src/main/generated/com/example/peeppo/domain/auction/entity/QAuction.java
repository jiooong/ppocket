package com.example.peeppo.domain.auction.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuction is a Querydsl query type for Auction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuction extends EntityPathBase<Auction> {

    private static final long serialVersionUID = -922263153L;

    public static final QAuction auction = new QAuction("auction");

    public final NumberPath<Long> auctionId = createNumber("auctionId", Long.class);

    public final ListPath<AuctionList, QAuctionList> auctionList = this.<AuctionList, QAuctionList>createList("auctionList", AuctionList.class, QAuctionList.class, PathInits.DIRECT2);

    public QAuction(String variable) {
        super(Auction.class, forVariable(variable));
    }

    public QAuction(Path<? extends Auction> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuction(PathMetadata metadata) {
        super(Auction.class, metadata);
    }

}

