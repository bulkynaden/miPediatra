package es.mdef.mipediatra.data;

import constants.BloodType;
import constants.Gender;
import entities.AutonomousCommunity;
import entities.Person;
import es.mdef.mipediatra.repositories.AutonomousCommunityRepository;
import es.mdef.mipediatra.repositories.PatientRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SeedDb {
    private final AutonomousCommunityRepository autonomousCommunityRepository;
    private final PatientRepository patientRepository;

    public SeedDb(AutonomousCommunityRepository autonomousCommunityRepository, PatientRepository patientRepository) {
        this.autonomousCommunityRepository = autonomousCommunityRepository;
        this.patientRepository = patientRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAutonomousCommunities();
        seedPatients();
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

    private void seedPatients() {
        if (patientRepository.findAll().isEmpty()) {
            Person person1 = new Person();
            person1.setName("Javier");
            person1.setLastName("Tomás Acín");
            person1.setGender(Gender.HOMBRE);
            person1.setBirthdate(LocalDateTime.of(1988, 11, 9, 13, 0));
            person1.setBirthWeight(3.4);
            person1.setComments("Hay que vigilarlo");
            person1.setAutonomousCommunity(autonomousCommunityRepository.findByName("Aragón"));
            person1.setBloodType(BloodType.A_POSITIVE);
            patientRepository.save(person1);

            Person person2 = new Person();
            person2.setName("Isabel");
            person2.setLastName("Tomás Acín");
            person2.setGender(Gender.MUJER);
            person2.setBirthdate(LocalDateTime.of(1989, 6, 2, 15, 45));
            person2.setBirthWeight(2.8);
            person2.setComments("Crece adecuadamente");
            person2.setAutonomousCommunity(autonomousCommunityRepository.findByName("Aragón"));
            person2.setBloodType(BloodType.AB_POSITIVE);
            patientRepository.save(person2);
        }
    }
}
