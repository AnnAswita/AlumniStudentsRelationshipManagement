package com.alumni.mentorshipservice.service;

import com.alumni.mentorshipservice.domain.model.Mentorship;
import com.alumni.mentorshipservice.domain.valueobject.MentorshipStatus;
import com.alumni.mentorshipservice.dto.*;
import com.alumni.mentorshipservice.repository.MentorshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class MentorshipService {

    private final MentorshipRepository repo;
    private final RestTemplate restTemplate;

    public MentorshipService(MentorshipRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }
    public UserDTO getUserFromUserService(Long userId) {
        String url = "http://user-service/users/" + userId;
        return restTemplate.getForObject(url, UserDTO.class);
    }
    public MentorshipResponseDTO request(MentorshipRequestDTO dto) {
        Mentorship m = MentorshipMapper.toEntity(dto);
        m.setStatus(MentorshipStatus.REQUESTED);
        m.setRequestDate(LocalDate.now());

        return MentorshipMapper.toDTO(repo.save(m));
    }

    public MentorshipResponseDTO reject(Long id){
        Mentorship m = repo.findById(id).orElseThrow();
        if(m.getStatus() != MentorshipStatus.REQUESTED) {
            throw new RuntimeException("Invalid state transition: Cannot reject mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.REJECTED);
        m.setEndDate(LocalDate.now());
        return MentorshipMapper.toDTO(repo.save(m));
    }

    public MentorshipResponseDTO accept(Long id) {
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
        if ((m.getStatus() != MentorshipStatus.ACTIVE) && (m.getStatus() !=MentorshipStatus.ACCEPTED)) {
            throw new RuntimeException("Invalid state transition: cannot cancel mentorship in state "+m.getStatus());
        }
        m.setStatus(MentorshipStatus.CANCELLED);
        m.setEndDate(LocalDate.now());

        return MentorshipMapper.toDTO(repo.save(m));
    }
    public MentorshipResponseDTO getByStudentAndAlumni(Long studentId, Long alumniId) {
        Mentorship mentorship = repo.findByStudentIdAndAlumniId(studentId, alumniId)
                .orElseThrow(() -> new RuntimeException("Mentorship not found"));
        return MentorshipMapper.toDTO(mentorship);
    }

}
