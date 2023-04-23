import { defineStore } from "pinia";

import data from "../data/patients.json";

export const usePatientStore = defineStore({
  id: "patientStore",
  state: () => ({
    patients: data.patients,
  }),
  getters: {
    getPatients() {
      return this.patients;
    },
  },
  actions: {
    addPatient(patient) {
      this.patients.push(patient);
      return true;
    },
    editPatient(editedPatient) {
      this.patients = this.patients.map((patient) => {
        if (patient.id === editedPatient.id) {
          return editedPatient;
        }
        return patient;
      });
    },
    deletePatient(patientToDelete) {
      this.patients = this.patients.filter(
        (patient) => patient.id !== patientToDelete.id
      );
    },
  },
});
