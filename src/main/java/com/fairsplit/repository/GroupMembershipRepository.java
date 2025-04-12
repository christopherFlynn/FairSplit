package com.fairsplit.repository;

import com.fairsplit.entity.Group;
import com.fairsplit.entity.GroupMembership;
import com.fairsplit.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, UUID> {

    boolean existsByUserAndGroup(User user, Group group);

    List<GroupMembership> findByGroup(Group group);

    List<GroupMembership> findByUser(User user);

    
}
