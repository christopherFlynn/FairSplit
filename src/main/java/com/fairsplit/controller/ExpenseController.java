package com.fairsplit.controller;

import com.fairsplit.dto.CreateExpenseRequest;
import com.fairsplit.dto.ExpenseResponse;
import com.fairsplit.dto.ExpenseSummary;
import com.fairsplit.entity.User;
import com.fairsplit.service.ExpenseService;
import com.fairsplit.util.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid CreateExpenseRequest request) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(expenseService.createExpense(request, currentUser));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseSummary>> getGroupExpenses(@PathVariable UUID groupId) {
        User currentUser = AuthUtil.getCurrentUser();
        return ResponseEntity.ok(expenseService.getExpensesByGroup(groupId, currentUser));
    }

}
