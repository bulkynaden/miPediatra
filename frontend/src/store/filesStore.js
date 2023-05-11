import { defineStore } from "pinia";
import axios from "axios";

export const useFilesStore = defineStore({
  id: "filesStore",
  actions: {
    async uploadFile(rawFile) {
      const formData = new FormData();
      formData.append("file", rawFile);
      return await axios.post(
        import.meta.env.VITE_APP_API_URL + "upload",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
    },
  },
});
