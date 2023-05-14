package es.mdef.mipediatra.controllers;

import es.mdef.mipediatra.assemblers.vaccine.VaccineDetailsAssembler;
import es.mdef.mipediatra.assemblers.vaccine.VaccineDetailsListAssembler;
import es.mdef.mipediatra.exceptions.EntityNotFoundException;
import es.mdef.mipediatra.models.vaccine.VaccineDetailsListModel;
import es.mdef.mipediatra.models.vaccine.VaccineDetailsModel;
import es.mdef.mipediatra.repositories.VaccineDetailsRepository;
import es.mdef.mipediatralib.entities.VaccineDetails;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vaccineDetails")
public class VaccineDetailsController {

    private final VaccineDetailsRepository vaccineDetailsRepository;
    private final VaccineDetailsAssembler vaccineDetailsAssembler;
    private final VaccineDetailsListAssembler vaccineDetailsListAssembler;

    public VaccineDetailsController(VaccineDetailsRepository vaccineDetailsRepository,
                                    VaccineDetailsAssembler vaccineDetailsAssembler,
                                    VaccineDetailsListAssembler vaccineDetailsListAssembler) {
        this.vaccineDetailsRepository = vaccineDetailsRepository;
        this.vaccineDetailsAssembler = vaccineDetailsAssembler;
        this.vaccineDetailsListAssembler = vaccineDetailsListAssembler;
    }

    @GetMapping("{id}")
    public VaccineDetailsModel getById(@PathVariable Long id) {
        VaccineDetails vaccine = vaccineDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, "vaccineDetails"));
        return vaccineDetailsAssembler.toModel(vaccine);
    }

    @GetMapping
    public CollectionModel<VaccineDetailsListModel> getAll() {
        return vaccineDetailsListAssembler.toCollectionModel(vaccineDetailsRepository.findAll());
    }
}
