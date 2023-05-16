package es.mdef.mipediatra.repositories;

import es.mdef.mipediatralib.entities.VaccinesPersons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinesPersonsRepository extends JpaRepository<VaccinesPersons, Long> {
}