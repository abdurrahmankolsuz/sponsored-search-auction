package com.check24.task.service.ranking;

import com.check24.task.model.Slot;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Slot> {
    @Override
    public int compare(Slot o1, Slot o2) {
        return o1.getBidder().getScore().compareTo(o2.getBidder().getScore());
    }
}
