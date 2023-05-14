package es.mdef.mipediatra.services.impl;

import es.mdef.mipediatra.assemblers.PatientAssembler;
import es.mdef.mipediatra.models.patient.PatientListModel;
import es.mdef.mipediatra.models.patient.PatientModel;
import es.mdef.mipediatra.models.patient.PatientPostModel;
import es.mdef.mipediatra.models.patient.PatientPutModel;
import es.mdef.mipediatra.repositories.PatientRepository;
import es.mdef.mipediatra.services.AssemblerService;
import es.mdef.mipediatra.services.CrudService;
import es.mdef.mipediatralib.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientsService implements CrudService<Patient, PatientModel, PatientPostModel, PatientPutModel, PatientListModel> {

    private final PatientAssembler patientAssembler;
    private final PatientRepository patientRepository;

    public PatientsService(PatientAssembler patientAssembler, PatientRepository patientRepository) {
        this.patientAssembler = patientAssembler;
        this.patientRepository = patientRepository;
    }

    @Override
    public String getEntityName() {
        return "patient";
    }

    @Override
    public JpaRepository<Patient, Long> getRepository() {
        return patientRepository;
    }

    @Override
    public AssemblerService<Patient, PatientModel, PatientPostModel, PatientPutModel, PatientListModel> getAssembler() {
        return patientAssembler;
    }
}
