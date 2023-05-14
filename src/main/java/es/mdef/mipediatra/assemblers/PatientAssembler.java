package es.mdef.mipediatra.assemblers;

import es.mdef.mipediatra.constants.PatientType;
import es.mdef.mipediatra.controllers.PatientsController;
import es.mdef.mipediatra.models.patient.*;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatralib.entities.Patient;
import es.mdef.mipediatralib.entities.Person;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PatientAssembler extends AssemblerService<Patient, PatientModel, PatientPostModel, PatientPutModel, PatientListModel> {

    private final AutonomousCommunityAssembler autonomousCommunityAssembler;
    private final FileAssembler fileAssembler;

    public PatientAssembler(AutonomousCommunityAssembler autonomousCommunityAssembler, FileAssembler fileAssembler) {
        super(() -> linkTo(methodOn(PatientsController.class).getAll()).withSelfRel());
        this.autonomousCommunityAssembler = autonomousCommunityAssembler;
        this.fileAssembler = fileAssembler;
    }

    @Override
    public PatientModel toModel(Patient patient) {
        PatientModel model = new PatientModel();

        if (patient instanceof Person personEntity) {
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

        model.setComments(patient.getComments());
        if (patient.getPhoto() != null) {
            model.setPhoto(fileAssembler.toModel(patient.getPhoto()));
        }
        //TODO: Add link a consultas
        model.add(linkTo(methodOn(PatientsController.class).getById(patient.getId())).withSelfRel());
        return model;
    }

    @Override
    public PatientListModel toListModel(Patient patient) {
        PatientListModel model = new PatientListModel();

        if (patient instanceof Person personEntity) {
            PersonListModel personModel = new PersonListModel();
            personModel.setName(personEntity.getName());
            personModel.setLastName(personEntity.getLastName());
            personModel.setGender(personEntity.getGender());
            personModel.setBirthdate(personEntity.getBirthdate());
            //TODO: Add link a vacunas
            model = personModel;
        }

        model.setComments(patient.getComments());

        model.setComments(patient.getComments());
        if (patient.getPhoto() != null) {
            model.setPhoto(fileAssembler.toModel(patient.getPhoto()));
        }
        //TODO: Add link a consultas
        model.add(linkTo(methodOn(PatientsController.class).getById(patient.getId())).withSelfRel());
        return model;
    }

    @Override
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

    @Override
    public Patient toEntity(PatientPutModel model) {
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

    @Override
    public Patient update(Patient patient, PatientPutModel model) {
        if (patient != null && model.getType() == PatientType.Person) {
            ((Person) patient).setName(model.getName());
            ((Person) patient).setLastName(model.getLastName());
            ((Person) patient).setAutonomousCommunity(model.getAutonomousCommunity());
            ((Person) patient).setBirthWeight(model.getBirthWeight());
            ((Person) patient).setGender(model.getGender());
            ((Person) patient).setBloodType(model.getBloodType());
            ((Person) patient).setBirthdate(model.getBirthdate());
        }

        if (patient != null) {
            patient.setPhoto(model.getPhoto());
            patient.setComments(model.getComments());
        }
        return patient;
    }
}
