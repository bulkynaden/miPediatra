import { defineStore } from "pinia";
import axios from "axios";

export const useFilesStore = defineStore({
  id: "filesStore",
  actions: {
    async uploadFile(rawFile) {
      const formData = new FormData();
      formData.append("file", rawFile);
      return await axios.post("http://localhost:8080/api/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
    },
  },
});
