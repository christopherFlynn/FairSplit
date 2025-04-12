package com.fairsplit.controller;

import com.fairsplit.dto.CreateGroupRequest;
import com.fairsplit.dto.GroupBalanceResponse;
import com.fairsplit.dto.GroupMemberResponse;
import com.fairsplit.dto.GroupResponse;
import com.fairsplit.dto.JoinGroupRequest;
import com.fairsplit.dto.SettlementInstruction;
import com.fairsplit.entity.User;
import com.fairsplit.service.GroupService;
import com.fairsplit.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody @Valid CreateGroupRequest request) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.createGroup(request, currentUser));
    }

    @PostMapping("/join")
    public ResponseEntity<GroupResponse> joinGroup(@RequestBody @Valid JoinGroupRequest request) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.joinGroup(request, currentUser));
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberResponse>> getGroupMembers(@PathVariable UUID groupId) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.getGroupMembers(groupId, currentUser));
    }

    @GetMapping("/{groupId}/balances")
    public ResponseEntity<List<GroupBalanceResponse>> getGroupBalances(@PathVariable UUID groupId) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.getGroupBalances(groupId, currentUser));
    }

    @GetMapping("/{groupId}/settlements")
    public ResponseEntity<List<SettlementInstruction>> getSettlements(@PathVariable UUID groupId) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.calculateSettlements(groupId, currentUser));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getMyGroups() {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.getGroupsForUser(currentUser));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable UUID groupId) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(groupService.getGroupById(groupId, currentUser));
    }

}
