import { defineStore } from "pinia";
import consultationsSymptomsData from "@/data/consultationsSymptoms.json";
import symptomsData from "@/data/symptoms.json";

export const useSymptomsStore = defineStore({
  id: "symptomsStore",
  state: () => ({
    symptoms: [...symptomsData.symptoms],
    consultationsSymptoms: [...consultationsSymptomsData.consultationsSymptoms],
    nextId: 3,
  }),
  actions: {
    async getSymptom(id) {
      let symptomToReturn;
      for (const symptom of this.consultationsSymptoms) {
        if (symptom.id === id) {
          for (const s of this.symptoms) {
            if (s.id === symptom.symptom) {
              symptomToReturn = {
                id: s.id,
                name: s.name,
                description: symptom.description,
              };
            }
          }
        }
      }
      return symptomToReturn;
    },
    async getSymptoms(consultationId) {
      const consultationSymptoms = [];
      for (const symptom of this.consultationsSymptoms) {
        if (symptom.consultation === consultationId) {
          for (const s of this.symptoms) {
            if (s.id === symptom.symptom) {
              consultationSymptoms.push({
                id: s.id,
                name: s.name,
                description: symptom.description,
              });
            }
          }
        }
      }
      return consultationSymptoms;
    },
    async addSymptom(consultationId, symptomId, symptomDescription) {
      const newId = this.nextId;
      this.consultationsSymptoms.push({
        id: newId,
        consultation: consultationId,
        symptom: symptomId,
        description: symptomDescription,
      });
      this.nextId++;
      return newId;
    },

    async editSymptoms(consultation) {
      this.consultationsSymptoms = this.consultationsSymptoms.filter(
        (cs) => cs.consultation !== consultation.id
      );
      consultation.consultationsSymptoms = [];
      for (const symptom of consultation.symptoms) {
        consultation.consultationsSymptoms.push(
          await this.addSymptom(
            consultation.id,
            symptom.id,
            symptom.description
          )
        );
      }
    },
  },
});
