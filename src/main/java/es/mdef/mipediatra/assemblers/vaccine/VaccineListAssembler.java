package es.mdef.mipediatra.assemblers.patient;

import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.controllers.VaccinesController;
import es.mdef.mipediatra.models.vaccine.VaccineListModel;
import es.mdef.mipediatralib.entities.Vaccine;
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
public class VaccineListAssembler implements RepresentationModelAssembler<Vaccine, VaccineListModel> {
    private final Logger log = MiPediatraApplication.log;

    @Override
    public @NonNull VaccineListModel toModel(@NonNull Vaccine entity) {
        VaccineListModel model = new VaccineListModel();
        model.setDose(entity.getDose() != 0 ? entity.getDose() + "º dosis" : "Anual");
        model.setName(entity.getVaccineDetails().getName());
        model.setShortName(entity.getVaccineDetails().getShortName());

        model.add(linkTo(methodOn(VaccinesController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public @NonNull CollectionModel<VaccineListModel> toCollectionModel(Iterable<? extends Vaccine> entities) {
        List<VaccineListModel> listModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .toList();
        return CollectionModel.of(listModels,
                linkTo(methodOn(VaccinesController.class).getAll()).withSelfRel());
    }
}
