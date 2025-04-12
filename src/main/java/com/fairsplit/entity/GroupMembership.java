package com.fairsplit.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "group_memberships", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "group_id"})
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GroupMembership {

    @Id
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;
}
