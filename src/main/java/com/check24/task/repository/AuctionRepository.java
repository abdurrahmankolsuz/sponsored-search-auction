package com.check24.task.repository;

import com.check24.task.model.Auction;
import com.check24.task.model.Slot;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Getter
public class AuctionRepository {
    private final Map<String, Auction> auctions = new HashMap<>();

    public Auction addAuction(String keyword, Auction auction) {
         auctions.put(keyword, auction);
         return auctions.get(keyword);
    }

    public Auction getAuctionByKeyword(String keyword) {
        return auctions.get(keyword);
    }

    public List<Slot> getSlotsByKeyword(String keyword) {
        Auction auction = auctions.get(keyword);
        auction.sort();
        return auction.getSlots();
    }


}
