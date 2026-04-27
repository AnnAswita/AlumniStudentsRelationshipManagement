package com.alumni.opportunity_service.service.Implementation;

import com.alumni.opportunity_service.dto.UserDTO;
import com.alumni.opportunity_service.entity.*;
import com.alumni.opportunity_service.enums.ApplicationStatus;
import com.alumni.opportunity_service.repository.*;
import com.alumni.opportunity_service.service.ApplicationService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repository;
    private final OpportunityRepository opportunityRepository;

    public ApplicationServiceImpl(ApplicationRepository repository,
                                  OpportunityRepository opportunityRepository) {
        this.repository = repository;
        this.opportunityRepository = opportunityRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    private String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private UserDTO getUserById(Long userId, HttpServletRequest request) {

        String token = getToken(request);

        if (token == null) {
            throw new RuntimeException("Missing Authorization header");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String url = "http://user-service/users/" + userId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserDTO.class
        ).getBody();
    }


    @Override
    public Application apply(Long studentId, Long opportunityId, HttpServletRequest request) {

        UserDTO student = getUserById(studentId, request);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        if (!"STUDENT".equalsIgnoreCase(student.getRole())) {
            throw new RuntimeException("Only STUDENTS can apply");
        }

        opportunityRepository.findById(opportunityId)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        Application app = new Application();
        app.setStudentId(studentId);
        app.setOpportunityId(opportunityId);
        app.setStatus(ApplicationStatus.APPLIED);

        return repository.save(app);
    }


    @Override
    public Application updateStatus(Long applicationId, String status, HttpServletRequest request) {

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
        return repository.findByOpportunityId(opportunityId);
    }
}