package com.alumni.opportunity_service.controller;

import com.alumni.opportunity_service.dto.OpportunityRequestDTO;
import com.alumni.opportunity_service.dto.OpportunityResponseDTO;
import com.alumni.opportunity_service.entity.Opportunity;
import com.alumni.opportunity_service.service.OpportunityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/opportunities")
public class OpportunityController {

    private final OpportunityService service;

    public OpportunityController(OpportunityService service) {
        this.service = service;
    }

    @PostMapping
    public OpportunityResponseDTO create(@RequestBody OpportunityRequestDTO request) {

        Opportunity opportunity = new Opportunity();
        opportunity.setTitle(request.getTitle());
        opportunity.setDescription(request.getDescription());
        opportunity.setType(request.getType());
        opportunity.setDeadline(request.getDeadline());

        Opportunity saved = service.create(opportunity, request.getUserId());

        return mapToDTO(saved);
    }

    @GetMapping
    public List<OpportunityResponseDTO> getAll() {
        return service.getAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public OpportunityResponseDTO getById(@PathVariable UUID id) {
        return mapToDTO(service.getById(id));
    }

    @PutMapping("/{id}")
    public OpportunityResponseDTO update(@PathVariable UUID id,
                                         @RequestParam UUID userId,
                                         @RequestBody OpportunityRequestDTO request) {

        Opportunity updated = new Opportunity();
        updated.setTitle(request.getTitle());
        updated.setDescription(request.getDescription());
        updated.setType(request.getType());
        updated.setDeadline(request.getDeadline());

        Opportunity saved = service.update(id, userId, updated);

        return mapToDTO(saved);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }


    private OpportunityResponseDTO mapToDTO(Opportunity op) {

        OpportunityResponseDTO dto = new OpportunityResponseDTO();

        dto.setOpportunityId(op.getOpportunityId());
        dto.setTitle(op.getTitle());
        dto.setDescription(op.getDescription());
        dto.setType(op.getType());
        dto.setDeadline(op.getDeadline());
        dto.setCreatedAt(op.getCreatedAt());

        return dto;
    }
}