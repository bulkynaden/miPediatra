package es.mdef.mipediatra.data;

import entities.AutonomousCommunity;
import es.mdef.mipediatra.repositories.AutonomousCommunityRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SeedDb {
    private final AutonomousCommunityRepository autonomousCommunityRepository;

    public SeedDb(AutonomousCommunityRepository autonomousCommunityRepository) {
        this.autonomousCommunityRepository = autonomousCommunityRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAutonomousCommunities();
    }

    private void seedAutonomousCommunities() {
        if (autonomousCommunityRepository.findAll().isEmpty()) {
            autonomousCommunityRepository.save(new AutonomousCommunity("Andalucía"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Aragón"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Principado de Asturias"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Islas Baleares"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Castilla y León"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Castilla-La Mancha"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Cataluña"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Canarias"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Cantabria"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Ceuta"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Extremadura"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Galicia"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Comunidad de Madrid"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Melilla"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Región de Murcia"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Comunidad Foral de Navarra"));
            autonomousCommunityRepository.save(new AutonomousCommunity("La Rioja"));
            autonomousCommunityRepository.save(new AutonomousCommunity("País Vasco"));
            autonomousCommunityRepository.save(new AutonomousCommunity("Comunidad Valenciana"));
        }
    }
}
