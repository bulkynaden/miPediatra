package es.mdef.mipediatra.assemblers;

import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.controllers.PatientsController;
import es.mdef.mipediatra.controllers.VaccinesController;
import es.mdef.mipediatra.controllers.VaccinesPersonsController;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsListModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPostModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPutModel;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatralib.entities.VaccinesPersons;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VaccinesPersonsAssembler extends AssemblerService<VaccinesPersons, VaccinesPersonsModel, VaccinesPersonsPostModel, VaccinesPersonsPutModel, VaccinesPersonsListModel> {
    private final Logger log = MiPediatraApplication.log;
    private final FileAssembler fileAssembler;

    public VaccinesPersonsAssembler(FileAssembler fileAssembler) {
        super(() -> linkTo(methodOn(VaccinesPersonsController.class).getAll()).withSelfRel());
        this.fileAssembler = fileAssembler;
    }

    public VaccinesPersonsModel toModel(VaccinesPersons entity) {
        VaccinesPersonsModel model = new VaccinesPersonsModel();

        String name = entity.getVaccine().getVaccineDetails().getName();
        String shortName = entity.getVaccine().getVaccineDetails().getShortName();
        int dose = entity.getVaccine().getDose();
        String doseToDisplay = dose != 0 ? dose + "º dosis" : "Anual";

        String finalName = name + " (" + shortName + ")" + " - " + doseToDisplay;

        model.setName(finalName);
        model.setDate(entity.getDate());
        model.setExpectedDate(entity.getExpectedDate());
        model.setPhoto(fileAssembler.toModel(entity.getPhoto()));
        model.setReaction(entity.getReaction());
        model.setHasBeenAdministered(entity.isHasBeenAdministered());

        model.add(linkTo(methodOn(PatientsController.class).getVaccines(entity.getPerson().getId())).withRel("patient"));
        model.add(linkTo(methodOn(VaccinesController.class).getById(entity.getVaccine().getId())).withRel("vaccine"));
        model.add(linkTo(methodOn(VaccinesPersonsController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    @Override
    public VaccinesPersonsListModel toListModel(VaccinesPersons entity) {
        VaccinesPersonsListModel model = new VaccinesPersonsListModel();

        String name = entity.getVaccine().getVaccineDetails().getName();
        String shortName = entity.getVaccine().getVaccineDetails().getShortName();
        int dose = entity.getVaccine().getDose();
        String doseToDisplay = dose != 0 ? dose + "º dosis" : "Anual";

        String finalName = name + " (" + shortName + ")" + " - " + doseToDisplay;

        model.setName(finalName);
        model.setDate(entity.getDate());
        model.setExpectedDate(entity.getExpectedDate());
        model.setPhoto(fileAssembler.toModel(entity.getPhoto()));
        model.setReaction(entity.getReaction());
        model.setHasBeenAdministered(entity.isHasBeenAdministered());

        model.add(linkTo(methodOn(PatientsController.class).getVaccines(entity.getPerson().getId())).withRel("patient"));
        model.add(linkTo(methodOn(VaccinesController.class).getById(entity.getVaccine().getId())).withRel("vaccine"));
        model.add(linkTo(methodOn(VaccinesPersonsController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public VaccinesPersons toEntity(VaccinesPersonsPostModel model) {
        VaccinesPersons entity = new VaccinesPersons();
        entity.setVaccine(model.getVaccine());
        entity.setPerson(model.getPerson());
        entity.setDate(model.getDate());
        entity.setExpectedDate(model.getExpectedDate());
        entity.setPhoto(model.getPhoto());
        entity.setReaction(model.getReaction());
        entity.setHasBeenAdministered(model.isHasBeenAdministered());
        return entity;
    }

    @Override
    public VaccinesPersons toEntity(VaccinesPersonsPutModel model) {
        return null;
    }

    @Override
    public VaccinesPersons update(VaccinesPersons entity, VaccinesPersonsPutModel model) {
        return null;
    }
}
