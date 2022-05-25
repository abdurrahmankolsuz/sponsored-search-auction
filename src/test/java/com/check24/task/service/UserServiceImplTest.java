package com.check24.task.service;

import com.check24.task.exception.OutOfBudgetException;
import com.check24.task.model.Auction;
import com.check24.task.model.Bidder;
import com.check24.task.model.Slot;
import com.check24.task.repository.AuctionRepository;
import com.check24.task.service.pricing.GeneralizedFirstPrice;
import com.check24.task.service.ranking.RankByBid;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

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
    void clickOnSlot() {
        Auction auction = new Auction("car insurance");
        auction.setPricingStrategy(new GeneralizedFirstPrice());

        Bidder bidder = new Bidder();
        bidder.setBudget(new BigDecimal(100));
        bidder.setDescription("ABC Company");
        bidder.setTitle("ABC");
        bidder.setHyperLink("www.abc.com");


        Slot slot = new Slot();
        slot.setPrice(new BigDecimal(10));
        slot.setIdentifier(1);
        slot.setBidder(bidder);


        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        auction.setSlots(slots);

        when(auctionRepository.getAuctionByKeyword(anyString())).thenReturn(auction);

        Auction auction1 = userService.clickOnSlot("car insurance", 1);

        Assertions.assertNotNull(auction1);
        Assertions.assertEquals(new BigDecimal(90),
                auction1.getSlots().stream().filter(a -> a.getIdentifier() == 1).findFirst().get().getBidder().getBudget());
    }

    @Test
    void testExpectedExceptionClickOnSlot() {
        Auction auction = new Auction("car insurance");
        auction.setPricingStrategy(new GeneralizedFirstPrice());

        Bidder bidder = new Bidder();
        bidder.setBudget(new BigDecimal(50));
        bidder.setDescription("ABC Company");
        bidder.setTitle("ABC");
        bidder.setHyperLink("www.abc.com");

        Slot slot = new Slot();
        slot.setPrice(new BigDecimal(60));
        slot.setIdentifier(1);
        slot.setBidder(bidder);

        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        auction.setSlots(slots);

        when(auctionRepository.getAuctionByKeyword(anyString())).thenReturn(auction);
        assertThrows(OutOfBudgetException.class, () -> userService.clickOnSlot("car insurance", 1));
    }

    @Test
    void searchKeyword() {
        Auction auction = new Auction("car insurance");

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
        slot1.setPrice(new BigDecimal(50));
        slot1.setIdentifier(2);
        slot1.setBidder(bidder1);

        Bidder bidder2 = new Bidder();
        bidder2.setBudget(new BigDecimal(100));
        bidder2.setDescription("BBC Company");
        bidder2.setTitle("BBC");
        bidder2.setHyperLink("www.bbc.com");


        Slot slot2 = new Slot();
        slot2.setPrice(new BigDecimal(30));
        slot2.setIdentifier(3);
        slot2.setBidder(bidder2);

        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        slots.add(slot1);
        slots.add(slot2);
        auction.setSlots(slots);

        when(auctionRepository.getSlotsByKeyword(anyString())).thenReturn(auction.getSlots());

        List<Slot> slotList = userService.searchKeyword("car insurance",10);

        Assertions.assertNotNull(slotList);
        Assertions.assertArrayEquals(auction.getSlots().toArray(new Slot[0]), slotList.toArray());

    }

    @Test
    void searchKeyword_rankByBid_Test() {
        Auction auction = new Auction("car insurance");
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
        slot1.setPrice(new BigDecimal(50));
        slot1.setIdentifier(2);
        slot1.setBidder(bidder1);

        Bidder bidder2 = new Bidder();
        bidder2.setBudget(new BigDecimal(100));
        bidder2.setDescription("BBC Company");
        bidder2.setTitle("BBC");
        bidder2.setHyperLink("www.bbc.com");


        Slot slot2 = new Slot();
        slot2.setPrice(new BigDecimal(30));
        slot2.setIdentifier(3);
        slot2.setBidder(bidder2);

        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        slots.add(slot1);
        slots.add(slot2);
        auction.setSlots(slots);
        auction.sort();


        when(auctionRepository.getSlotsByKeyword(anyString())).thenReturn(auction.getSlots());

        List<Slot> slotList = userService.searchKeyword("car insurance",10);

        Assertions.assertEquals("www.abd.com", slotList.get(0).getBidder().getHyperLink());


    }

    @Test
    void searchKeyword_fixedNumberSlot_Test() {
        Auction auction = new Auction("car insurance");
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
        slot1.setPrice(new BigDecimal(50));
        slot1.setIdentifier(2);
        slot1.setBidder(bidder1);

        Bidder bidder2 = new Bidder();
        bidder2.setBudget(new BigDecimal(100));
        bidder2.setDescription("BBC Company");
        bidder2.setTitle("BBC");
        bidder2.setHyperLink("www.bbc.com");


        Slot slot2 = new Slot();
        slot2.setPrice(new BigDecimal(30));
        slot2.setIdentifier(3);
        slot2.setBidder(bidder2);

        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        slots.add(slot1);
        slots.add(slot2);
        auction.setSlots(slots);
        auction.sort();


        when(auctionRepository.getSlotsByKeyword(anyString())).thenReturn(auction.getSlots());

        List<Slot> slotList = userService.searchKeyword("car insurance",2);

        Assertions.assertEquals(2, slotList.size());


    }
}