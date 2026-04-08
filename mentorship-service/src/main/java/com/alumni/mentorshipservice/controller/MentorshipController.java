package com.alumni.mentorshipservice.controller;

import com.alumni.mentorshipservice.dto.*;
import com.alumni.mentorshipservice.service.MentorshipService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}/accept")
    public MentorshipResponseDTO accept(@PathVariable Long id) {
        return service.accept(id);
    }

    @PutMapping("/{id}/complete")
    public MentorshipResponseDTO complete(@PathVariable Long id) {
        return service.complete(id);
    }
}