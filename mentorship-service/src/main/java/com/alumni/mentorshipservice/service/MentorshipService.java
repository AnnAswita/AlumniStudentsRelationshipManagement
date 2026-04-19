package com.alumni.mentorshipservice.service;

import com.alumni.mentorshipservice.config.RoleValidator;
import com.alumni.mentorshipservice.domain.model.Mentorship;
import com.alumni.mentorshipservice.domain.valueobject.MentorshipStatus;
import com.alumni.mentorshipservice.dto.*;
import com.alumni.mentorshipservice.repository.MentorshipRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class MentorshipService {

    private final MentorshipRepository repo;
    private final RestTemplate restTemplate;
    private final RoleValidator roleValidator;
    private final HttpServletRequest request;

    public MentorshipService(MentorshipRepository repo, RestTemplate restTemplate, RoleValidator roleValidator, HttpServletRequest request) {
        this.repo = repo;
        this.restTemplate = restTemplate;
        this.roleValidator = roleValidator;
        this.request = request;
    }
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUser")
    public void validateUser(Long userId) {
        String token = request.getHeader("Authorization"); //get JWT from client request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); //forward JWT to user-service

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = "http://user-service/users/" + userId;
        restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);
    }

    public void fallbackUser(Long userId, Throwable ex) {
        throw new RuntimeException("User Service unavailable. Try later.");
    }

    public UserDTO getUserFromUserService(Long userId) {
        String token = request.getHeader("Authorization"); //get JWT from client request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); //forward JWT to user-service

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = "http://user-service/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, entity, UserDTO.class).getBody();
    }
    public MentorshipResponseDTO request(MentorshipRequestDTO dto) {
        roleValidator.allowOnlyStudent(request);

        validateUser(dto.getStudentId());
        validateUser(dto.getAlumniId());

        Mentorship m = MentorshipMapper.toEntity(dto);
        m.setStatus(MentorshipStatus.REQUESTED);
        m.setRequestDate(LocalDate.now());

        return MentorshipMapper.toDTO(repo.save(m));
    }

    public MentorshipResponseDTO reject(Long id){
        roleValidator.allowOnlyAlumni(request);

        Mentorship m = repo.findById(id).orElseThrow();
        if(m.getStatus() != MentorshipStatus.REQUESTED) {
            throw new RuntimeException("Invalid state transition: Cannot reject mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.REJECTED);
        m.setEndDate(LocalDate.now());
        return MentorshipMapper.toDTO(repo.save(m));
    }

    public MentorshipResponseDTO accept(Long id) {
        roleValidator.allowOnlyAlumni(request);
        Mentorship m = repo.findById(id).orElseThrow();

        if (m.getStatus() != MentorshipStatus.REQUESTED) {
            throw new RuntimeException("Invalid state transition: Cannot accept mentorship in state "+m.getStatus());
        }

        m.setStatus(MentorshipStatus.ACCEPTED);
        m.setStartDate(LocalDate.now());

        return MentorshipMapper.toDTO(repo.save(m));
    }

    public MentorshipResponseDTO activate(Long id){
        Mentorship m = repo.findById(id).orElseThrow();

        if (m.getStatus() != MentorshipStatus.ACCEPTED) {
            throw new RuntimeException("Invalid state transition: Cannot activate mentorship in state "+m.getStatus());
        }

        m.setStatus(MentorshipStatus.ACTIVE);

        return MentorshipMapper.toDTO(repo.save(m));
    }

    public MentorshipResponseDTO complete(Long id) {
        Mentorship m = repo.findById(id)
                .orElseThrow();
        if ((m.getStatus() != MentorshipStatus.ACTIVE) && (m.getStatus() !=MentorshipStatus.ACCEPTED)) {
            throw new RuntimeException("Invalid state transition: cannot complete mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.COMPLETED);
        m.setEndDate(LocalDate.now());

        return MentorshipMapper.toDTO(repo.save(m));
    }
    public MentorshipResponseDTO cancel(Long id) {
        Mentorship m = repo.findById(id)
                .orElseThrow();
        if ((m.getStatus() != MentorshipStatus.REQUESTED) && (m.getStatus() != MentorshipStatus.ACTIVE) && (m.getStatus() !=MentorshipStatus.ACCEPTED)) {
            throw new RuntimeException("Invalid state transition: cannot cancel mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.CANCELLED);
        m.setEndDate(LocalDate.now());

        return MentorshipMapper.toDTO(repo.save(m));
    }

}
