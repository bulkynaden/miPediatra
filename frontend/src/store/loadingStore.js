import { defineStore } from "pinia";

export const useLoadingStore = defineStore({
  id: "loadingStore",
  state: () => ({
    loading: false,
  }),
  actions: {
    setLoading(loading) {
      this.loading = loading;
    },
  },
});
