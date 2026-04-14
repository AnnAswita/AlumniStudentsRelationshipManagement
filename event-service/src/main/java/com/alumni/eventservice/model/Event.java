package com.alumni.eventservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Column(length = 1000)
    private String description;

    private LocalDateTime dateTime;

    private String location;

    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private UUID createdBy; // Alumni ID
}
