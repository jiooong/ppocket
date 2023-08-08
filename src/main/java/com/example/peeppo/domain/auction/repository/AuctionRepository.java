package com.example.peeppo.domain.auction.repository;

import com.example.peeppo.domain.auction.entity.AuctionList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<AuctionList, Long> {
}
