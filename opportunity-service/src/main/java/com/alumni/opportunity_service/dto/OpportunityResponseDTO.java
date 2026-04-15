package com.alumni.opportunity_service.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class OpportunityResponseDTO {

    private Long opportunityId;
    private String title;
    private String description;
    private String type;
    private LocalDateTime deadline;
    private LocalDateTime createdAt;
}

