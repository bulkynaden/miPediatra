package es.mdef.mipediatra.controllers;

import es.mdef.mipediatra.assemblers.patient.VaccineListAssembler;
import es.mdef.mipediatra.assemblers.vaccine.VaccineAssembler;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.models.vaccine.VaccineListModel;
import es.mdef.mipediatra.models.vaccine.VaccineModel;
import es.mdef.mipediatra.repositories.VaccineDetailsRepository;
import es.mdef.mipediatra.repositories.VaccineRepository;
import es.mdef.mipediatralib.entities.Vaccine;
import es.mdef.mipediatralib.entities.VaccineDetails;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vaccines")
public class VaccinesController {
    private final VaccineRepository vaccineRepository;
    private final VaccineAssembler vaccineAssembler;
    private final VaccineListAssembler vaccineListAssembler;
    private final VaccineDetailsRepository vaccineDetailsRepository;

    public VaccinesController(VaccineRepository vaccineRepository,
                              VaccineAssembler vaccineAssembler,
                              VaccineListAssembler vaccineListAssembler,
                              VaccineDetailsRepository vaccineDetailsRepository) {
        this.vaccineRepository = vaccineRepository;
        this.vaccineAssembler = vaccineAssembler;
        this.vaccineListAssembler = vaccineListAssembler;
        this.vaccineDetailsRepository = vaccineDetailsRepository;
    }


    @GetMapping("{id}")
    public VaccineModel getById(@PathVariable Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "vaccine"));
        return vaccineAssembler.toModel(vaccine);
    }

    @GetMapping
    public CollectionModel<VaccineListModel> getAll() {
        return vaccineListAssembler.toCollectionModel(vaccineRepository.findAll());
    }

    @GetMapping({"{id}/doses"})
    public CollectionModel<VaccineListModel> getAllDosesForVaccineDetails(@PathVariable long id) {
        VaccineDetails vaccineDetails = vaccineDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "vaccineDetails"));
        return vaccineListAssembler.toCollectionModel(vaccineRepository.findAllByVaccineDetails(vaccineDetails));
    }
}