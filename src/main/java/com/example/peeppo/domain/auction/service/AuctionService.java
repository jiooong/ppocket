package com.example.peeppo.domain.auction.service;

import com.example.peeppo.domain.auction.entity.Auction;
import com.example.peeppo.domain.auction.entity.AuctionList;
import com.example.peeppo.domain.auction.repository.AuctionRepository;
import com.example.peeppo.domain.goods.entity.Goods;
import com.example.peeppo.domain.goods.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final GoodsRepository goodsRepository;
    private final AuctionRepository auctionRepository;

    public String createAuction(Long goodsId) {
        //회원저장, 상품저장, 주문저장 ( 다 new 해주고 order 저장)
        Goods getGoods = findGoodsId(goodsId);
        Auction auction = new Auction();
        AuctionList auctionList = new AuctionList(getGoods,auction);

        auctionRepository.save(auctionList);
        return "등록 성공";
    }

    public Goods findGoodsId(Long goodsId) {
        return goodsRepository.findById(goodsId).orElse(null);
    }
}
