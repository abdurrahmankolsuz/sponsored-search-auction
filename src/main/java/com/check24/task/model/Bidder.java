package com.check24.task.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Bidder {
    private String title;
    private String description;
    private String hyperLink;
    private BigDecimal budget;
    private Integer score;

}
