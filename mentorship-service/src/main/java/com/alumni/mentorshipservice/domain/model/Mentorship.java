package com.alumni.mentorshipservice.domain.model;

import com.alumni.mentorshipservice.domain.valueobject.MentorshipStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mentorship {

    @Id
    @GeneratedValue
    private Long id;

    private Long studentId;
    private Long alumniId;

    @Enumerated(EnumType.STRING)
    private MentorshipStatus status;

    private LocalDate requestDate;
    private LocalDate startDate;
    private LocalDate endDate;
}
