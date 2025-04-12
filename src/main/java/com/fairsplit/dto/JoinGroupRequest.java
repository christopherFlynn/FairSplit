package com.fairsplit.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JoinGroupRequest {
    @NotBlank(message = "Invite code is required")
    private String inviteCode;
}
