package es.mdef.mipediatra.controllers;

import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityListModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPostModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPutModel;
import es.mdef.mipediatra.services.ControllerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatra.services.impl.AutonomousCommunitiesService;
import es.mdef.mipediatralib.entities.AutonomousCommunity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/autonomous_communities")
public class AutonomousCommunitiesController implements ControllerService<AutonomousCommunity, AutonomousCommunityModel, AutonomousCommunityPostModel, AutonomousCommunityPutModel, AutonomousCommunityListModel> {

    private final AutonomousCommunitiesService autonomousCommunitiesService;

    public AutonomousCommunitiesController(AutonomousCommunitiesService autonomousCommunitiesService) {
        this.autonomousCommunitiesService = autonomousCommunitiesService;
    }

    @Override
    public CrudService<AutonomousCommunity, AutonomousCommunityModel, AutonomousCommunityPostModel, AutonomousCommunityPutModel, AutonomousCommunityListModel> getService() {
        return autonomousCommunitiesService;
    }
}
