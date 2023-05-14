package es.mdef.mipediatra.repositories;

import es.mdef.mipediatralib.entities.Person;
import es.mdef.mipediatralib.entities.VaccinesPersons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinesPersonsRepository extends JpaRepository<VaccinesPersons, Long> {
    List<VaccinesPersons> findVaccinesPersonsByPerson(Person person);
}