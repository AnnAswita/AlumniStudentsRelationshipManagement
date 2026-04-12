package com.alumni.mentorshipservice.domain.model;
import jakarta.persistence.*;
@Entity
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;

    private Long mentorshipId;
    private String description;
    private String date;
}
