package es.mdef.mipediatra.data;

import es.mdef.mipediatra.repositories.*;
import es.mdef.mipediatralib.constants.BloodType;
import es.mdef.mipediatralib.constants.Gender;
import es.mdef.mipediatralib.entities.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Period;

@Service
public class SeedDb {
    private final AutonomousCommunityRepository autonomousCommunityRepository;
    private final VaccineDetailsRepository vaccineDetailsRepository;
    private final VaccineRepository applicableRepository;
    private final VaccineDosePeriodRepository vaccineDosePeriodRepository;
    private final PatientRepository patientRepository;

    public SeedDb(AutonomousCommunityRepository autonomousCommunityRepository, VaccineDetailsRepository vaccineDetailsRepository, VaccineRepository applicableRepository, VaccineDosePeriodRepository vaccineDosePeriodRepository, PatientRepository patientRepository) {
        this.autonomousCommunityRepository = autonomousCommunityRepository;
        this.vaccineDetailsRepository = vaccineDetailsRepository;
        this.applicableRepository = applicableRepository;
        this.vaccineDosePeriodRepository = vaccineDosePeriodRepository;
        this.patientRepository = patientRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedAutonomousCommunities();
        seedVaccines();
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


    private void seedVaccines() {
        if (vaccineDetailsRepository.findAll().isEmpty()) {
            VaccineDetails poliomielitis = new VaccineDetails("Poliomielitis", "VPI");
            VaccineDetails dtpa = new VaccineDetails("Difteria, tétanos, tosferina", "DTPa");
            VaccineDetails hib = new VaccineDetails("Haemophilus influenzae b", "Hib");
            VaccineDetails tv = new VaccineDetails("Sarampión, rubeola, parotiditis", "TV");
            VaccineDetails hepatitisB = new VaccineDetails("Hepatitis B", "HB");
            VaccineDetails menB = new VaccineDetails("Enfermedad meningocócica", "MenB");
            VaccineDetails menC = new VaccineDetails("Enfermedad meningocócica", "MenC");
            VaccineDetails menACWY = new VaccineDetails("Enfermedad meningocócica", "MenACWY");
            VaccineDetails vvz = new VaccineDetails("Varicela", "VVZ");
            VaccineDetails vph = new VaccineDetails("Virus del Papiloma Humano", "VPH");
            VaccineDetails vnc = new VaccineDetails("Enfermedad neumocócica", "VNC");
            VaccineDetails vn = new VaccineDetails("Enfermedad neumocócica", "VN");
            VaccineDetails hz = new VaccineDetails("Virus Herpes Zóster", "HZ");
            VaccineDetails gripe = new VaccineDetails("Gripe", "Gripe");

            vaccineDetailsRepository.save(poliomielitis);
            vaccineDetailsRepository.save(dtpa);
            vaccineDetailsRepository.save(hib);
            vaccineDetailsRepository.save(tv);
            vaccineDetailsRepository.save(hepatitisB);
            vaccineDetailsRepository.save(menB);
            vaccineDetailsRepository.save(menC);
            vaccineDetailsRepository.save(menACWY);
            vaccineDetailsRepository.save(vvz);
            vaccineDetailsRepository.save(vph);
            vaccineDetailsRepository.save(vnc);
            vaccineDetailsRepository.save(vn);
            vaccineDetailsRepository.save(hz);
            vaccineDetailsRepository.save(gripe);

            Vaccine poliomielitis1 = new Vaccine(1, poliomielitis);
            applicableRepository.save(poliomielitis1);

            VaccineDosePeriod poliomielitis1DosePeriodAragon =
                    new VaccineDosePeriod(poliomielitis1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 2, 0), true);
            poliomielitis1.addDosePeriod(poliomielitis1DosePeriodAragon);
            vaccineDosePeriodRepository.save(poliomielitis1DosePeriodAragon);

            Vaccine poliomielitis2 = new Vaccine(2, poliomielitis);
            applicableRepository.save(poliomielitis2);

            VaccineDosePeriod poliomielitis2DosePeriodAragon =
                    new VaccineDosePeriod(poliomielitis2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), true);
            poliomielitis2.addDosePeriod(poliomielitis2DosePeriodAragon);
            vaccineDosePeriodRepository.save(poliomielitis2DosePeriodAragon);

            Vaccine poliomielitis3 = new Vaccine(3, poliomielitis);
            applicableRepository.save(poliomielitis3);

            VaccineDosePeriod poliomielitis3DosePeriodAragon =
                    new VaccineDosePeriod(poliomielitis3,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 11, 0), true);
            poliomielitis3.addDosePeriod(poliomielitis3DosePeriodAragon);
            vaccineDosePeriodRepository.save(poliomielitis3DosePeriodAragon);

            Vaccine poliomielitis4 = new Vaccine(4, poliomielitis);
            applicableRepository.save(poliomielitis4);

            VaccineDosePeriod poliomielitis4DosePeriodAragon =
                    new VaccineDosePeriod(poliomielitis4,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(6, 0, 0), true);
            poliomielitis4.addDosePeriod(poliomielitis4DosePeriodAragon);
            vaccineDosePeriodRepository.save(poliomielitis4DosePeriodAragon);

            Vaccine dtpa1 = new Vaccine(1, dtpa);
            applicableRepository.save(dtpa1);

            VaccineDosePeriod dtpa1DosePeriodAragon =
                    new VaccineDosePeriod(dtpa1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 2, 0), true);
            dtpa1.addDosePeriod(dtpa1DosePeriodAragon);
            vaccineDosePeriodRepository.save(dtpa1DosePeriodAragon);

            Vaccine dtpa2 = new Vaccine(2, dtpa);
            applicableRepository.save(dtpa2);

            VaccineDosePeriod dtpa2DosePeriodAragon =
                    new VaccineDosePeriod(dtpa2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), true);
            dtpa2.addDosePeriod(dtpa2DosePeriodAragon);
            vaccineDosePeriodRepository.save(dtpa2DosePeriodAragon);

            Vaccine dtpa3 = new Vaccine(3, dtpa);
            applicableRepository.save(dtpa3);

            VaccineDosePeriod dtpa3DosePeriodAragon =
                    new VaccineDosePeriod(dtpa3,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 11, 0), true);
            dtpa3.addDosePeriod(dtpa3DosePeriodAragon);
            vaccineDosePeriodRepository.save(dtpa3DosePeriodAragon);

            Vaccine dtpa4 = new Vaccine(4, dtpa);
            applicableRepository.save(dtpa4);

            VaccineDosePeriod dtpa4DosePeriodAragon =
                    new VaccineDosePeriod(dtpa4,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(6, 0, 0), true);
            dtpa4.addDosePeriod(dtpa4DosePeriodAragon);
            vaccineDosePeriodRepository.save(dtpa4DosePeriodAragon);

            Vaccine dtpa5 = new Vaccine(5, dtpa);
            applicableRepository.save(dtpa5);

            VaccineDosePeriod dtpa5DosePeriodAragon =
                    new VaccineDosePeriod(dtpa5,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(14, 0, 0), true);
            dtpa5.addDosePeriod(dtpa5DosePeriodAragon);
            vaccineDosePeriodRepository.save(dtpa5DosePeriodAragon);

            Vaccine hib1 = new Vaccine(1, hib);
            applicableRepository.save(hib1);

            VaccineDosePeriod hib1DosePeriodAragon =
                    new VaccineDosePeriod(hib1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 2, 0), true);
            hib1.addDosePeriod(hib1DosePeriodAragon);
            vaccineDosePeriodRepository.save(hib1DosePeriodAragon);

            Vaccine hib2 = new Vaccine(2, hib);
            applicableRepository.save(hib2);

            VaccineDosePeriod hib2DosePeriodAragon =
                    new VaccineDosePeriod(hib2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), true);
            hib2.addDosePeriod(hib2DosePeriodAragon);
            vaccineDosePeriodRepository.save(hib2DosePeriodAragon);

            Vaccine hib3 = new Vaccine(3, hib);
            applicableRepository.save(hib3);

            VaccineDosePeriod hib3DosePeriodAragon =
                    new VaccineDosePeriod(hib3,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 11, 0), true);
            hib3.addDosePeriod(hib3DosePeriodAragon);
            vaccineDosePeriodRepository.save(hib3DosePeriodAragon);

            Vaccine tv1 = new Vaccine(1, tv);
            applicableRepository.save(tv1);

            VaccineDosePeriod tv1DosePeriodAragon =
                    new VaccineDosePeriod(tv1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 12, 0), true);
            tv1.addDosePeriod(tv1DosePeriodAragon);
            vaccineDosePeriodRepository.save(tv1DosePeriodAragon);

            Vaccine tv2 = new Vaccine(2, tv);
            applicableRepository.save(tv2);

            VaccineDosePeriod tv2DosePeriodAragon =
                    new VaccineDosePeriod(tv2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(3, 0, 0), true);
            tv2.addDosePeriod(tv2DosePeriodAragon);
            vaccineDosePeriodRepository.save(tv2DosePeriodAragon);

            Vaccine hepatitisB1 = new Vaccine(1, hepatitisB);
            applicableRepository.save(hepatitisB1);

            VaccineDosePeriod hepatitisB1DosePeriodAragon =
                    new VaccineDosePeriod(hepatitisB1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 0, 0), true);
            hepatitisB1.addDosePeriod(hepatitisB1DosePeriodAragon);
            vaccineDosePeriodRepository.save(hepatitisB1DosePeriodAragon);

            Vaccine hepatitisB2 = new Vaccine(2, hepatitisB);
            applicableRepository.save(hepatitisB2);

            VaccineDosePeriod hepatitisB2DosePeriodAragon =
                    new VaccineDosePeriod(hepatitisB2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 2, 0), true);
            hepatitisB2.addDosePeriod(hepatitisB2DosePeriodAragon);
            vaccineDosePeriodRepository.save(hepatitisB2DosePeriodAragon);

            Vaccine hepatitisB3 = new Vaccine(3, hepatitisB);
            applicableRepository.save(hepatitisB3);

            VaccineDosePeriod hepatitisB3DosePeriodAragon =
                    new VaccineDosePeriod(hepatitisB3,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), true);
            hepatitisB3.addDosePeriod(hepatitisB3DosePeriodAragon);
            vaccineDosePeriodRepository.save(hepatitisB3DosePeriodAragon);

            Vaccine hepatitisB4 = new Vaccine(4, hepatitisB);
            applicableRepository.save(hepatitisB4);

            VaccineDosePeriod hepatitisB4DosePeriodAragon =
                    new VaccineDosePeriod(hepatitisB4,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 11, 0), true);
            hepatitisB4.addDosePeriod(hepatitisB4DosePeriodAragon);
            vaccineDosePeriodRepository.save(hepatitisB4DosePeriodAragon);

            Vaccine menB1 = new Vaccine(1, menB);
            applicableRepository.save(menB1);

            VaccineDosePeriod menB1DosePeriodAragon =
                    new VaccineDosePeriod(menB1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 2, 0), true);
            menB1.addDosePeriod(menB1DosePeriodAragon);
            vaccineDosePeriodRepository.save(menB1DosePeriodAragon);

            Vaccine menB2 = new Vaccine(2, menB);
            applicableRepository.save(menB2);

            VaccineDosePeriod menB2DosePeriodAragon =
                    new VaccineDosePeriod(menB2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), true);
            menB2.addDosePeriod(menB2DosePeriodAragon);
            vaccineDosePeriodRepository.save(menB2DosePeriodAragon);

            Vaccine menB3 = new Vaccine(3, menB);
            applicableRepository.save(menB3);

            VaccineDosePeriod menB3DosePeriodAragon =
                    new VaccineDosePeriod(menB3,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 12, 0), true);
            menB3.addDosePeriod(menB3DosePeriodAragon);
            vaccineDosePeriodRepository.save(menB3DosePeriodAragon);

            Vaccine menC1 = new Vaccine(1, menC);
            applicableRepository.save(menC1);

            VaccineDosePeriod menC1DosePeriodAragon =
                    new VaccineDosePeriod(menC1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), true);
            menC1.addDosePeriod(menC1DosePeriodAragon);
            vaccineDosePeriodRepository.save(menC1DosePeriodAragon);

            Vaccine menC2 = new Vaccine(2, menC);
            applicableRepository.save(menC2);

            VaccineDosePeriod menC2DosePeriodAragon =
                    new VaccineDosePeriod(menC2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 12, 0), true);
            menC2.addDosePeriod(menC2DosePeriodAragon);
            vaccineDosePeriodRepository.save(menC2DosePeriodAragon);

            Vaccine menACWY1 = new Vaccine(1, menACWY);
            applicableRepository.save(menACWY1);

            VaccineDosePeriod menACWY1DosePeriodAragon =
                    new VaccineDosePeriod(menACWY1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(12, 0, 0), true);
            menACWY1.addDosePeriod(menACWY1DosePeriodAragon);
            vaccineDosePeriodRepository.save(menACWY1DosePeriodAragon);

            Vaccine vvz1 = new Vaccine(1, vvz);
            applicableRepository.save(vvz1);

            VaccineDosePeriod vvz1DosePeriodAragon =
                    new VaccineDosePeriod(vvz1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 15, 0), false);
            vvz1.addDosePeriod(vvz1DosePeriodAragon);
            vaccineDosePeriodRepository.save(vvz1DosePeriodAragon);

            Vaccine vvz2 = new Vaccine(2, vvz);
            applicableRepository.save(vvz2);

            VaccineDosePeriod vvz2DosePeriodAragon =
                    new VaccineDosePeriod(vvz2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(3, 0, 0), false);
            vvz2.addDosePeriod(vvz2DosePeriodAragon);
            vaccineDosePeriodRepository.save(vvz2DosePeriodAragon);

            Vaccine vph1 = new Vaccine(1, vph);
            applicableRepository.save(vph1);

            VaccineDosePeriod vph1DosePeriodAragon =
                    new VaccineDosePeriod(vph1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(12, 0, 0), false);
            vph1.addDosePeriod(vph1DosePeriodAragon);
            vaccineDosePeriodRepository.save(vph1DosePeriodAragon);

            Vaccine vnc1 = new Vaccine(1, vnc);
            applicableRepository.save(vnc1);

            VaccineDosePeriod vnc1DosePeriodAragon =
                    new VaccineDosePeriod(vnc1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 2, 0), false);
            vnc1.addDosePeriod(vnc1DosePeriodAragon);
            vaccineDosePeriodRepository.save(vnc1DosePeriodAragon);

            Vaccine vnc2 = new Vaccine(2, vnc);
            applicableRepository.save(vnc2);

            VaccineDosePeriod vnc2DosePeriodAragon =
                    new VaccineDosePeriod(vnc2,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 4, 0), false);
            vnc2.addDosePeriod(vnc2DosePeriodAragon);
            vaccineDosePeriodRepository.save(vnc2DosePeriodAragon);

            Vaccine vnc3 = new Vaccine(3, vnc);
            applicableRepository.save(vnc3);

            VaccineDosePeriod vnc3DosePeriodAragon =
                    new VaccineDosePeriod(vnc3,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(0, 11, 0), false);
            vnc3.addDosePeriod(vnc3DosePeriodAragon);
            vaccineDosePeriodRepository.save(vnc3DosePeriodAragon);

            Vaccine hz1 = new Vaccine(1, hz);
            applicableRepository.save(hz1);

            VaccineDosePeriod hz1DosePeriodAragon =
                    new VaccineDosePeriod(hz1,
                            autonomousCommunityRepository.findByName("Aragón"),
                            Period.of(65, 0, 0), false);
            hz1.addDosePeriod(hz1DosePeriodAragon);
            vaccineDosePeriodRepository.save(hz1DosePeriodAragon);

            Vaccine gripe1 = new Vaccine(0, gripe);
            applicableRepository.save(gripe1);
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
