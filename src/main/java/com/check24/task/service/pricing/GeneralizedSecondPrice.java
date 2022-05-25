package com.check24.task.service.pricing;

import com.check24.task.model.Slot;

import java.math.BigDecimal;

public class GeneralizedSecondPrice implements PricingStrategy {

    @Override
    public BigDecimal pricing(Slot slot) {
        return new BigDecimal(0);
    }
}
