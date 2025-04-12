package com.fairsplit.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class ExpenseSummary {
    private UUID id;
    private String title;
    private BigDecimal amount;
    private UUID paidBy;
    private Instant createdAt;
}
