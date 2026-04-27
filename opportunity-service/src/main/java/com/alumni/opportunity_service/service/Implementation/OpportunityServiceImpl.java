package com.alumni.opportunity_service.service.Implementation;
import com.alumni.opportunity_service.dto.UserDTO;
import com.alumni.opportunity_service.entity.Opportunity;
import com.alumni.opportunity_service.repository.OpportunityRepository;
import com.alumni.opportunity_service.service.OpportunityService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityRepository repository;

    public OpportunityServiceImpl(OpportunityRepository repository
                                ) {
        this.repository = repository;

    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletRequest request;

    private UserDTO getUserById(Long userId) {

        // STEP 1: GET TOKEN FROM INCOMING REQUEST
        System.out.println("AUTH HEADER = " + request.getHeader("Authorization"));
        String token = request.getHeader("Authorization");

        // STEP 2: CREATE HEADERS
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // STEP 3: CALL USER SERVICE WITH TOKEN
        String url = "http://user-service/users/" + userId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                UserDTO.class
        ).getBody();
    }
    @Override
    public Opportunity create(Opportunity opportunity, Long userId) {

        UserDTO user = getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!"ALUMNI".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only ALUMNI can post opportunities");
        }


        opportunity.setAlumniId(userId);

        return repository.save(opportunity);
    }

    @Override
    public Opportunity update(Long id, Long userId, Opportunity updatedOpportunity) {

        UserDTO user = getUserById(userId);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!"ALUMNI".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only ALUMNI can update opportunities");
        }

        Opportunity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        if (updatedOpportunity.getTitle() != null)
        existing.setTitle(updatedOpportunity.getTitle());

        if (updatedOpportunity.getDescription() != null)
            existing.setDescription(updatedOpportunity.getDescription());

        if (updatedOpportunity.getType() != null)
            existing.setType(updatedOpportunity.getType());

        if (updatedOpportunity.getDeadline() != null)
            existing.setDeadline(updatedOpportunity.getDeadline());

        return repository.save(existing);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Opportunity> getAll() {
        return repository.findAll();
    }

    @Override
    public Opportunity getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
    }
}
