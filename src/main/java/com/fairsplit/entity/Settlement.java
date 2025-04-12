package com.fairsplit.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "settlements")
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}
