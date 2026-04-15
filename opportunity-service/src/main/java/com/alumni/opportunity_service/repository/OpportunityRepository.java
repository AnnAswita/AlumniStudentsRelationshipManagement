package com.alumni.opportunity_service.repository;

import com.alumni.opportunity_service.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
}
