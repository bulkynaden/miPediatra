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
import { usePatientsStore } from "@/store/patientsStore.js";

export default {
  name: "SymptomsComo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  emits: ["selected-item"],
  props: {
    patient: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      patientSymptoms: [],
      symptomsData: {
        fields: { groupBy: "state", text: "name", value: "id" },
        placeholder: "Seleccione un tipo de síntoma...",
        data: symptomsList.symptoms,
      },
    };
  },
  watch: {
    patient: {
      handler() {
        this.updateSymptoms();
      },
      immediate: true,
    },
  },
  methods: {
    async updateSymptoms() {
      const allSymptoms = symptomsList.symptoms.map((symptom) => ({
        ...symptom,
        state: "Todos",
      }));

      if (this.patient) {
        this.patientSymptoms = (
          await usePatientsStore().getLatestSymptomsFromPatient(this.patient, 5)
        ).map((symptom) => ({ ...symptom, state: "Recientes" }));

        this.symptomsData.data = [...this.patientSymptoms, ...allSymptoms];
      } else {
        this.patientSymptoms = [];
        this.symptomsData.data = [...allSymptoms];
      }
    },

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
