package com.fairsplit.service;

import com.fairsplit.dto.CreateGroupRequest;
import com.fairsplit.dto.GroupBalanceResponse;
import com.fairsplit.dto.GroupMemberResponse;
import com.fairsplit.dto.GroupResponse;
import com.fairsplit.dto.JoinGroupRequest;
import com.fairsplit.dto.SettlementInstruction;
import com.fairsplit.entity.Expense;
import com.fairsplit.entity.ExpenseSplit;
import com.fairsplit.entity.Group;
import com.fairsplit.entity.GroupMembership;
import com.fairsplit.entity.User;
import com.fairsplit.repository.GroupMembershipRepository;
import com.fairsplit.repository.GroupRepository;
import com.fairsplit.repository.ExpenseRepository;
import com.fairsplit.repository.ExpenseSplitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMembershipRepository membershipRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository splitRepository;

    public GroupResponse createGroup(CreateGroupRequest request, User user) {
        String inviteCode = generateInviteCode();

        Group group = Group.builder()
                .name(request.getName())
                .inviteCode(inviteCode)
                .createdBy(user)
                .createdAt(Instant.now())
                .build();

        Group saved = groupRepository.save(group);

        // Auto-add creator to group
        GroupMembership membership = GroupMembership.builder()
                .user(user)
                .group(saved)
                .joinedAt(Instant.now())
                .build();
        membershipRepository.save(membership);

        return GroupResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .inviteCode(saved.getInviteCode())
                .build();
    }

    private String generateInviteCode() {
        // Can be smarter later
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public GroupResponse joinGroup(JoinGroupRequest request, User user) {
        Group group = groupRepository.findByInviteCode(request.getInviteCode())
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite code"));

        // Prevent duplicate joins
        boolean alreadyJoined = membershipRepository.existsByUserAndGroup(user, group);
        if (alreadyJoined) {
            throw new IllegalStateException("User is already a member of this group");
        }

        GroupMembership membership = GroupMembership.builder()
                .user(user)
                .group(group)
                .joinedAt(Instant.now())
                .build();

        membershipRepository.save(membership);

        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .inviteCode(group.getInviteCode())
                .build();
    }

    public List<GroupMemberResponse> getGroupMembers(UUID groupId, User requester) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Optional: enforce that requester is a member of the group

        List<GroupMembership> memberships = membershipRepository.findByGroup(group);

        return memberships.stream()
                .map(m -> {
                    User user = m.getUser();
                    return GroupMemberResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .build();
                })
                .toList();
    }

    public List<GroupBalanceResponse> getGroupBalances(UUID groupId, User requester) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        List<GroupMembership> memberships = membershipRepository.findByGroup(group);

        // Initialize everyone’s balance at 0
        Map<UUID, GroupBalanceResponse.GroupBalanceResponseBuilder> balanceMap = new HashMap<>();
        for (GroupMembership membership : memberships) {
            User user = membership.getUser();
            balanceMap.put(user.getId(), GroupBalanceResponse.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .balance(BigDecimal.ZERO));
        }

        // Go through all expenses in the group
        List<Expense> expenses = expenseRepository.findAllByGroup(group);
        for (Expense expense : expenses) {
            UUID payerId = expense.getPaidBy().getId();
            BigDecimal paidAmount = expense.getAmount();

            // Add to payer’s positive balance
            GroupBalanceResponse.GroupBalanceResponseBuilder payerBalance = balanceMap.get(payerId);
            payerBalance.balance(payerBalance.build().getBalance().add(paidAmount));
        }

        // Go through all splits and subtract owed amounts
        List<ExpenseSplit> splits = splitRepository.findAllByExpense_Group(group);
        for (ExpenseSplit split : splits) {
            UUID userId = split.getUser().getId();
            BigDecimal owedAmount = split.getAmount();

            GroupBalanceResponse.GroupBalanceResponseBuilder userBalance = balanceMap.get(userId);
            userBalance.balance(userBalance.build().getBalance().subtract(owedAmount));
        }

        // Finalize and return
        return balanceMap.values().stream()
                .map(GroupBalanceResponse.GroupBalanceResponseBuilder::build)
                .toList();
    }

    public List<SettlementInstruction> calculateSettlements(UUID groupId, User requester) {
        List<GroupBalanceResponse> balances = getGroupBalances(groupId, requester);

        // Separate creditors and debtors
        List<GroupBalanceResponse> creditors = new ArrayList<>();
        List<GroupBalanceResponse> debtors = new ArrayList<>();

        for (GroupBalanceResponse b : balances) {
            if (b.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                creditors.add(b);
            } else if (b.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                debtors.add(b);
            }
        }

        List<SettlementInstruction> instructions = new ArrayList<>();

        // Sort largest creditor first, largest debtor first
        creditors.sort((a, b) -> b.getBalance().compareTo(a.getBalance()));
        debtors.sort((a, b) -> a.getBalance().compareTo(b.getBalance())); // more negative first

        int i = 0, j = 0;
        while (i < debtors.size() && j < creditors.size()) {
            GroupBalanceResponse debtor = debtors.get(i);
            GroupBalanceResponse creditor = creditors.get(j);

            BigDecimal debt = debtor.getBalance().abs();
            BigDecimal credit = creditor.getBalance();

            BigDecimal min = debt.min(credit);

            if (min.compareTo(BigDecimal.ZERO) > 0) {
                instructions.add(SettlementInstruction.builder()
                        .from(debtor.getUserId())
                        .to(creditor.getUserId())
                        .amount(min)
                        .build());

                // Update balances
                debtor.setBalance(debtor.getBalance().add(min));
                creditor.setBalance(creditor.getBalance().subtract(min));
            }

            if (debtor.getBalance().compareTo(BigDecimal.ZERO) == 0) i++;
            if (creditor.getBalance().compareTo(BigDecimal.ZERO) == 0) j++;
        }

        return instructions;
    }

    public List<GroupResponse> getGroupsForUser(User user) {
        List<GroupMembership> memberships = membershipRepository.findByUser(user);

        return memberships.stream()
                .map(m -> {
                    Group g = m.getGroup();
                    return GroupResponse.builder()
                            .id(g.getId())
                            .name(g.getName())
                            .inviteCode(g.getInviteCode())
                            .build();
                })
                .toList();
    }

    public GroupResponse getGroupById(UUID groupId, User user) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Optional: verify user is in the group

        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .inviteCode(group.getInviteCode())
                .build();
    }

}
