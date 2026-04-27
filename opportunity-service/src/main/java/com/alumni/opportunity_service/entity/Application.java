package com.alumni.opportunity_service.entity;

import com.alumni.opportunity_service.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Application {

    @Id
    @GeneratedValue
    private Long applicationId;

    private LocalDateTime appliedDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private Long studentId; 


    private Long opportunityId;

    @PrePersist
    public void prePersist() {
        this.appliedDate = LocalDateTime.now();
        this.status = ApplicationStatus.APPLIED;
    }
}

