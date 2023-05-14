package es.mdef.mipediatra.services.impl;

import es.mdef.mipediatra.assemblers.AutonomousCommunityAssembler;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityListModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPostModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPutModel;
import es.mdef.mipediatra.repositories.AutonomousCommunityRepository;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatralib.entities.AutonomousCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AutonomousCommunitiesService implements CrudService<AutonomousCommunity, AutonomousCommunityModel, AutonomousCommunityPostModel, AutonomousCommunityPutModel, AutonomousCommunityListModel> {
    private final AutonomousCommunityRepository autonomousCommunityRepository;
    private final AutonomousCommunityAssembler autonomousCommunityAssembler;

    public AutonomousCommunitiesService(AutonomousCommunityRepository autonomousCommunityRepository, AutonomousCommunityAssembler autonomousCommunityAssembler) {
        this.autonomousCommunityRepository = autonomousCommunityRepository;
        this.autonomousCommunityAssembler = autonomousCommunityAssembler;
    }

    @Override
    public String getEntityName() {
        return "autonomous community";
    }

    @Override
    public JpaRepository<AutonomousCommunity, Long> getRepository() {
        return autonomousCommunityRepository;
    }

    @Override
    public AssemblerService<AutonomousCommunity, AutonomousCommunityModel, AutonomousCommunityPostModel, AutonomousCommunityPutModel, AutonomousCommunityListModel> getAssembler() {
        return autonomousCommunityAssembler;
    }
}
