package es.mdef.mipediatra.controllers;

import entities.Patient;
import entities.Person;
import es.mdef.mipediatra.MiPediatraApplication;
import es.mdef.mipediatra.assemblers.patient.PatientAssembler;
import es.mdef.mipediatra.assemblers.patient.PatientListAssembler;
import es.mdef.mipediatra.constants.PatientType;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.models.patient.PatientListModel;
import es.mdef.mipediatra.models.patient.PatientModel;
import es.mdef.mipediatra.models.patient.PatientPostModel;
import es.mdef.mipediatra.models.patient.PatientPutModel;
import es.mdef.mipediatra.repositories.PatientRepository;
import es.mdef.mipediatra.services.FileStorageService;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patients")
public class PatientsController {
    private final PatientRepository patientRepository;
    private final PatientAssembler patientAssembler;
    private final PatientListAssembler patientListAssembler;
    private final FileStorageService fileService;
    private final Logger log;

    public PatientsController(PatientRepository patientRepository,
                              PatientAssembler patientAssembler,
                              PatientListAssembler patientListAssembler, FileStorageService fileService) {
        this.patientRepository = patientRepository;
        this.patientAssembler = patientAssembler;
        this.patientListAssembler = patientListAssembler;
        this.fileService = fileService;
        this.log = MiPediatraApplication.log;
    }

    @GetMapping("{id}")
    public PatientModel getById(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "person"));
        return patientAssembler.toModel(patient);
    }

    @GetMapping
    public CollectionModel<PatientListModel> getAll() {
        return patientListAssembler.toCollectionModel(patientRepository.findAll());
    }


    @PostMapping
    public PatientModel create(
            @RequestBody PatientPostModel model) {
        Patient patient = patientRepository.save(patientAssembler.toEntity(model));
        return patientAssembler.toModel(patient);
    }

    @PutMapping("{id}")
    public PatientModel edit(@PathVariable Long id,
                             @RequestBody PatientPutModel model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "usuario"));

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

        patientRepository.save(Objects.requireNonNull(patient));

        return patientAssembler.toModel(patient);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        patientRepository.deleteById(id);
    }
}