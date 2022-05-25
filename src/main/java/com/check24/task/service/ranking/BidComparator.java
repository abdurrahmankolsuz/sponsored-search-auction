package com.check24.task.service.ranking;

import com.check24.task.model.Slot;

import java.util.Comparator;

public class BidComparator implements Comparator<Slot> {
    @Override
    public int compare(Slot o1, Slot o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}
