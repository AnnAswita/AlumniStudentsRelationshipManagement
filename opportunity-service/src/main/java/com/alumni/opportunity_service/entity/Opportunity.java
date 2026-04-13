package com.alumni.opportunity_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Opportunity {

    @Id
    @GeneratedValue
    private UUID opportunityId;

    private String title;
    private String description;
    private String type;

    private LocalDateTime deadline;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "alumni_id")
    private User alumni;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

