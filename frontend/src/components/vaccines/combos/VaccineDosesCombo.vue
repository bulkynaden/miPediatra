<template>
  <ejs-dropdownlist
    :dataSource="vaccinesData.data"
    :fields="vaccinesData.fields"
    :placeholder="vaccinesData.placeholder"
    locale="es"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { useVaccinesStore } from "@/store/vaccinesStore.js";

export default {
  name: "VaccineDosesCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["vaccineDetails"],
  emits: ["selected-item"],
  data() {
    return {
      patientSymptoms: [],
      vaccinesData: {
        fields: { text: "dose", value: "id" },
        placeholder: "Seleccione una dosis...",
        data: [],
      },
    };
  },
  watch: {
    vaccineDetails: {
      handler() {
        this.updateDoses();
      },
    },
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("selected-item", e.itemData);
    },
    updateDoses() {
      this.vaccinesData.data = useVaccinesStore()
        .getVaccines()
        .filter(
          (vaccine) => vaccine.vaccineDetails.id === this.vaccineDetails.id
        );
    },
  },
  async beforeMount() {
    this.updateDoses();
  },
};
</script>

<style scoped></style>
