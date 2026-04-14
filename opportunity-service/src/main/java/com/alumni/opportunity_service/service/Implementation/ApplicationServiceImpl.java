package com.alumni.opportunity_service.service.Implementation;

import com.alumni.opportunity_service.entity.*;
import com.alumni.opportunity_service.enums.ApplicationStatus;
import com.alumni.opportunity_service.enums.UserRole;
import com.alumni.opportunity_service.repository.*;
import com.alumni.opportunity_service.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repository;
    private final UserRepository userRepository;
    private final OpportunityRepository opportunityRepository;

    public ApplicationServiceImpl(ApplicationRepository repository,
                                  UserRepository userRepository,
                                  OpportunityRepository opportunityRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.opportunityRepository = opportunityRepository;
    }

    @Override
    public Application apply(UUID studentId, UUID opportunityId) {

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        if (student.getRole() != UserRole.STUDENT) {
        throw new RuntimeException("Only STUDENTS can apply for opportunities");
        }

        Opportunity opportunity = opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        Application app = new Application();
        app.setStudent(student);
        app.setOpportunity(opportunity);

        return repository.save(app);
    }

    @Override
    public Application updateStatus(UUID applicationId, String status) {

        Application app = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status));
        return repository.save(app);
    }

    @Override
    public List<Application> getByStudent(UUID studentId) {
        return repository.findByStudentId(studentId);
    }

    @Override
    public List<Application> getByOpportunity(UUID opportunityId) {
        return repository.findByOpportunityOpportunityId(opportunityId);
    }
}