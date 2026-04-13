package com.alumni.opportunity_service.service.Implementation;
import com.alumni.opportunity_service.entity.User;
import com.alumni.opportunity_service.enums.UserRole;
import com.alumni.opportunity_service.entity.Opportunity;
import com.alumni.opportunity_service.repository.UserRepository;
import com.alumni.opportunity_service.repository.OpportunityRepository;
import com.alumni.opportunity_service.service.OpportunityService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityRepository repository;
    private final UserRepository userRepository;

    public OpportunityServiceImpl(OpportunityRepository repository,
                                  UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Opportunity create(Opportunity opportunity, UUID userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // ONLY ALUMNI CAN POST
        if (user.getRole() != UserRole.ALUMNI) {
            throw new RuntimeException("Only mentors (ALUMNI) can post opportunities");
        }

        opportunity.setAlumni(user);

        return repository.save(opportunity);
    }

    @Override
    public Opportunity update(UUID id, UUID userId, Opportunity updatedOpportunity) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != UserRole.ALUMNI) {
            throw new RuntimeException("Only ALUMNI can update opportunities");
    }

        Opportunity existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Opportunity not found"));

        existing.setTitle(updatedOpportunity.getTitle());
        existing.setDescription(updatedOpportunity.getDescription());
        existing.setType(updatedOpportunity.getType());
        existing.setDeadline(updatedOpportunity.getDeadline());

        return repository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Opportunity> getAll() {
        return repository.findAll();
    }

    @Override
    public Opportunity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found"));
    }
}
