import { defineStore } from "pinia";
import data from "../data/files.json";

export const useFilesStore = defineStore({
  id: "filesStore",
  state: () => ({
    files: [],
  }),
  getters: {
    getFiles() {
      return this.files;
    },
  },
  actions: {
    async fetchFiles() {
      this.files = data.files;
    },
    async getFileById(id) {
      return this.files.find((file) => file.id === id);
    },
  },
});
