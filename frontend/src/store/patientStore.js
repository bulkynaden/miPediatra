import { defineStore } from "pinia";

import data from "../data/patients.json";

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
        this.patients.push(patient);
        resolve(true);
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
    async deletePatient(patientToDelete) {
      return new Promise((resolve) => {
        this.patients = this.patients.filter(
          (patient) => patient.id !== patientToDelete.id
        );
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
