package es.mdef.mipediatra.assemblers;

import es.mdef.mipediatra.controllers.AutonomousCommunitiesController;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityListModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPostModel;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityPutModel;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatralib.entities.AutonomousCommunity;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AutonomousCommunityAssembler extends AssemblerService<AutonomousCommunity, AutonomousCommunityModel, AutonomousCommunityPostModel, AutonomousCommunityPutModel, AutonomousCommunityListModel> {

    public AutonomousCommunityAssembler() {
        super(() -> WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AutonomousCommunitiesController.class).getAll()).withSelfRel());
    }

    @Override
    public AutonomousCommunityModel toModel(AutonomousCommunity autonomousCommunity) {
        AutonomousCommunityModel model = new AutonomousCommunityModel();
        model.setName(autonomousCommunity.getName());
        model.add(linkTo(methodOn(AutonomousCommunitiesController.class).getById(autonomousCommunity.getId())).withSelfRel());
        return model;
    }

    @Override
    public AutonomousCommunityListModel toListModel(AutonomousCommunity autonomousCommunity) {
        AutonomousCommunityListModel model = new AutonomousCommunityListModel();

        model.setName(autonomousCommunity.getName());

        model.add(linkTo(methodOn(AutonomousCommunitiesController.class).getById(autonomousCommunity.getId())).withSelfRel());
        return model;
    }

    @Override
    public AutonomousCommunity toEntity(AutonomousCommunityPostModel model) {
        return new AutonomousCommunity(model.getName());
    }

    @Override
    public AutonomousCommunity toEntity(AutonomousCommunityPutModel model) {
        return new AutonomousCommunity(model.getName());
    }

    @Override
    public AutonomousCommunity update(AutonomousCommunity entity, AutonomousCommunityPutModel model) {
        entity.setName(model.getName());
        return entity;
    }
}
