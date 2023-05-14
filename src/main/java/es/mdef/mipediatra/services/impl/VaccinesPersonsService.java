package es.mdef.mipediatra.services.impl;

import es.mdef.mipediatra.assemblers.VaccinesPersonsAssembler;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsListModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPostModel;
import es.mdef.mipediatra.models.vaccine.VaccinesPersonsPutModel;
import es.mdef.mipediatra.repositories.VaccinesPersonsRepository;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatralib.entities.Person;
import es.mdef.mipediatralib.entities.VaccinesPersons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinesPersonsService implements CrudService<VaccinesPersons, VaccinesPersonsModel, VaccinesPersonsPostModel, VaccinesPersonsPutModel, VaccinesPersonsListModel> {
    private final VaccinesPersonsRepository repository;
    private final VaccinesPersonsAssembler assembler;

    public VaccinesPersonsService(VaccinesPersonsRepository repository, VaccinesPersonsAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Override
    public String getEntityName() {
        return "vaccines_persons";
    }

    @Override
    public JpaRepository<VaccinesPersons, Long> getRepository() {
        return repository;
    }

    @Override
    public AssemblerService<VaccinesPersons, VaccinesPersonsModel, VaccinesPersonsPostModel, VaccinesPersonsPutModel, VaccinesPersonsListModel> getAssembler() {
        return assembler;
    }

    public List<VaccinesPersons> findVaccinesPersonsByPerson(Person person) {
        return repository.findVaccinesPersonsByPerson(person);
    }
}
