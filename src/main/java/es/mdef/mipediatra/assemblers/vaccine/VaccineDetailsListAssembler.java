package es.mdef.mipediatra.assemblers.vaccine;

import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.controllers.VaccineDetailsController;
import es.mdef.mipediatra.models.vaccine.VaccineDetailsListModel;
import es.mdef.mipediatralib.entities.VaccineDetails;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VaccineDetailsListAssembler implements RepresentationModelAssembler<VaccineDetails, VaccineDetailsListModel> {
    private final Logger log = MiPediatraApplication.log;

    @Override
    public @NonNull VaccineDetailsListModel toModel(@NonNull VaccineDetails entity) {
        VaccineDetailsListModel model = new VaccineDetailsListModel();
        model.setName(entity.getName() + " (" + entity.getShortName() + ")");

        model.add(linkTo(methodOn(VaccineDetailsController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public @NonNull CollectionModel<VaccineDetailsListModel> toCollectionModel(Iterable<? extends VaccineDetails> entities) {
        List<VaccineDetailsListModel> listModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .toList();
        return CollectionModel.of(listModels,
                linkTo(methodOn(VaccineDetailsController.class).getAll()).withSelfRel());
    }
}
