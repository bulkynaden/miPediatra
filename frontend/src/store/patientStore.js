import { defineStore } from "pinia";

import data from "../data/patients.json";

import { getDownloadURL, getStorage, ref, uploadBytes } from "firebase/storage";

export const usePatientStore = defineStore({
  id: "patientStore",
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
        }, 500);
      });
    },
    async addPatient(patient) {
      patient.id = this.patients.length + 1 + "";
      return new Promise((resolve) => {
        const timestamp = Date.now();
        const fileName = `${patient.id}_${timestamp}_${patient.photo.name}`;
        const storage = getStorage();
        const storageRef = ref(storage, `patients/${fileName}`);
        // 'file' comes from the Blob or File API
        uploadBytes(storageRef, patient.photo.rawFile)
          .then((snapshot) => {
            getDownloadURL(snapshot.ref).then((url) => {
              patient.photoUrls.push(url);

              this.patients.push(patient);
              resolve(patient);
            });
          })
          .catch((error) => {
            console.log("Error uploading file", error);
          });
      });
    },
    async editPatient(editedPatient) {
      return new Promise((resolve) => {
        this.patients = this.patients.map((patient) => {
          if (patient.id === editedPatient.id) {
            return editedPatient;
          }
          return patient;
        });
        resolve();
      });
    },
    async deletePatient(id) {
      return new Promise((resolve) => {
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
