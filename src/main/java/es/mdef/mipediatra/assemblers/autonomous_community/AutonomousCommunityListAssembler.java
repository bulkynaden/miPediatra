package es.mdef.mipediatra.assemblers.autonomous_community;

import entities.AutonomousCommunity;
import es.mdef.mipediatra.controllers.AutonomousCommunitiesController;
import es.mdef.mipediatra.models.autonomous_community.AutonomousCommunityListModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AutonomousCommunityListAssembler implements RepresentationModelAssembler<AutonomousCommunity, AutonomousCommunityListModel> {
    @Override
    public @NonNull AutonomousCommunityListModel toModel(AutonomousCommunity entity) {
        AutonomousCommunityListModel model = new AutonomousCommunityListModel();

        model.setName(entity.getName());

        model.add(linkTo(methodOn(AutonomousCommunitiesController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public @NonNull CollectionModel<AutonomousCommunityListModel> toCollectionModel(Iterable<? extends AutonomousCommunity> entities) {
        List<AutonomousCommunityListModel> listModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(listModels,
                linkTo(methodOn(AutonomousCommunitiesController.class).getAll()).withSelfRel());
    }
}
