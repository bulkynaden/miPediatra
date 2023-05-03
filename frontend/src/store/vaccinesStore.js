import { defineStore } from "pinia";
import vaccinesData from "@/data/vaccines.json";
import vaccinesPatientData from "@/data/vaccinesPatients.json";
import vaccinesDetailsData from "@/data/vaccineDetails.json";
import { getDownloadURL, getStorage, ref, uploadBytes } from "firebase/storage";
import { useFilesStore } from "@/store/filesStore.js";

export const useVaccinesStore = defineStore({
  id: "vaccinesStore",
  state: () => ({
    vaccines: [...vaccinesData.vaccines],
    vaccinesPatients: [...vaccinesPatientData.vaccinesPatients],
    vaccinesDetails: [...vaccinesDetailsData.vaccineDetails],
    nextId: 2,
  }),
  actions: {
    getVaccines() {
      if (this.vaccines.length === 0) {
        this.vaccines = [...vaccinesData.vaccines];
      }
      return this.vaccines;
    },
    getVaccinesDetails() {
      if (this.vaccinesDetails.length === 0) {
        this.vaccinesDetails = [...vaccinesData.vaccines];
      }
      return this.vaccinesDetails;
    },
    async addVaccine(patient, vaccine) {
      let newId = this.nextId;
      this.nextId++;
      await this.handlePhoto(vaccine);
      this.vaccinesPatients.push({
        id: newId,
        vaccine: { id: vaccine.vaccine.id },
        patient: { id: patient.id },
        date: vaccine.date,
        expectedDate: vaccine.expectedDate,
        photo: vaccine.photo,
        reaction: vaccine.reaction,
        hasBeenAdministered: vaccine.hasBeenAdministered,
      });
      return newId;
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
            ...vaccineDetails,
            dose: vaccineData.dose,
          };
        }
      }
      return vaccineToReturn;
    },
    async handlePhoto(vaccine) {
      if (vaccine.photo && vaccine.photo.blob) {
        const fileName = Date.now() + "_" + `${vaccine.photo.blob.name}`;
        const storage = getStorage();
        const storageRef = ref(storage, `vaccines/${fileName}`);

        try {
          const snapshot = await uploadBytes(
            storageRef,
            vaccine.photo.blob.rawFile
          );
          vaccine.photo.url = await getDownloadURL(snapshot.ref);
          vaccine.photo.id = useFilesStore().getFiles.length + 1;
          vaccine.photo.size = vaccine.photo.blob.size;
          vaccine.photo.name = fileName;
        } catch (error) {
          throw error;
        }
        return vaccine;
      }
    },
  },
});
