package com.alumni.opportunity_service.repository;

import com.alumni.opportunity_service.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findByStudentId(UUID studentId);

    List<Application> findByOpportunityOpportunityId(UUID opportunityId);
}

