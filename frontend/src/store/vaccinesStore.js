import { defineStore } from "pinia";
import { deleteObject, getStorage, ref } from "firebase/storage";
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

    async getVaccinesDoses(vaccineDetails) {
      let doses = [];
      const vaccinesDosesData = await axios.get(
        import.meta.env.VITE_APP_API_URL +
          "vaccines/" +
          getIdFromLink(vaccineDetails.id) +
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

    async getVaccine(id) {
      let vaccineToReturn;
      let foundVaccine;
      let vaccineDetails;
      for (const vaccine of this.vaccinesPatients) {
        if (vaccine.id === id) {
          foundVaccine = vaccine;
        }
      }
      if (foundVaccine) {
        let vaccineData = this.vaccines.find(
          (vaccine) => vaccine.id === foundVaccine.vaccine.id
        );

        vaccineDetails = this.vaccinesDetails.find(
          (vaccineDetails) =>
            vaccineDetails.id === vaccineData.vaccineDetails.id
        );

        if (vaccineDetails) {
          vaccineToReturn = {
            ...foundVaccine,
            vaccineDetails: vaccineDetails,
            vaccine: vaccineData,
          };
        }
      }
      return vaccineToReturn;
    },
    async addVaccine(patient, vaccine) {
      const vaccineToAdd = {
        ...vaccine,
        vaccine: vaccine.vaccine.id,
        vaccineDetails: vaccineDetails.id,
      };

      console.log(vaccine);
      if (vaccineToAdd.photo && vaccineToAdd.photo.rawFile) {
        const response = await useFilesStore().uploadFile(
          vaccineToAdd.photo.rawFile
        );
        vaccineToAdd.photo = response.data._links.self.href;
      } else {
        vaccineToAdd.photo = null;
      }

      const patientId = getIdFromLink(patient._links.self.href);
      const vaccineToAddData = await axios.post(
        import.meta.env.VITE_APP_API_URL +
          "patients/" +
          patientId +
          "/add-vaccine",
        vaccineToAdd
      );
      let addedVaccine;
      if (vaccineToAddData.data._embedded) {
        addedVaccine = vaccineToAddData.data;
        addedVaccine.id = getIdFromLink(vaccineToAddData.data._links.self.href);
      }
      return addedVaccine;
    },
    async editVaccine(editedVaccine) {
      if (editedVaccine.changedPhoto) {
        if (
          editedVaccine.photo &&
          editedVaccine.photo.url &&
          editedVaccine.photo.url !== ""
        ) {
          try {
            await deleteObject(ref(getStorage(), editedVaccine.photo.url));
          } catch (error) {
            throw error;
          }
          editedVaccine.photo.url = "";
        }
        await this.handlePhoto(editedVaccine);
      }

      let foundVaccine = this.vaccinesPatients.find(
        (vaccine) => vaccine.id === editedVaccine.id
      );

      if (foundVaccine) {
        foundVaccine.vaccine.id = editedVaccine.vaccine;
        foundVaccine.date = editedVaccine.date;
        foundVaccine.expectedDate = editedVaccine.expectedDate;
        foundVaccine.photo = editedVaccine.photo;
        foundVaccine.reaction = editedVaccine.reaction;
        foundVaccine.hasBeenAdministered = editedVaccine.hasBeenAdministered;
        foundVaccine.id = editedVaccine.id;
      }
      return foundVaccine;
    },
    async deleteVaccine(patient, vaccineToDelete) {
      let id = vaccineToDelete.id;
      let foundVaccine = this.vaccinesPatients.find(
        (vaccine) => vaccine.id === id
      );
      if (foundVaccine) {
        if (
          foundVaccine.photo &&
          foundVaccine.photo.url &&
          foundVaccine.photo.url !== ""
        ) {
          try {
            await deleteObject(ref(getStorage(), foundVaccine.photo.url));
          } catch (error) {
            throw error;
          }
        }
        this.vaccinesPatients.splice(
          this.vaccinesPatients.indexOf(foundVaccine),
          1
        );

        for (const vaccine of patient.vaccines) {
          if (vaccine.id === id) {
            patient.vaccines.splice(patient.vaccines.indexOf(vaccine), 1);
          }
        }
      }
      return foundVaccine;
    },
  },
});
