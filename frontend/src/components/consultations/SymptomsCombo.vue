<template>
  <ejs-dropdownlist
    :allowFiltering="true"
    :dataSource="symptomsData.data"
    :fields="symptomsData.fields"
    :filtering="onFiltering"
    :placeholder="symptomsData.placeholder"
    locale="es"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import symptomsList from "@/data/symptoms.json";
import { Predicate, Query } from "@syncfusion/ej2-data";

export default {
  name: "SymptomsComo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  emits: ["selected-item"],
  data() {
    return {
      symptomsData: {
        fields: { text: "name", value: "id" },
        placeholder: "Seleccione un tipo de síntoma...",
        data: symptomsList.symptoms,
      },
    };
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("selected-item", e.itemData);
    },
    onFiltering(e) {
      let predicate = new Predicate("name", "contains", e.text, true);

      if (e.text !== "") {
        const customPredicate = new Predicate("name", "equal", "Otros", true);
        predicate = predicate.or(customPredicate);
      }

      let query = new Query();
      query = query.where(predicate);
      e.updateData(this.symptomsData.data, query);
    },
  },
};
</script>

<style scoped></style>
