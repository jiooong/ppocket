package com.example.peeppo.domain.auction.controller;


import com.example.peeppo.domain.auction.dto.AuctionListResponseDto;
import com.example.peeppo.domain.auction.dto.AuctionRequestDto;
import com.example.peeppo.domain.auction.dto.AuctionResponseDto;
import com.example.peeppo.domain.auction.entity.AuctionList;
import com.example.peeppo.domain.auction.service.AuctionService;
import com.example.peeppo.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction")
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/{goodsId}")
    public AuctionResponseDto createAuction(@PathVariable("goodsId") Long goodsId,
                                            @RequestBody AuctionRequestDto auctionRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
            return auctionService.createAuction(goodsId, auctionRequestDto, userDetails.getUser());
    }

    @GetMapping
    public List<AuctionListResponseDto> allAuction(){
        return auctionService.findAllAuction();
    }

    @GetMapping("/{auctionId}")
    public AuctionResponseDto getAuction(@PathVariable("auctionId")Long auctionId){
        return auctionService.findAuctionById(auctionId);
    }

    @DeleteMapping("/{auctionId}")
    public String deleteAuction(@PathVariable("auctionId") Long auctionId){
        auctionService.deleteAuction(auctionId);
        return "삭제성공";
    }
}