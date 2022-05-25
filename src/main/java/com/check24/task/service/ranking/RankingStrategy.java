package com.check24.task.service.ranking;

import com.check24.task.model.Slot;

import java.util.List;

public interface RankingStrategy {
    void sort(List<Slot> input);
}
