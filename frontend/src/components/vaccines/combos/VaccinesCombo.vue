<template>
  <ejs-dropdownlist
    :allowFiltering="true"
    :dataSource="vaccinesData.data"
    :fields="vaccinesData.fields"
    :filtering="onFiltering"
    :placeholder="vaccinesData.placeholder"
    locale="es"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { Predicate, Query } from "@syncfusion/ej2-data";
import { useVaccinesStore } from "@/store/vaccinesStore.js";

export default {
  name: "VaccinesCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  emits: ["selected-item"],
  data() {
    return {
      patientSymptoms: [],
      vaccinesData: {
        fields: { text: "name", value: "id" },
        placeholder: "Seleccione una vacuna...",
        data: [],
      },
    };
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("selected-item", e.itemData);
    },
    onFiltering(e) {
      let predicate = new Predicate("name", "contains", e.text, true);
      let query = new Query();
      query = query.where(predicate);
      e.updateData(this.vaccinesData.data, query);
    },
  },
  async beforeMount() {
    this.vaccinesData.data = await useVaccinesStore().getVaccinesDetails();
  },
};
</script>

<style scoped></style>
