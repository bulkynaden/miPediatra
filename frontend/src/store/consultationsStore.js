import { defineStore } from "pinia";

import data from "../data/consultations.json";
import { usePatientsStore } from "./patientsStore.js";
import { useFilesStore } from "@/store/filesStore.js";
import {
  deleteObject,
  getDownloadURL,
  getStorage,
  ref,
  uploadBytes,
} from "firebase/storage";
import { useSymptomsStore } from "@/store/symptomsStore.js";

export const useConsultationsStore = defineStore({
  id: "consultationsStore",
  state: () => ({
    consultations: [],
  }),
  actions: {
    async getConsultations() {
      if (this.consultations.length === 0) {
        await this.fetchConsultations();
      }
      return this.consultations;
    },
    async fetchConsultations() {
      this.consultations = [...data.consultations];

      for (const consultation of this.consultations) {
        consultation.patient = await usePatientsStore().getPatient(
          consultation.patient.id
        );
        for (let file of consultation.files) {
          file = useFilesStore().getFileById(file.id);
        }
      }
    },
    async getConsultation(id) {
      if (this.consultations.length === 0) {
        await this.fetchConsultations();
      }
      return new Promise(async (resolve) => {
        const consultation = this.consultations.find(
          (consultation) => consultation.id === parseInt(id, 10)
        );
        consultation.symptoms = await useSymptomsStore().getSymptoms(
          consultation.id
        );
        resolve(consultation);
      });
    },
    async getConsultationCopy(id) {
      if (this.consultations.length === 0) {
        await this.fetchConsultations();
      }
      return new Promise(async (resolve) => {
        const consultation = this.consultations.find(
          (consultation) => consultation.id === parseInt(id, 10)
        );
        consultation.symptoms = await useSymptomsStore().getSymptoms(
          consultation.id
        );

        const consultationCopy = { ...consultation };
        resolve(consultationCopy);
      });
    },
    async addConsultation(consultation) {
      consultation.id = this.consultations.length + 1;

      for (const file of consultation.blobFiles) {
        let fileUploaded = await this.handleConsultationFile(file);

        if (fileUploaded) {
          consultation.files.push(fileUploaded);
        }
      }

      //TODO: AÑADIR LA CONSULTA EN LA BASE DE DATOS
      usePatientsStore().addConsultationToPatient(
        consultation.patient.id,
        consultation
      );

      consultation.consultationsSymptoms = [];
      for (const symptom of consultation.symptoms) {
        consultation.consultationsSymptoms.push(
          await useSymptomsStore().addSymptom(
            consultation.id,
            symptom.id,
            symptom.description
          )
        );
      }
      this.consultations.push(consultation);
      return consultation;
    },

    async editConsultation(editedConsultation) {
      //TODO: ACTUALIZAR LA CONSULTA EN LA BASE DE DATOS
      await useSymptomsStore().editSymptoms(editedConsultation);
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

        for (const file of consultationToDelete.files) {
          await deleteObject(ref(getStorage(), file.url));
        }

        //TODO: BORRAR LA CONSULTA EN LA BASE DE DATOS
        usePatientsStore().removeConsultationFromPatient(
          consultationToDelete.patient.id,
          consultationToDelete
        );
        this.consultations = this.consultations.filter(
          (consultation) => consultation.id !== id
        );
        resolve();
      });
    },
    async handleConsultationFile(blobFile) {
      if (!blobFile) {
        return null;
      }

      const fileName = Date.now() + "_" + `${blobFile.name}`;
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
        return null;
      }
    },
    async uploadFile(file, consultation) {
      let fileUploaded = await this.handleConsultationFile(file);

      await consultation.files.push(fileUploaded);
      //TODO: ACTUALIZAR LA CONSULTA EN LA BASE DE DATOS
    },
    async deleteFile(file, consultation) {
      let index = consultation.files.findIndex((f) => f.url === file.url);
      if (index !== -1) {
        consultation.files.splice(index, 1);
        try {
          await deleteObject(ref(getStorage(), file.url));
        } catch (error) {
          throw error;
        }
        //TODO: ACTUALIZAR LA CONSULTA EN LA BASE DE DATOS
      }
    },
  },
});
