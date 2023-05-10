package es.mdef.mipediatra.assemblers.patient;

import entities.Patient;
import entities.Person;
import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.controllers.AutonomousCommunitiesController;
import es.mdef.mipediatra.controllers.PatientsController;
import es.mdef.mipediatra.models.patient.PatientListModel;
import es.mdef.mipediatra.models.patient.PersonListModel;
import org.slf4j.Logger;
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
public class PatientListAssembler implements RepresentationModelAssembler<Patient, PatientListModel> {
    private final Logger log = MiPediatraApplication.log;

    @Override
    public @NonNull PatientListModel toModel(@NonNull Patient entity) {
        PatientListModel model = new PatientListModel();


        if (entity instanceof Person personEntity) {
            PersonListModel personModel = new PersonListModel();
            personModel.setName(personEntity.getName());
            personModel.setLastName(personEntity.getLastName());
            personModel.setGender(personEntity.getGender());
            personModel.setBirthdate(personEntity.getBirthdate());

            model.add(linkTo(methodOn(AutonomousCommunitiesController.class).getById(personEntity.getAutonomousCommunity().getId())).withRel("autonomousCommunity"));

            //TODO: Add link a vacunas
            model = personModel;
        }

        model.setComments(entity.getComments());
        model.setPhoto(entity.getPhoto());

        //TODO: ADD LINK a consultas
        model.add(linkTo(methodOn(PatientsController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public @NonNull CollectionModel<PatientListModel> toCollectionModel(Iterable<? extends Patient> entities) {
        List<PatientListModel> userListModels = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(userListModels,
                linkTo(methodOn(PatientsController.class).getAll()).withSelfRel());
    }
}
