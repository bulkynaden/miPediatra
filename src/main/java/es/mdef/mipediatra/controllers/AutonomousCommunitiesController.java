package es.mdef.mipediatra.controllers;

import entities.AutonomousCommunity;
import es.mdef.mipediatra.assemblers.autonomous_community.AutonomousCommunityAssembler;
import es.mdef.mipediatra.assemblers.autonomous_community.AutonomousCommunityListAssembler;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityListModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPostModel;
import es.mdef.mipediatra.repositories.AutonomousCommunityRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/autonomous_communities")
public class AutonomousCommunitiesController {

    private final AutonomousCommunityRepository autonomousCommunityRepository;
    private final AutonomousCommunityAssembler autonomousCommunityAssembler;
    private final AutonomousCommunityListAssembler autonomousCommunityListAssembler;

    public AutonomousCommunitiesController(AutonomousCommunityRepository autonomousCommunityRepository,
                                           AutonomousCommunityAssembler autonomousCommunityAssembler,
                                           AutonomousCommunityListAssembler autonomousCommunityListAssembler) {
        this.autonomousCommunityRepository = autonomousCommunityRepository;
        this.autonomousCommunityAssembler = autonomousCommunityAssembler;
        this.autonomousCommunityListAssembler = autonomousCommunityListAssembler;
    }

    @GetMapping("{id}")
    public AutonomousCommunityModel getById(@PathVariable Long id) {
        AutonomousCommunity autonomousCommunity = autonomousCommunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "comunidad autónoma"));
        return autonomousCommunityAssembler.toModel(autonomousCommunity);
    }

    @GetMapping
    public CollectionModel<AutonomousCommunityListModel> getAll() {
        return autonomousCommunityListAssembler.toCollectionModel(autonomousCommunityRepository.findAll());
    }

    @PostMapping
    public AutonomousCommunityModel create(@RequestBody AutonomousCommunityPostModel model) {
        AutonomousCommunity autonomousCommunity = autonomousCommunityRepository.save(autonomousCommunityAssembler.toEntity(model));
        return autonomousCommunityAssembler.toModel(autonomousCommunity);
    }
}
