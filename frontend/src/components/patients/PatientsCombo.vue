<template>
  <ejs-dropdownlist
    :dataSource="patients"
    :fields="patientData.fields"
    :itemTemplate="patientItemTemplate"
    :placeholder="patientData.placeholder"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { usePatientsStore } from "../../store/patientsStore.js";
import { publicImagesPath } from "../../router/publicPath.js";

export default {
  name: "PatientsCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  data() {
    return {
      patientData: {
        fields: { text: "name", value: "id" },
        placeholder: "Seleccione un paciente...",
      },
      patientItemTemplate:
        "<div class='flex items-center patient-item p-2'>" +
        "<img class='h-16 w-16 rounded-full shadow-md object-cover mr-4' src='${photoUrl}' alt='Foto'>" +
        "<span class='name text-lg font-semibold text-gray-800 capitalize'>${name}</span>" +
        "</div>",
    };
  },
  computed: {
    patients() {
      const patientStore = usePatientsStore();
      const patients = patientStore.getPatients;
      return patients.map((patient) => ({
        ...patient,
        photoUrl: this.photoSrc(patient),
      }));
    },
  },
  methods: {
    photoSrc(patient) {
      const patientToShow = patient;
      return patientToShow.photo && patientToShow.photo.url !== ""
        ? patientToShow.photo.url
        : patientToShow.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
    },
    onSelectionChange(e) {
      this.$emit("selected-item", e.itemData);
    },
  },
};
</script>

<style scoped></style>
