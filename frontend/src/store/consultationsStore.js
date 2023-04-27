import { defineStore } from "pinia";

import data from "../data/consultations.json";
import { usePatientsStore } from "./patientsStore.js";
import { useFilesStore } from "@/store/filesStore.js";
import { getDownloadURL, getStorage, ref, uploadBytes } from "firebase/storage";

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

      for (const consultation of this.consultations) {
        consultation.patient = await usePatientsStore().getPatient(
          consultation.patient.id
        );
        for (let file of consultation.files) {
          file = useFilesStore().getFileById(file.id);
        }
      }
    },
    async handleConsultationFile(blobFile) {
      if (!blobFile) {
        return null;
      }

      const fileName = `${blobFile.name}`;
      const storage = getStorage();
      const storageRef = ref(storage, `consultations/${fileName}`);

      try {
        const snapshot = await uploadBytes(storageRef, blobFile.rawFile);
        let fileUploaded = {
          id: useFilesStore().getFiles.length + 1,
          size: blobFile.size,
          name: fileName,
        };
        fileUploaded.url = await getDownloadURL(snapshot.ref);
        return fileUploaded;
      } catch (error) {
        console.log("Error uploading file", error);
        return null;
      }
    },
    async addConsultation(consultation) {
      consultation.id = this.consultations.length + 1 + "";
      console.log(consultation);

      for (const file of consultation.blobFiles) {
        let fileUploaded = await this.handleConsultationFile(file);

        if (fileUploaded) {
          consultation.files.push(fileUploaded);
        }
      }
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
