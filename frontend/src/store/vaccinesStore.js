import { defineStore } from "pinia";
import vaccinesData from "@/data/vaccines.json";
import vaccinesPatientData from "@/data/vaccinesPatients.json";
import vaccinesDetailsData from "@/data/vaccineDetails.json";
import {
  deleteObject,
  getDownloadURL,
  getStorage,
  ref,
  uploadBytes,
} from "firebase/storage";
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
      let newId = this.nextId;
      this.nextId++;
      await this.handlePhoto(vaccine);
      this.vaccinesPatients.push({
        id: newId,
        vaccine: {
          id: vaccine.vaccine,
          vaccineDetails: { id: vaccine.vaccineDetails },
        },
        patient: { id: patient.id },
        date: vaccine.date,
        expectedDate: vaccine.expectedDate,
        photo: vaccine.photo,
        reaction: vaccine.reaction,
        hasBeenAdministered: vaccine.hasBeenAdministered,
      });
      return newId;
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
