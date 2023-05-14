package es.mdef.mipediatra.assemblers.vaccine;

import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.controllers.VaccinesController;
import es.mdef.mipediatra.models.vaccine.VaccineModel;
import es.mdef.mipediatralib.entities.Vaccine;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VaccineAssembler {
    private final Logger log = MiPediatraApplication.log;

    public VaccineModel toModel(Vaccine entity) {
        VaccineModel model = new VaccineModel();

        model.setDose(entity.getDose() != 0 ? entity.getDose() + "º dosis" : "Anual");
        model.setName(entity.getVaccineDetails().getName());
        model.setShortName(entity.getVaccineDetails().getShortName());

        model.add(linkTo(methodOn(VaccinesController.class).getById(entity.getId())).withSelfRel());
        return model;
    }
}