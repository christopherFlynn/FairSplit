package com.fairsplit.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GroupResponse {
    private UUID id;
    private String name;
    private String inviteCode;
}
