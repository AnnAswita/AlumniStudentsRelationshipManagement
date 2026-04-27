package com.alumni.opportunity_service.service;

import com.alumni.opportunity_service.entity.Application;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface ApplicationService {

    Application apply(Long studentId, Long opportunityId, HttpServletRequest request);

    Application updateStatus(Long applicationId, String status, HttpServletRequest request);

    List<Application> getByStudent(Long studentId);

    List<Application> getByOpportunity(Long opportunityId);
}

