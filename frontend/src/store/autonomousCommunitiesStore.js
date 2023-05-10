import { defineStore } from "pinia";
import axios from "axios";

export const useAutonomousCommunitiesStore = defineStore({
  id: "autonomousCommunitiesStore",
  state: () => ({
    autonomousCommunities: [],
  }),
  actions: {
    async fetchAutonomousCommunities() {
      const response = await axios.get(
        "http://localhost:8080/api/autonomous_communities"
      );
      this.autonomousCommunities =
        response.data._embedded.autonomousCommunityListModels.map((ac) => {
          const selfLink = ac._links.self.href;
          return {
            id: selfLink,
            ...ac,
          };
        });
    },
    async loadData() {
      if (
        this.autonomousCommunities === null ||
        this.autonomousCommunities.length === 0
      ) {
        await this.fetchAutonomousCommunities();
      }
    },
    async getAutonomousCommunities() {
      await this.loadData();
      return this.autonomousCommunities;
    },
    async getAutonomousCommunityName(id) {
      await this.loadData();
      return this.autonomousCommunities.find((ac) => ac.id === id).name;
    },
  },
});
