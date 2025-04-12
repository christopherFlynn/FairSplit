package com.fairsplit.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class GroupBalanceResponse {
    private UUID userId;
    private String name;
    private BigDecimal balance; // positive = owed, negative = owes
}
