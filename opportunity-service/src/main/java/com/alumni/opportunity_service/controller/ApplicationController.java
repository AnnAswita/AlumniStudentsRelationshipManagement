package com.alumni.opportunity_service.controller;

import com.alumni.opportunity_service.dto.ApplicationRequestDTO;
import com.alumni.opportunity_service.dto.ApplicationResponseDTO;
import com.alumni.opportunity_service.entity.Application;
import com.alumni.opportunity_service.service.ApplicationService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService service;

    public ApplicationController(ApplicationService service) {
        this.service = service;
    }

    @PostMapping("/apply")
    public ApplicationResponseDTO apply(
            @RequestBody ApplicationRequestDTO request,
            HttpServletRequest httpRequest
    ) {

        Application app = service.apply(
                request.getStudentId(),
                request.getOpportunityId(),
                httpRequest
        );

        return mapToDTO(app);
    }

    @PutMapping("/{id}/status")
    public ApplicationResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            HttpServletRequest httpRequest
    ) {

        Application app = service.updateStatus(id, status, httpRequest);
        return mapToDTO(app);
    }

    @GetMapping("/student/{studentId}")
    public List<ApplicationResponseDTO> getByStudent(@PathVariable Long studentId) {

        return service.getByStudent(studentId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/opportunity/{opportunityId}")
    public List<ApplicationResponseDTO> getByOpportunity(@PathVariable Long opportunityId) {

        return service.getByOpportunity(opportunityId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }


    private ApplicationResponseDTO mapToDTO(Application app) {

    ApplicationResponseDTO dto = new ApplicationResponseDTO();

    dto.setApplicationId(app.getApplicationId());
    dto.setAppliedDate(app.getAppliedDate());
    dto.setStatus(app.getStatus().name());

    dto.setStudentId(app.getStudentId());
    dto.setOpportunityId(app.getOpportunityId());

    return dto;
    }
}