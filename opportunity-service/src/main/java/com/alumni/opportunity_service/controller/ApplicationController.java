package com.alumni.opportunity_service.controller;

import com.alumni.opportunity_service.dto.ApplicationRequestDTO;
import com.alumni.opportunity_service.dto.ApplicationResponseDTO;
import com.alumni.opportunity_service.entity.Application;
import com.alumni.opportunity_service.service.ApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping("/apply")
    public ApplicationResponseDTO apply(@RequestBody ApplicationRequestDTO request) {

        Application app = service.apply(
                request.getStudentId(),
                request.getOpportunityId()
        );

        return mapToDTO(app);
    }

    @PutMapping("/{id}/status")
    public ApplicationResponseDTO updateStatus(@PathVariable UUID id,
                                               @RequestParam String status) {

        Application app = service.updateStatus(id, status);
        return mapToDTO(app);
    }

    @GetMapping("/student/{studentId}")
    public List<ApplicationResponseDTO> getByStudent(@PathVariable UUID studentId) {

        return service.getByStudent(studentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/opportunity/{opportunityId}")
    public List<ApplicationResponseDTO> getByOpportunity(@PathVariable UUID opportunityId) {

        return service.getByOpportunity(opportunityId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // MAPPER
    private ApplicationResponseDTO mapToDTO(Application app) {

        ApplicationResponseDTO dto = new ApplicationResponseDTO();

        dto.setApplicationId(app.getApplicationId());
        dto.setAppliedDate(app.getAppliedDate());
        dto.setStatus(app.getStatus().name());

        dto.setStudentId(app.getStudent().getId());
        dto.setStudentName(app.getStudent().getName());

        dto.setOpportunityId(app.getOpportunity().getOpportunityId());
        dto.setOpportunityTitle(app.getOpportunity().getTitle());

        return dto;
    }
}