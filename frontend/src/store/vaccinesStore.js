import { defineStore } from "pinia";
import axios from "axios";
import getIdFromLink from "@/services/parsers.js";
import { useFilesStore } from "@/store/filesStore.js";
import vaccineDetails from "@/components/vaccines/VaccineDetails.vue";

export const useVaccinesStore = defineStore({
  id: "vaccinesStore",
  state: () => ({
    vaccines: [],
    vaccinesDetails: [],
    nextId: 2,
  }),
  actions: {
    async loadData() {
      if (this.vaccines === null || this.vaccines.length === 0) {
        await this.fetchVaccines();
      }
    },
    async loadVaccinesDetails() {
      if (this.vaccinesDetails === null || this.vaccinesDetails.length === 0) {
        await this.fetchVaccinesDetails();
      }
    },
    async fetchVaccines() {
      const response = await axios.get(
        import.meta.env.VITE_APP_API_URL + "vaccines"
      );

      if (response.data._embedded) {
        this.vaccines = response.data._embedded.vaccines.map((vaccine) => {
          const selfLink = vaccine._links.self.href;
          return {
            id: getIdFromLink(selfLink),
            ...vaccine,
          };
        });
      } else {
        this.vaccines = [];
      }
    },

    async fetchVaccinesDetails() {
      const vaccinesDetailsData = await axios.get(
        import.meta.env.VITE_APP_API_URL + "vaccineDetails"
      );
      if (vaccinesDetailsData.data._embedded) {
        this.vaccinesDetails =
          vaccinesDetailsData.data._embedded.vaccinesDetails.map((vaccine) => {
            const selfLink = vaccine._links.self.href;
            return {
              id: selfLink,
              ...vaccine,
            };
          });
      } else {
        this.vaccinesDetails = [];
      }
    },
    async getVaccines() {
      await this.loadData();
      return this.vaccines;
    },

    async getVaccinesDetails() {
      await this.loadVaccinesDetails();
      return this.vaccinesDetails;
    },

    async getVaccinesDoses(vac) {
      let doses = [];
      const vaccinesDosesData = await axios.get(
        import.meta.env.VITE_APP_API_URL +
          "vaccines/" +
          getIdFromLink(vac.id) +
          "/doses"
      );
      if (vaccinesDosesData.data._embedded) {
        doses = vaccinesDosesData.data._embedded.vaccines.map((vaccine) => {
          const selfLink = vaccine._links.self.href;
          return {
            id: selfLink,
            ...vaccine,
          };
        });
      } else {
        doses = [];
      }
      return doses;
    },
    async addVaccine(patient, vaccine) {
      const vaccineToAdd = {
        ...vaccine,
        vaccine: vaccine.vaccine.id,
        person: patient._links.self.href,
        vaccineDetails: vaccineDetails.id,
      };

      if (vaccineToAdd.photo && vaccineToAdd.photo.rawFile) {
        const response = await useFilesStore().uploadFile(
          vaccineToAdd.photo.rawFile
        );
        vaccineToAdd.photo = response.data._links.self.href;
      } else {
        vaccineToAdd.photo = null;
      }

      const vaccineToAddData = await axios.post(
        import.meta.env.VITE_APP_API_URL + "vaccines_persons",
        vaccineToAdd
      );
      let addedVaccine;
      if (vaccineToAddData.data._embedded) {
        addedVaccine = vaccineToAddData.data;
        addedVaccine.id = getIdFromLink(vaccineToAddData.data._links.self.href);

        const vaccineId = getIdFromLink(addedVaccine._links.vaccine.href);

        const vaccine = await axios.get(
          import.meta.env.VITE_APP_API_URL +
            "vaccines/" +
            getIdFromLink(addedVaccine._links.vaccine.href)
        );

        addedVaccine.vaccine = { id: vaccineId };
        addedVaccine.vaccineDetails = {
          id: getIdFromLink(vaccine._links.vaccinedetails.href),
        };
      }
      return addedVaccine;
    },
    async editVaccine(editedVaccine) {
      const vaccineToEdit = {
        ...editedVaccine,
        vaccine: editedVaccine.vaccine.id,
        vaccineDetails: editedVaccine.vaccineDetails.id,
      };

      if (vaccineToEdit.changedPhoto) {
        if (vaccineToEdit.photo && vaccineToEdit.photo.rawFile) {
          const response = await useFilesStore().uploadFile(
            vaccineToEdit.photo.rawFile
          );
          vaccineToEdit.photo = response.data._links.self.href;
        } else {
          vaccineToEdit.photo = null;
        }
      } else {
        if (vaccineToEdit.photo) vaccineToEdit.photo = vaccineToEdit.photo.id;
      }

      const response = await axios.put(
        import.meta.env.VITE_APP_API_URL +
          "vaccines_persons/" +
          editedVaccine.id,
        vaccineToEdit
      );
      return response.data;
    },
    async deleteVaccine(vaccineToDelete) {
      await axios.delete(
        import.meta.env.VITE_APP_API_URL +
          "vaccines_persons/" +
          vaccineToDelete.id
      );
    },
  },
});
