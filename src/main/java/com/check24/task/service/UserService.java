package com.check24.task.service;

import com.check24.task.model.Auction;
import com.check24.task.model.Slot;

import java.util.List;

public interface UserService {
    Auction clickOnSlot(String keyword, Integer identifier);

    List<Slot> searchKeyword(String keyword, Integer maxSlotNumber);
}
