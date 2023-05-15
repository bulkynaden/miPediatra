import { defineStore } from "pinia";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import { useSymptomsStore } from "@/store/symptomsStore.js";
import { useVaccinesStore } from "@/store/vaccinesStore.js";
import axios from "axios";
import getIdFromLink from "@/services/parsers.js";
import { useBloodTypesStore } from "@/store/bloodTypesStore.js";
import { useFilesStore } from "@/store/filesStore.js";

export const usePatientsStore = defineStore({
  id: "patientsStore",
  state: () => ({
    patients: [],
  }),
  actions: {
    async loadData() {
      if (this.patients === null || this.patients.length === 0) {
        await this.fetchPatients();
      }
    },
    async fetchPatients() {
      const response = await axios.get(
        import.meta.env.VITE_APP_API_URL + "patients"
      );

      if (response.data._embedded) {
        this.patients = response.data._embedded.persons.map((patient) => {
          const selfLink = patient._links.self.href;
          return {
            id: getIdFromLink(selfLink),
            ...patient,
          };
        });
      } else {
        this.patients = [];
      }
    },

    async getPatients() {
      await this.loadData();
      return this.patients;
    },

    async getPatient(id) {
      await this.loadData();
      return new Promise((resolve) => {
        const patient = this.patients.find(
          (patient) => patient.id === parseInt(id, 10)
        );
        resolve(patient);
      });
    },

    async getPatientDetails(id) {
      const patientData = await axios.get(
        import.meta.env.VITE_APP_API_URL + "patients/" + id
      );
      const patient = patientData.data;

      return await this.loadPatientFields(patient);
    },

    async getPatientCopy(id) {
      const patient = await this.getPatientDetails(id);
      return { ...patient };
    },

    async getPatientWithConsultations(patient) {
      patient.consultations = await this.getConsultations(patient);
      return patient;
    },

    async getConsultations(patient) {
      const consultationsStore = useConsultationsStore();
      const allConsultations = await consultationsStore.getConsultations();
      return allConsultations.filter(
        (consultation) => consultation.patient.id === patient.id
      );
    },

    async addPatient(patient) {
      const patientToAdd = { ...patient };
      patientToAdd.type = "Person";
      if (patientToAdd.bloodType) {
        patientToAdd.bloodType = patientToAdd.bloodType.id;
      }
      if (patientToAdd.autonomousCommunity) {
        patientToAdd.autonomousCommunity = patientToAdd.autonomousCommunity.id;
      }
      if (patientToAdd.photo && patientToAdd.photo.rawFile) {
        const response = await useFilesStore().uploadFile(
          patientToAdd.photo.rawFile
        );
        patientToAdd.photo = response.data._links.self.href;
      } else {
        patientToAdd.photo = null;
      }

      const patientReturned = await axios.post(
        import.meta.env.VITE_APP_API_URL + "patients",
        patientToAdd
      );

      const newPatient = patientReturned.data;
      newPatient.id = getIdFromLink(patientReturned.data._links.self.href);
      this.patients.push(newPatient);
    },

    async editPatient(editedPatient) {
      editedPatient.type = "Person";

      if (editedPatient.bloodType) {
        editedPatient.bloodType = editedPatient.bloodType.id;
      }
      if (editedPatient.autonomousCommunity) {
        editedPatient.autonomousCommunity =
          editedPatient.autonomousCommunity.id;
      }

      if (editedPatient.changedPhoto) {
        if (editedPatient.photo && editedPatient.photo.rawFile) {
          const response = await useFilesStore().uploadFile(
            editedPatient.photo.rawFile
          );
          editedPatient.photo = response.data._links.self.href;
        } else {
          editedPatient.photo = null;
        }
      } else {
        if (editedPatient.photo) editedPatient.photo = editedPatient.photo.id;
      }

      const editedPatientData = await axios.put(
        import.meta.env.VITE_APP_API_URL + "patients/" + editedPatient.id,
        editedPatient
      );

      editedPatient = editedPatientData.data;
      editedPatient = await this.loadPatientFields(editedPatient);
      this.patients = this.patients.map((patient) => {
        if (patient.id === editedPatient.id) {
          return editedPatient;
        }
        return patient;
      });
    },

    async deletePatient(id) {
      await axios.delete(import.meta.env.VITE_APP_API_URL + "patients/" + id);
      this.patients = this.patients.filter((patient) => patient.id !== id);
    },

    addConsultationToPatient(patientId, consultation) {
      this.patients = this.patients.map((patient) => {
        if (patient.id === patientId) {
          patient.consultations.push({ id: consultation.id });
        }
        return patient;
      });
    },

    removeConsultationFromPatient(patientId, consultationToDelete) {
      this.patients = this.patients.map((patient) => {
        if (patient.id === patientId) {
          patient.consultations = patient.consultations.filter(
            (consultation) => consultation.id !== consultationToDelete.id
          );
        }
        return patient;
      });
    },

    async getLatestSymptomsFromPatient(pat, amount) {
      return new Promise(async (resolve) => {
        const symptoms = [];
        let patient = await this.getPatientWithConsultations(pat);
        if (patient && patient.consultations) {
          const emergencies = patient.consultations.filter(
            (consultation) => consultation.consultationType === "Emergency"
          );

          const lastEmergencies = emergencies.sort(
            (e1, e2) =>
              new Date(e2.date).getTime() - new Date(e1.date).getTime()
          );

          let index = 0;

          for (const emergency of lastEmergencies) {
            emergency.symptoms = await useSymptomsStore().getSymptoms(
              emergency.id
            );

            for (const symptom of emergency.symptoms) {
              if (index === amount) {
                break;
              }
              if (!symptoms.find((s) => s.name === symptom.name)) {
                symptoms.push(symptom);
                index++;
              }
            }
          }
        }
        resolve(symptoms);
      });
    },

    async getVaccines(patient) {
      let vaccines = [];
      const vaccinesData = await axios.get(
        import.meta.env.VITE_APP_API_URL +
          "patients/" +
          patient.id +
          "/vaccines"
      );
      if (vaccinesData.data._embedded) {
        vaccines = vaccinesData.data._embedded.vaccinesPersons;
      }
      return await Promise.all(
        vaccines.map(async (vac) => {
          const vaccine = await axios.get(
            import.meta.env.VITE_APP_API_URL +
              "vaccines/" +
              getIdFromLink(vac._links.vaccine.href)
          );
          vac.id = getIdFromLink(vac._links.self.href);
          vac.vaccine = { id: vac._links.vaccine.href };
          vac.vaccineDetails = {
            id: vaccine.data._links.vaccinedetails.href.replace(
              "http://",
              "https://"
            ),
          };
          return vac;
        })
      );
    },

    async addVaccine(patient, vaccine) {
      await useVaccinesStore().addVaccine(patient, vaccine);
    },

    async editVaccine(editedVaccine) {
      await useVaccinesStore().editVaccine(editedVaccine);
    },

    async loadPatientFields(patient) {
      patient.id = getIdFromLink(patient._links.self.href);
      if (patient.autonomousCommunity) {
        patient.autonomousCommunity.id =
          patient.autonomousCommunity._links.self.href;
      }
      patient.bloodType = await useBloodTypesStore().getBloodTypeById(
        patient.bloodType
      );
      if (patient.photo) {
        patient.photo.id = patient.photo._links.self.href;
      }

      return patient;
    },
  },
});
