package com.fairsplit.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExpenseResponse {
    private UUID expenseId;
    private String title;
    private BigDecimal amount;
    private UUID paidBy;
    private List<Split> splits;

    @Data
    @Builder
    public static class Split {
        private UUID userId;
        private BigDecimal amount;
    }
}
