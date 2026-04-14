package com.alumni.opportunity_service.service;

import com.alumni.opportunity_service.entity.Application;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    Application apply(UUID studentId, UUID opportunityId);

    Application updateStatus(UUID applicationId, String status);

    List<Application> getByStudent(UUID studentId);

    List<Application> getByOpportunity(UUID opportunityId);
}

