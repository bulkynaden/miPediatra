package es.mdef.mipediatra.controllers;

import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.exceptions.PatientNotAPersonException;
import es.mdef.mipediatra.models.patient.PatientListModel;
import es.mdef.mipediatra.models.patient.PatientModel;
import es.mdef.mipediatra.models.patient.PatientPostModel;
import es.mdef.mipediatra.models.patient.PatientPutModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsListModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPostModel;
import es.mdef.mipediatra.services.ControllerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatra.services.impl.PatientsService;
import es.mdef.mipediatra.services.impl.VaccinesPersonsService;
import es.mdef.mipediatralib.entities.Patient;
import es.mdef.mipediatralib.entities.Person;
import es.mdef.mipediatralib.entities.VaccinesPersons;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patients")
public class PatientsController implements ControllerService<Patient, PatientModel, PatientPostModel, PatientPutModel, PatientListModel> {
    private final Logger log = MiPediatraApplication.log;
    private final PatientsService patientsService;
    private final VaccinesPersonsService vaccinesPersonsService;

    public PatientsController(PatientsService patientsService, VaccinesPersonsService vaccinesPersonsService) {
        this.patientsService = patientsService;
        this.vaccinesPersonsService = vaccinesPersonsService;
    }

    @GetMapping("{id}/vaccines")
    public CollectionModel<VaccinesPersonsListModel> getVaccines(@PathVariable Long id) {
        Patient patient = patientsService.getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(id, "person"));


        if (patient instanceof Person person) {
            return vaccinesPersonsService.getAssembler().toCollectionModel(vaccinesPersonsService.findVaccinesPersonsByPerson(person));
        } else {
            throw new PatientNotAPersonException(id);
        }
    }

    @PostMapping({"{id}/add-vaccine"})
    public PatientModel addVaccine(
            @RequestBody VaccinesPersonsPostModel model, @PathVariable long id) {
        Patient patient = patientsService.getRepository().findById(id).orElseThrow(() -> new EntityNotFoundException(id, "paciente"));

        if (patient instanceof Person person) {
            model.setPerson(person);
            VaccinesPersons applicablePatients = vaccinesPersonsService.getRepository().save(vaccinesPersonsService.getAssembler().toEntity(model));
            person.getVaccinesPersons().add(applicablePatients);
            patientsService.getRepository().save(Objects.requireNonNull(person));
        } else {
            throw new PatientNotAPersonException(id);
        }

        return patientsService.getAssembler().toModel(patient);
    }

    @Override
    public CrudService<Patient, PatientModel, PatientPostModel, PatientPutModel, PatientListModel> getService() {
        return patientsService;
    }
}