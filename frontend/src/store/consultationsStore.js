import { defineStore } from "pinia";

import data from "../data/consultations.json";

export const useConsultationsStore = defineStore({
  id: "consultationsStore",
  state: () => ({
    consultations: [],
  }),
  getters: {
    getConsultations() {
      return this.consultations;
    },
  },
  actions: {
    async fetchConsultations() {
      this.consultations = await new Promise((resolve) => {
        setTimeout(() => {
          resolve([...data.consultations]);
        }, 500);
      });
    },
    async addConsultation(consultation) {
      consultation.id = this.consultations.length + 1 + "";
      //const newConsultation = await this.handlePatientPhoto(consultation);
      this.consultations.push(consultation);
      return consultation;
    },

    async editConsultation(editedConsultation) {
      this.consultations = this.consultations.map((consultation) => {
        if (consultation.id === editedConsultation.id) {
          return editedConsultation;
        }
        return consultation;
      });
    },
    async deleteConsultation(id) {
      return new Promise(async (resolve) => {
        const consultationToDelete = this.consultations.find(
          (consultation) => consultation.id === id
        );

        /*
         * if (consultationToDelete && consultationToDelete.photoUrl !== "") {
         * await deleteObject(ref(getStorage(), consultationToDelete.photoUrl));
         * }
         */

        this.consultations = this.consultations.filter(
          (consultation) => consultation.id !== id
        );
        resolve();
      });
    },
    async getConsultation(id) {
      if (this.consultations.length === 0) {
        await this.fetchConsultations();
      }
      return new Promise((resolve) => {
        const consultation = this.consultations.find(
          (consultation) => consultation.id === id
        );
        resolve(consultation);
      });
    },
  },
});
