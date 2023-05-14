package es.mdef.mipediatra.repositories;

import es.mdef.mipediatralib.entities.VaccineDosePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineDosePeriodRepository extends JpaRepository<VaccineDosePeriod, Long> {

}
