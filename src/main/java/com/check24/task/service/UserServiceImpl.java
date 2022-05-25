package com.check24.task.service;

import com.check24.task.exception.OutOfBudgetException;
import com.check24.task.model.Auction;
import com.check24.task.model.Slot;
import com.check24.task.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final AuctionRepository auctionRepository;

    public UserServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction clickOnSlot(String keyword, Integer identifier) {
        Auction auction = auctionRepository.getAuctionByKeyword(keyword);
        Slot slot = auction.getSlots().stream().filter(x -> x.getIdentifier().equals(identifier)).findFirst().orElseThrow();
        if (slot.getBidder().getBudget().compareTo(auction.pricing(slot)) < 0) {
            throw new OutOfBudgetException("Budget is running out!");
        } else {
            slot.getBidder().setBudget(slot.getBidder().getBudget().subtract(auction.pricing(slot)));
            auctionRepository.addAuction(keyword, auction);
            return auction;
        }
    }

    @Override
    public List<Slot> searchKeyword(String keyword, Integer maxSlotNumber) {
        return auctionRepository.getSlotsByKeyword(keyword).stream().limit(maxSlotNumber).collect(Collectors.toList());
    }
}
