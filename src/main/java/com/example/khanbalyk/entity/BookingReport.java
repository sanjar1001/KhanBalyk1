package com.example.khanbalyk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingReport {

    private BigDecimal totalAmount;
    private int totalFishermenAmount;
    private int totalVisitorAmount;
    private int totalPensioInvalidAmount;




}

