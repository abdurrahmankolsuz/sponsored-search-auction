package com.check24.task.service;

import com.check24.task.model.Bidder;
import com.check24.task.model.Auction;
import com.check24.task.service.pricing.PricingStrategy;
import com.check24.task.service.ranking.RankingStrategy;

import java.math.BigDecimal;

public interface AuctionService {
    Auction createAnAuction(String keyword, PricingStrategy pricingStrategy, RankingStrategy rankingStrategy);

    Auction placeBid(String keyword, BigDecimal bidPrice, Bidder bidder);

}
