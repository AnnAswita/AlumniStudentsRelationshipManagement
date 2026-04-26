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
import java.util.List;

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
        m=repo.save(m);
        UserDTO student = getUserFromUserService(m.getStudentId());
        UserDTO alumni = getUserFromUserService(m.getAlumniId());
        return MentorshipMapper.toDTO(m,student,alumni);
    }

    public MentorshipResponseDTO reject(Long id){
        roleValidator.allowOnlyAlumni(request);

        Mentorship m = repo.findById(id).orElseThrow();
        if(m.getStatus() != MentorshipStatus.REQUESTED) {
            throw new RuntimeException("Invalid state transition: Cannot reject mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.REJECTED);
        m.setEndDate(LocalDate.now());
        m=repo.save(m);
        UserDTO student = getUserFromUserService(m.getStudentId());
        UserDTO alumni = getUserFromUserService(m.getAlumniId());
        return MentorshipMapper.toDTO(m,student,alumni);
    }

    public MentorshipResponseDTO accept(Long id) {
        roleValidator.allowOnlyAlumni(request);
        Mentorship m = repo.findById(id).orElseThrow();

        if (m.getStatus() != MentorshipStatus.REQUESTED) {
            throw new RuntimeException("Invalid state transition: Cannot accept mentorship in state "+m.getStatus());
        }

        m.setStatus(MentorshipStatus.ACCEPTED);
        m.setStartDate(LocalDate.now());

        m=repo.save(m);
        UserDTO student = getUserFromUserService(m.getStudentId());
        UserDTO alumni = getUserFromUserService(m.getAlumniId());
        return MentorshipMapper.toDTO(m,student,alumni);
    }

    public MentorshipResponseDTO activate(Long id){
        Mentorship m = repo.findById(id).orElseThrow();

        if (m.getStatus() != MentorshipStatus.ACCEPTED) {
            throw new RuntimeException("Invalid state transition: Cannot activate mentorship in state "+m.getStatus());
        }

        m.setStatus(MentorshipStatus.ACTIVE);

        m=repo.save(m);
        UserDTO student = getUserFromUserService(m.getStudentId());
        UserDTO alumni = getUserFromUserService(m.getAlumniId());
        return MentorshipMapper.toDTO(m,student,alumni);
    }

    public MentorshipResponseDTO complete(Long id) {
        Mentorship m = repo.findById(id)
                .orElseThrow();
        if ((m.getStatus() != MentorshipStatus.ACTIVE) && (m.getStatus() !=MentorshipStatus.ACCEPTED)) {
            throw new RuntimeException("Invalid state transition: cannot complete mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.COMPLETED);
        m.setEndDate(LocalDate.now());

        m=repo.save(m);
        UserDTO student = getUserFromUserService(m.getStudentId());
        UserDTO alumni = getUserFromUserService(m.getAlumniId());
        return MentorshipMapper.toDTO(m,student,alumni);
    }
    public MentorshipResponseDTO cancel(Long id) {
        Mentorship m = repo.findById(id)
                .orElseThrow();
        if ((m.getStatus() != MentorshipStatus.REQUESTED) && (m.getStatus() != MentorshipStatus.ACTIVE) && (m.getStatus() !=MentorshipStatus.ACCEPTED)) {
            throw new RuntimeException("Invalid state transition: cannot cancel mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.CANCELLED);
        m.setEndDate(LocalDate.now());

        m=repo.save(m);
        UserDTO student = getUserFromUserService(m.getStudentId());
        UserDTO alumni = getUserFromUserService(m.getAlumniId());
        return MentorshipMapper.toDTO(m,student,alumni);
    }
    public MentorshipResponseDTO getByStudentAndAlumni(Long studentId, Long alumniId) {
        Mentorship mentorship = repo.findByStudentIdAndAlumniId(studentId, alumniId)
                .orElseThrow(() -> new RuntimeException("Mentorship not found"));
        UserDTO student = getUserFromUserService(mentorship.getStudentId());
        UserDTO alumni = getUserFromUserService(mentorship.getAlumniId());

        // Return DTO with names included
        return MentorshipMapper.toDTO(mentorship, student, alumni);
    }
    public List<MentorshipResponseDTO> getByStudent(Long studentId) {
        return repo.findByStudentId(studentId)
                .stream()
                .map(m -> {
                    UserDTO student = getUserFromUserService(m.getStudentId());
                    UserDTO alumni = getUserFromUserService(m.getAlumniId());
                    return MentorshipMapper.toDTO(m, student, alumni);
                })
                .toList();
    }

    public List<MentorshipResponseDTO> getByAlumni(Long alumniId) {
        return repo.findByAlumniId(alumniId)
                .stream()
                .map(m -> {
                    UserDTO student = getUserFromUserService(m.getStudentId());
                    UserDTO alumni = getUserFromUserService(m.getAlumniId());
                    return MentorshipMapper.toDTO(m, student, alumni);
                })
                .toList();
    }


}
