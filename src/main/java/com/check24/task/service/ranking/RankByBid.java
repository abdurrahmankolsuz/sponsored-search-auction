package com.check24.task.service.ranking;

import com.check24.task.model.Slot;

import java.util.Collections;
import java.util.List;

public class RankByBid implements RankingStrategy {
    @Override
    public void sort(List<Slot> input) {
        Collections.sort(input, new BidComparator().reversed());
    }
}
