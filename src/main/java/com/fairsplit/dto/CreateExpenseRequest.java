package com.fairsplit.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CreateExpenseRequest {
    @NotBlank
    private String title;

    @DecimalMin("0.01")
    private BigDecimal amount;

    private UUID groupId;

    @NotEmpty
    private List<Split> splits;

    @Data
    public static class Split {
        private UUID userId;
        private BigDecimal amount;
    }
}
