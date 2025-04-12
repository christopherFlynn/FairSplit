package com.fairsplit.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MarkPaidRequest {
    private UUID from;
    private UUID to;
}
