package com.fairsplit.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GroupMemberResponse {
    private UUID id;
    private String name;
    private String email;
}
