package com.fairsplit.service;

import com.fairsplit.dto.CreateExpenseRequest;
import com.fairsplit.dto.ExpenseResponse;
import com.fairsplit.dto.ExpenseSummary;
import com.fairsplit.entity.*;
import com.fairsplit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository splitRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ExpenseResponse createExpense(CreateExpenseRequest request, User paidBy) {
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        Expense expense = Expense.builder()
                .group(group)
                .title(request.getTitle())
                .amount(request.getAmount())
                .paidBy(paidBy)
                .createdAt(Instant.now())
                .build();

        Expense saved = expenseRepository.save(expense);

        List<ExpenseSplit> splits = request.getSplits().stream().map(s -> {
            User user = userRepository.findById(s.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user in split"));

            return ExpenseSplit.builder()
                    .expense(saved)
                    .user(user)
                    .amount(s.getAmount())
                    .build();
        }).collect(toList());

        splitRepository.saveAll(splits);

        return ExpenseResponse.builder()
                .expenseId(saved.getId())
                .title(saved.getTitle())
                .amount(saved.getAmount())
                .paidBy(paidBy.getId())
                .splits(splits.stream().map(s ->
                        ExpenseResponse.Split.builder()
                                .userId(s.getUser().getId())
                                .amount(s.getAmount())
                                .build()
                ).collect(toList()))
                .build();
    }

    public List<ExpenseSummary> getExpensesByGroup(UUID groupId, User requester) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        // Optional: check if user is in the group
        // (could add a check with GroupMembershipRepository if needed)

        List<Expense> expenses = expenseRepository.findAllByGroup(group);

        return expenses.stream()
                .map(e -> ExpenseSummary.builder()
                        .id(e.getId())
                        .title(e.getTitle())
                        .amount(e.getAmount())
                        .paidBy(e.getPaidBy().getId())
                        .createdAt(e.getCreatedAt())
                        .build())
                .toList();
    }

}
