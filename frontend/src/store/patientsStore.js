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

export const usePatientsStore = defineStore({
  id: "patientsStore",
  state: () => ({
    patients: [],
  }),
  getters: {
    getPatients() {
      return this.patients;
    },
  },
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
    async addPatient(patient) {
      patient.id = this.patients.length + 1 + "";
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
    async getPatient(id) {
      if (this.patients.length === 0) {
        await this.fetchPatients();
      }
      return new Promise((resolve) => {
        const patient = this.patients.find((patient) => patient.id === id);
        resolve(patient);
      });
    },
  },
});
