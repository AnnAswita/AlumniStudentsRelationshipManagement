package com.alumni.opportunity_service.service;

import com.alumni.opportunity_service.entity.Opportunity;

import java.util.List;


public interface OpportunityService {

    Opportunity create(Opportunity opportunity, Long userId);

    Opportunity update(Long id, Long userId, Opportunity updatedOpportunity);

    void delete(Long id);

    List<Opportunity> getAll();

    Opportunity getById(Long id);
}

