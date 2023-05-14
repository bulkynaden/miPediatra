package es.mdef.mipediatra.repositories;

import es.mdef.mipediatralib.entities.Vaccine;
import es.mdef.mipediatralib.entities.VaccineDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findAllByVaccineDetails(VaccineDetails vaccineDetails);
}
