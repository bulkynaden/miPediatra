package es.mdef.mipediatra.repositories;

import es.mdef.mipediatralib.entities.AutonomousCommunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutonomousCommunityRepository extends JpaRepository<AutonomousCommunity, Long> {
    AutonomousCommunity findByName(String name);
}
