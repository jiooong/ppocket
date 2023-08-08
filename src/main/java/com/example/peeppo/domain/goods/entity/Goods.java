package com.example.peeppo.domain.goods.entity;

import com.example.peeppo.domain.goods.dto.GoodsRequestDto;
import com.example.peeppo.domain.goods.enums.Category;
import com.example.peeppo.global.utils.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class Goods extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private Long goodsId;
    private String title;
    private String content;
    private String location;
    private String goodsCondition;
    private String tradeType;
    @Enumerated(EnumType.STRING) // ENUM타입을 String으로 넣음
    private Category category;
    private Long userId; // 유저부분 완료 시 수정할 것
    private boolean isDeleted;
//    @OneToOne(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wanted_id")
    private WantedGoods wantedGoods;

    public Goods(GoodsRequestDto requestDto, WantedGoods wantedGoods) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.location = requestDto.getLocation();
        this.goodsCondition = requestDto.getGoodsCondition();
        this.tradeType = requestDto.getTradeType();
        //this.category = requestDto.getCategory();
        this.wantedGoods = wantedGoods;
    }

    public void update(GoodsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.location = requestDto.getLocation();
    }
}
