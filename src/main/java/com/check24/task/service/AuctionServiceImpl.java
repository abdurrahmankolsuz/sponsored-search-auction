package com.check24.task.service;

import com.check24.task.exception.NotFoundAuctionException;
import com.check24.task.exception.NotFoundSlotException;
import com.check24.task.model.Bidder;
import com.check24.task.model.Auction;
import com.check24.task.model.Slot;
import com.check24.task.repository.AuctionRepository;
import com.check24.task.service.pricing.PricingStrategy;
import com.check24.task.service.ranking.RankingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }


    @Override
    public Auction createAnAuction(String keyword, PricingStrategy pricingStrategy, RankingStrategy rankingStrategy) {
        Auction auction = new Auction(keyword);
        auction.setRankingStrategy(rankingStrategy);
        auction.setPricingStrategy(pricingStrategy);
        return auctionRepository.addAuction(auction.getKeyword(), auction);
    }


    @Override
    public Auction placeBid(String keyword, BigDecimal bidPrice, Bidder bidder) {
        Auction result;
        var auction = auctionRepository.getAuctionByKeyword(keyword);
        if (auction != null) {
            List<Slot> slots = auction.getSlots();
            if (slots.stream().anyMatch(s -> s.getBidder().getHyperLink().equalsIgnoreCase(bidder.getHyperLink()))) {
                Optional<Slot> slot = slots.stream().filter(s -> s.getBidder().getHyperLink().equalsIgnoreCase(bidder.getHyperLink())).findFirst();
                if (slot.isPresent()) {
                    slot.get().setBidder(bidder);
                    slot.get().setPrice(bidPrice);
                    auction.setSlots(slots);
                    auctionRepository.addAuction(keyword, auction);
                } else throw new NotFoundSlotException("Not Found slot");
            } else {
                Slot slot = new Slot();
                slot.setBidder(bidder);
                slot.setPrice(bidPrice);
                auction.getSlots().add(slot);
            }
            result = auction;

        } else throw new NotFoundAuctionException("Auction is not found");

        return result;
    }

}
