package com.alumni.mentorshipservice.controller;

import com.alumni.mentorshipservice.dto.MeetingRequestDTO;
import com.alumni.mentorshipservice.dto.MeetingResponseDTO;
import com.alumni.mentorshipservice.service.MeetingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService service;

    public MeetingController(MeetingService service) {
        this.service = service;
    }

    @PostMapping("/schedule")
    public MeetingResponseDTO schedule(@RequestBody MeetingRequestDTO dto) {

        if (dto.getMentorshipId() == null) {
            throw new RuntimeException("Mentorship ID is required");
        }

        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new RuntimeException("Description cannot be empty");
        }

        if (dto.getDate() == null || dto.getDate().trim().isEmpty()) {
            throw new RuntimeException("Date cannot be empty");
        }

        return service.schedule(dto);
    }

    @GetMapping
    public List<MeetingResponseDTO> getMeetings(@RequestParam Long mentorshipId) {

        if (mentorshipId == null) {
            throw new RuntimeException("Mentorship ID is required");
        }

        return service.getMeetingsByMentorship(mentorshipId);
    }

}
