package es.mdef.mipediatra.repositories;

import es.mdef.mipediatralib.entities.VaccineDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineDetailsRepository extends JpaRepository<VaccineDetails, Long> {

}
