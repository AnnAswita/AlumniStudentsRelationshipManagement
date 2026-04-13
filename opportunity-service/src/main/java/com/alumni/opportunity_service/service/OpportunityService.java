package com.alumni.opportunity_service.service;

import com.alumni.opportunity_service.entity.Opportunity;

import java.util.List;
import java.util.UUID;

public interface OpportunityService {

    Opportunity create(Opportunity opportunity, UUID userId);

    Opportunity update(UUID id, UUID userId, Opportunity updatedOpportunity);

    void delete(UUID id);

    List<Opportunity> getAll();

    Opportunity getById(UUID id);
}

