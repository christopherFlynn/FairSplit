package com.fairsplit.repository;

import com.fairsplit.entity.ExpenseSplit;
import com.fairsplit.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, UUID> {

    List<ExpenseSplit> findAllByExpense_Group(Group group);
}
