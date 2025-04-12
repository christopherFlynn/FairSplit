package com.fairsplit.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class SettlementInstruction {
    private UUID from;
    private UUID to;
    private BigDecimal amount;
}
