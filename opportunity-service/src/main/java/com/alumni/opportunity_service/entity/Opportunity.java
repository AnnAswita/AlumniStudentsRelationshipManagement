package com.alumni.opportunity_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
public class Opportunity {

    @Id
    @GeneratedValue
    private Long opportunityId;

    private String title;
    private String description;
    private String type;

    private LocalDateTime deadline;
    private LocalDateTime createdAt;



    private Long alumniId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

