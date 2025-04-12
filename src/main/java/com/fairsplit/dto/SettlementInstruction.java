package com.fairsplit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class SettlementInstruction {
    private UUID id;
    private UUID from;
    private UUID to;
    private BigDecimal amount;
    private boolean paid;
}

