package com.check24.task.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Bid {
    private Integer id;
    private Bidder bidder;
    private BigDecimal bidPrice;
}
