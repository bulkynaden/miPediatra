import { defineStore } from "pinia";

import data from "../data/patients.json";

import {
  deleteObject,
  getDownloadURL,
  getStorage,
  ref,
  uploadBytes,
} from "firebase/storage";

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
    },
    async handlePatientPhoto(patient) {
      if (!patient.photo || !patient.photo.rawFile) {
        patient.previewImage = null;
      } else {
        const timestamp = Date.now();
        const fileName = `${patient.id}_${timestamp}_${patient.photo.name}`;
        const storage = getStorage();
        const storageRef = ref(storage, `patients/${fileName}`);

        try {
          const snapshot = await uploadBytes(storageRef, patient.photo.rawFile);
          patient.photoUrl = await getDownloadURL(snapshot.ref);
        } catch (error) {
          console.log("Error uploading file", error);
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
        if (editedPatient.photoUrl !== "") {
          try {
            await deleteObject(ref(getStorage(), editedPatient.photoUrl));
          } catch (e) {
            console.log("Error deleting file");
          }
          editedPatient.photoUrl = "";
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

        if (patientToDelete && patientToDelete.photoUrl !== "") {
          await deleteObject(ref(getStorage(), patientToDelete.photoUrl));
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
