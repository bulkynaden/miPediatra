import { defineStore } from "pinia";
import data from "@/data/bloodTypeData.json";

export const useBloodTypesStore = defineStore({
  id: "bloodTypesStore",
  state: () => ({
    bloodTypes: [],
  }),
  actions: {
    async fetchBloodTypes() {
      this.bloodTypes = [...data];
    },
    async loadData() {
      if (this.bloodTypes === null || this.bloodTypes.length === 0) {
        await this.fetchBloodTypes();
      }
    },
    async getBloodTypeById(id) {
      await this.loadData();
      return this.bloodTypes.find((bloodType) => bloodType.id === id);
    },
    async getBloodTypeByName(name) {
      await this.loadData();
      return this.bloodTypes.find((bloodType) => bloodType.name === name);
    },
  },
});
