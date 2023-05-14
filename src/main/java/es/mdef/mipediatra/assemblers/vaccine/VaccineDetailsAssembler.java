package es.mdef.mipediatra.assemblers.vaccine;

import es.mdef.mipediatra.controllers.VaccineDetailsController;
import es.mdef.mipediatra.models.vaccine.VaccineDetailsModel;
import es.mdef.mipediatralib.entities.VaccineDetails;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VaccineDetailsAssembler {
    public VaccineDetailsModel toModel(VaccineDetails entity) {
        VaccineDetailsModel model = new VaccineDetailsModel();

        model.setName(entity.getName());
        model.setShortName(entity.getShortName());

        model.add(linkTo(methodOn(VaccineDetailsController.class).getById(entity.getId())).withSelfRel());
        return model;
    }
}
