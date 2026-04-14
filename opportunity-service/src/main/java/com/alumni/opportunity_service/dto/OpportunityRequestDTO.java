package com.alumni.opportunity_service.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OpportunityRequestDTO {

    private UUID userId;   // alumni id

    private String title;
    private String description;
    private String type;
    private LocalDateTime deadline;
}

