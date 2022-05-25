package com.check24.task.service.pricing;

import com.check24.task.model.Slot;

import java.math.BigDecimal;

public class GeneralizedFirstPrice implements PricingStrategy {

    @Override
    public BigDecimal pricing(Slot slot) {
        return slot.getPrice();
    }
}
