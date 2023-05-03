import { defineStore } from "pinia";

import data from "../data/patients.json";
import {
  deleteObject,
  getDownloadURL,
  getStorage,
  ref,
  uploadBytes,
} from "firebase/storage";
import { useFilesStore } from "@/store/filesStore.js";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import { useSymptomsStore } from "@/store/symptomsStore.js";
import { useVaccinesStore } from "@/store/vaccinesStore.js";

export const usePatientsStore = defineStore({
  id: "patientsStore",
  state: () => ({
    patients: [],
  }),
  actions: {
    async fetchPatients() {
      this.patients = await new Promise((resolve) => {
        setTimeout(() => {
          resolve([...data.patients]);
        }, 1000);
      });
      for (const patient of this.patients) {
        const filesStore = useFilesStore();
        if (patient.photo && patient.photo.id) {
          patient.photo = await filesStore.getFileById(patient.photo.id);
        } else {
          patient.photo = { id: 0, url: "" };
        }
      }
    },
    async getPatient(id) {
      if (this.patients.length === 0) {
        await this.fetchPatients();
      }
      return new Promise((resolve) => {
        const patient = this.patients.find(
          (patient) => patient.id === parseInt(id, 10)
        );
        resolve(patient);
      });
    },
    async getPatientWithConsultations(patient) {
      patient.consultations = await this.getConsultations(patient);
      return patient;
    },
    async getConsultations(patient) {
      const consultationsStore = useConsultationsStore();
      const allConsultations = await consultationsStore.getConsultations();
      console.log(patient);
      console.log(allConsultations);
      return allConsultations.filter(
        (consultation) => consultation.patient.id === patient.id
      );
    },
    async addPatient(patient) {
      patient.id = this.patients.length + 1;
      patient.consultations = [];
      const newPatient = await this.handlePatientPhoto(patient);
      this.patients.push(newPatient);
      return newPatient;
    },

    async editPatient(editedPatient) {
      if (editedPatient.changedPhoto) {
        if (editedPatient.photo && editedPatient.photo.url !== "") {
          try {
            await deleteObject(ref(getStorage(), editedPatient.photo.url));
          } catch (error) {
            throw error;
          }
          editedPatient.photo.url = "";
        }
        await this.handlePatientPhoto(editedPatient);
      }
      this.patients = this.patients.map((patient) => {
        if (patient.id === editedPatient.id) {
          return editedPatient;
        }
        return patient;
      });
    },
    async deletePatient(id) {
      return new Promise(async (resolve) => {
        const patientToDelete = this.patients.find(
          (patient) => patient.id === id
        );

        if (
          patientToDelete &&
          patientToDelete.photo &&
          patientToDelete.photo.url !== ""
        ) {
          try {
            await deleteObject(ref(getStorage(), patientToDelete.photo.url));
          } catch (error) {
            throw error;
          }
        }

        this.patients = this.patients.filter((patient) => patient.id !== id);
        resolve();
      });
    },
    async handlePatientPhoto(patient) {
      if (!patient.photo || !patient.photo.blob) {
        patient.previewImage = null;
      } else {
        const fileName = Date.now() + "_" + `${patient.photo.blob.name}`;
        const storage = getStorage();
        const storageRef = ref(storage, `patients/${fileName}`);

        try {
          const snapshot = await uploadBytes(
            storageRef,
            patient.photo.blob.rawFile
          );
          patient.photo.url = await getDownloadURL(snapshot.ref);
          patient.photo.id = useFilesStore().getFiles.length + 1;
          patient.photo.size = patient.photo.blob.size;
          patient.photo.name = fileName;
        } catch (error) {
          throw error;
        }
      }
      return patient;
    },
    addConsultationToPatient(patientId, consultation) {
      this.patients = this.patients.map((patient) => {
        if (patient.id === patientId) {
          patient.consultations.push({ id: consultation.id });
        }
        return patient;
      });
    },
    removeConsultationFromPatient(patientId, consultation) {
      this.patients = this.patients.map((patient) => {
        if (patient.id === patientId) {
          patient.consultations = patient.consultations.filter(
            (consultation) => consultation.id !== consultation.id
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
      const vaccines = [];

      for (const vaccine of patient.vaccines) {
        vaccines.push(await useVaccinesStore().getVaccine(vaccine.id));
      }

      return vaccines;
    },
    async addVaccine(patient, vaccine) {
      let id = await useVaccinesStore().addVaccine(patient, vaccine);
      console.log(vaccine);
      patient.vaccines.push({
        id: await useVaccinesStore().addVaccine(patient, vaccine),
        photo: vaccine.photo,
        reaction: vaccine.reaction,
      });
      console.log(patient.vaccines);
    },
  },
});
