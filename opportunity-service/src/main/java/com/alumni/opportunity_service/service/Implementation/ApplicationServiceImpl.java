package com.alumni.opportunity_service.service.Implementation;

import com.alumni.opportunity_service.dto.UserDTO;
import com.alumni.opportunity_service.entity.*;
import com.alumni.opportunity_service.enums.ApplicationStatus;
import com.alumni.opportunity_service.enums.UserRole;
import com.alumni.opportunity_service.repository.*;
import com.alumni.opportunity_service.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repository;
    private final UserRepository userRepository;
    private final OpportunityRepository opportunityRepository;
    private final RestTemplate restTemplate;

    public ApplicationServiceImpl(ApplicationRepository repository,
                                  UserRepository userRepository,
                                  OpportunityRepository opportunityRepository,
                                  RestTemplate restTemplate) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.opportunityRepository = opportunityRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Application apply(Long studentId, Long opportunityId) {

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
    public Application updateStatus(Long applicationId, String status) {

        Application app = repository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        app.setStatus(ApplicationStatus.valueOf(status));
        return repository.save(app);
    }


    @Override
    public List<Application> getByStudent(Long studentId) {
        return repository.findByStudentId(studentId);
    }

    @Override
    public List<Application> getByOpportunity(Long opportunityId) {
        return repository.findByOpportunityOpportunityId(opportunityId);
    }
    
     public UserDTO getUserFromUserService(Long userId) {
        String url = "http://user-service/users/" + userId;
        return restTemplate.getForObject(url, UserDTO.class);
    }
}