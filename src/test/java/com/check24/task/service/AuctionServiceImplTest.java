package com.check24.task.service;

import com.check24.task.exception.NotFoundAuctionException;
import com.check24.task.model.Bidder;
import com.check24.task.model.Auction;
import com.check24.task.model.Slot;
import com.check24.task.repository.AuctionRepository;
import com.check24.task.service.pricing.GeneralizedFirstPrice;
import com.check24.task.service.ranking.RankByBid;
import com.check24.task.service.ranking.RankByScore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuctionServiceImplTest {

    @InjectMocks
    AuctionServiceImpl auctionService;

    @Mock
    AuctionRepository auctionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createAnAuction() {
        Auction auction = new Auction("car insurance");
        auction.setPricingStrategy(new GeneralizedFirstPrice());
        auction.setRankingStrategy(new RankByScore());

        Bidder bidder = new Bidder();
        bidder.setBudget(new BigDecimal(100));
        bidder.setDescription("ABC Company");
        bidder.setTitle("ABC");
        bidder.setHyperLink("www.abc.com");

        Slot slot = new Slot();
        slot.setPrice(new BigDecimal(40));
        slot.setIdentifier(1);
        slot.setBidder(bidder);

        Bidder bidder1 = new Bidder();
        bidder1.setBudget(new BigDecimal(100));
        bidder1.setDescription("ABD Company");
        bidder1.setTitle("ABD");
        bidder1.setHyperLink("www.abd.com");

        Slot slot1 = new Slot();
        slot1.setPrice(new BigDecimal(50));
        slot1.setIdentifier(2);
        slot1.setBidder(bidder1);

        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        slots.add(slot1);
        auction.setSlots(slots);

        when(auctionRepository.addAuction(anyString(), any(Auction.class))).thenReturn(auction);

        Auction auction1 = auctionService.createAnAuction("car insurance",new GeneralizedFirstPrice(),new RankByScore());

        Assertions.assertNotNull(auction1);
        Assertions.assertEquals("car insurance", auction1.getKeyword());
    }


    @Test
    void placeBid() {

        Auction auction = new Auction("car insurance");
        auction.setPricingStrategy(new GeneralizedFirstPrice());
        auction.setRankingStrategy(new RankByBid());

        Bidder bidder = new Bidder();
        bidder.setBudget(new BigDecimal(100));
        bidder.setDescription("ABC Company");
        bidder.setTitle("ABC");
        bidder.setHyperLink("www.abc.com");

        Slot slot = new Slot();
        slot.setPrice(new BigDecimal(10));
        slot.setIdentifier(1);
        slot.setBidder(bidder);

        Bidder bidder1 = new Bidder();
        bidder1.setBudget(new BigDecimal(100));
        bidder1.setDescription("ABD Company");
        bidder1.setTitle("ABD");
        bidder1.setHyperLink("www.abd.com");

        Slot slot1 = new Slot();
        slot1.setPrice(new BigDecimal(10));
        slot1.setIdentifier(2);
        slot1.setBidder(bidder1);

        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        slots.add(slot1);
        auction.setSlots(slots);

        when(auctionRepository.getAuctionByKeyword(anyString())).thenReturn(auction);

        Auction auction1 =  auctionService.placeBid("car insurance",new BigDecimal(60), bidder);

        Assertions.assertNotNull(auction1);
        Assertions.assertEquals(new BigDecimal(60), auction1.getSlots().get(0).getPrice());


    }
    @Test
    void testExpectedExceptionPlaceBid() {
        assertThrows(NotFoundAuctionException.class, () -> auctionService.placeBid("flowers",new BigDecimal(60), new Bidder()));
    }


}