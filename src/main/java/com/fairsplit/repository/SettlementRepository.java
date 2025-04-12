package com.fairsplit.repository;

import com.fairsplit.entity.Settlement;
import com.fairsplit.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SettlementRepository extends JpaRepository<Settlement, UUID> {
    @Query("""
        SELECT s FROM Settlement s
        JOIN FETCH s.fromUser
        JOIN FETCH s.toUser
        WHERE s.group = :group AND s.isPaid = true
    """)
    List<Settlement> findPaidWithUsersByGroup(@Param("group") Group group);

    List<Settlement> findByGroup(Group group);
    boolean existsByGroupAndFromUser_IdAndToUser_IdAndIsPaidTrue(Group group, UUID fromUserId, UUID toUserId);
}
