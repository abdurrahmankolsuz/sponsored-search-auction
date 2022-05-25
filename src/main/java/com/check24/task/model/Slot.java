package com.check24.task.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Slot {
    private Integer identifier;
    private BigDecimal price;
    private Bidder bidder;

}
