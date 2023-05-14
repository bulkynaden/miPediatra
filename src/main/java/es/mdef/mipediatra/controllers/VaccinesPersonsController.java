package es.mdef.mipediatra.controllers;

import es.mdef.mipediatra.models.vaccine.VaccinesPersonsListModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPostModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPutModel;
import es.mdef.mipediatra.services.ControllerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatra.services.impl.VaccinesPersonsService;
import es.mdef.mipediatralib.entities.VaccinesPersons;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/vaccines_persons")
public class VaccinesPersonsController implements ControllerService<VaccinesPersons, VaccinesPersonsModel, VaccinesPersonsPostModel, VaccinesPersonsPutModel, VaccinesPersonsListModel> {
    private final VaccinesPersonsService vaccinesPersonsService;

    public VaccinesPersonsController(VaccinesPersonsService vaccinesPersonsService) {
        this.vaccinesPersonsService = vaccinesPersonsService;
    }

    @Override
    public CrudService<VaccinesPersons, VaccinesPersonsModel, VaccinesPersonsPostModel, VaccinesPersonsPutModel, VaccinesPersonsListModel> getService() {
        return vaccinesPersonsService;
    }
}
