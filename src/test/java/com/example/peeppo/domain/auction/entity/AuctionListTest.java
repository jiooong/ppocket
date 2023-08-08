package com.example.peeppo.domain.auction.entity;

import com.example.peeppo.domain.goods.entity.Goods;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuctionListTest {


    @Test
    void createAuctionList() {
        Goods goods = new Goods();
        Auction auction = new Auction();
        AuctionList auctionList = new AuctionList(goods,auction);

        Assertions.assertThat(auctionList.getId()).isEqualTo(1);
    }


}