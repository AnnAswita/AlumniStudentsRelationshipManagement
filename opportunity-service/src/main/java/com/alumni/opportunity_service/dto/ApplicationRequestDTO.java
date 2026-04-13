package com.alumni.opportunity_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ApplicationRequestDTO {
    private UUID studentId;
    private UUID opportunityId;
}

