package com.check24.task.model;

import com.check24.task.service.pricing.PricingStrategy;
import com.check24.task.service.ranking.RankingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Auction {
    private String keyword;
    private RankingStrategy rankingStrategy;
    private PricingStrategy pricingStrategy;
    private List<Slot> slots;
    private Integer fixedSlotSize;

    public Auction(String keyword) {
        this.keyword = keyword;
        this.slots = new ArrayList<>();
    }

    public void sort() {
        rankingStrategy.sort(this.slots);
    }

    public BigDecimal pricing(Slot slot) {
        return pricingStrategy.pricing(slot);
    }

}
