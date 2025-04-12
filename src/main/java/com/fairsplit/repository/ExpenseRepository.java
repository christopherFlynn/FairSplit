package com.fairsplit.repository;

import com.fairsplit.entity.Expense;
import com.fairsplit.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    List<Expense> findAllByGroup(Group group);
}
