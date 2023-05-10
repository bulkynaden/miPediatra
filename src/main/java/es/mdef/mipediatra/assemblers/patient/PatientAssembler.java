package es.mdef.mipediatra.assemblers.patient;

import entities.Patient;
import entities.Person;
import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.assemblers.autonomous_community.AutonomousCommunityAssembler;
import es.mdef.mipediatra.assemblers.file.FileAssembler;
import es.mdef.mipediatra.constants.PatientType;
import es.mdef.mipediatra.controllers.PatientsController;
import es.mdef.mipediatra.models.patient.PatientModel;
import es.mdef.mipediatra.models.patient.PatientPostModel;
import es.mdef.mipediatra.models.patient.PersonModel;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PatientAssembler {

    private final Logger log = MiPediatraApplication.log;
    private final AutonomousCommunityAssembler autonomousCommunityAssembler;
    private final FileAssembler fileAssembler;

    public PatientAssembler(AutonomousCommunityAssembler autonomousCommunityAssembler, FileAssembler fileAssembler) {
        this.autonomousCommunityAssembler = autonomousCommunityAssembler;
        this.fileAssembler = fileAssembler;
    }

    public PatientModel toModel(Patient entity) {
        PatientModel model = new PatientModel();


        if (entity instanceof Person personEntity) {
            PersonModel personModel = new PersonModel();
            personModel.setName(personEntity.getName());
            personModel.setLastName(personEntity.getLastName());
            personModel.setBirthWeight(personEntity.getBirthWeight());
            personModel.setGender(personEntity.getGender());
            personModel.setBloodType(personEntity.getBloodType());
            personModel.setBirthdate(personEntity.getBirthdate());
            personModel.setAutonomousCommunity(autonomousCommunityAssembler.toModel(personEntity.getAutonomousCommunity()));
            //TODO: Add link a vacunas
            model = personModel;
        }

        model.setComments(entity.getComments());
        if (entity.getPhoto() != null) {
            model.setPhoto(fileAssembler.toModel(entity.getPhoto()));
        }
        //TODO: Add link a consultas
        model.add(linkTo(methodOn(PatientsController.class).getById(entity.getId())).withSelfRel());
        return model;
    }

    public Patient toEntity(PatientPostModel model) {
        Patient entity = null;

        if (model.getType() == PatientType.Person) {
            Person personEntity = new Person();
            personEntity.setName(model.getName());
            personEntity.setLastName(model.getLastName());
            personEntity.setAutonomousCommunity(model.getAutonomousCommunity());
            personEntity.setBirthWeight(model.getBirthWeight());
            personEntity.setGender(model.getGender());
            personEntity.setBloodType(model.getBloodType());
            personEntity.setBirthdate(model.getBirthdate());
            entity = personEntity;
        }

        if (entity != null) {
            entity.setPhoto(model.getPhoto());
            entity.setComments(model.getComments());
        }

        return entity;
    }
}