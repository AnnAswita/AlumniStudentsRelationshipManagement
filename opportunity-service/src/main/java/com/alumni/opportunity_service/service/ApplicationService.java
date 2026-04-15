package com.alumni.opportunity_service.service;

import com.alumni.opportunity_service.entity.Application;

import java.util.List;


public interface ApplicationService {

    Application apply(Long studentId, Long opportunityId);

    Application updateStatus(Long applicationId, String status);

    List<Application> getByStudent(Long studentId);

    List<Application> getByOpportunity(Long opportunityId);
}

