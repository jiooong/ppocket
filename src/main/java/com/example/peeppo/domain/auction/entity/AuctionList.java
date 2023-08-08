package com.example.peeppo.domain.auction.entity;

import com.example.peeppo.domain.goods.entity.Goods;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class AuctionList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "GOODS_ID")
    private Goods goods;

    public AuctionList(Goods goods, Auction auction){
        this.goods = goods;
        this.auction = auction;
    }

}
