package com.alumni.opportunity_service.dto;

import lombok.Data;
import java.time.LocalDateTime;


@Data
public class OpportunityRequestDTO {

    private Long userId;   // alumni id

    private String title;
    private String description;
    private String type;
    private LocalDateTime deadline;
}

