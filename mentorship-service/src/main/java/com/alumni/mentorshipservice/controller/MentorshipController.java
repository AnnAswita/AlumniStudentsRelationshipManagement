package com.alumni.mentorshipservice.controller;

import com.alumni.mentorshipservice.dto.*;
import com.alumni.mentorshipservice.service.MentorshipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentorship")
public class MentorshipController {

    private final MentorshipService service;

    public MentorshipController(MentorshipService service) {
        this.service = service;
    }

    @PostMapping("/request")
    public MentorshipResponseDTO request(@RequestBody MentorshipRequestDTO dto) {
        return service.request(dto);
    }
    @PutMapping("/{id}/reject")
    public MentorshipResponseDTO reject(@PathVariable Long id) {
        return service.reject(id);
    }

    @PutMapping("/{id}/accept")
    public MentorshipResponseDTO accept(@PathVariable Long id) {
        return service.accept(id);
    }

    @PutMapping("/{id}/complete")
    public MentorshipResponseDTO complete(@PathVariable Long id) {
        return service.complete(id);
    }

    @PutMapping("/{id}/cancel")
    public MentorshipResponseDTO cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    @GetMapping("/{studentId}/getMentorshipById/{alumniId}")
    public MentorshipResponseDTO getByStudentAndAlumni(@PathVariable Long studentId,
                                                       @PathVariable Long alumniId) {
        return service.getByStudentAndAlumni(studentId, alumniId);
    }
    @GetMapping("/student/{studentId}")
    public List<MentorshipResponseDTO> getByStudent(@PathVariable Long studentId) {
        return service.getByStudent(studentId);
    }

    @GetMapping("/alumni/{alumniId}")
    public List<MentorshipResponseDTO> getByAlumni(@PathVariable Long alumniId) {
        return service.getByAlumni(alumniId);
    }

}