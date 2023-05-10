package es.mdef.mipediatra.assemblers.autonomous_community;

import entities.AutonomousCommunity;
import es.mdef.mipediatra.controllers.AutonomousCommunitiesController;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPostModel;
import io.micrometer.common.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AutonomousCommunityAssembler {
    public @NonNull AutonomousCommunityModel toModel(@NonNull AutonomousCommunity entity) {
        AutonomousCommunityModel model = new AutonomousCommunityModel();
        model.setName(entity.getName());
        model.add(linkTo(methodOn(AutonomousCommunitiesController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public AutonomousCommunity toEntity(AutonomousCommunityPostModel model) {
        return new AutonomousCommunity(model.getName());
    }
}
