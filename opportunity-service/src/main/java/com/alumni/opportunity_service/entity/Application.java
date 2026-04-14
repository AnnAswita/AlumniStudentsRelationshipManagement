package com.alumni.opportunity_service.entity;

import com.alumni.opportunity_service.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue
    private UUID applicationId;

    private LocalDateTime appliedDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "opportunity_id")
    private Opportunity opportunity;

    @PrePersist
    public void prePersist() {
        this.appliedDate = LocalDateTime.now();
        this.status = ApplicationStatus.APPLIED;
    }
}

