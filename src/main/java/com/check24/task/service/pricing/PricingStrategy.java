package com.check24.task.service.pricing;

import com.check24.task.model.Slot;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal pricing(Slot slot);
}
