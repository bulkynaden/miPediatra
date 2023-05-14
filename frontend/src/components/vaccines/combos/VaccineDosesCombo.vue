<template>
  <ejs-dropdownlist
    v-model="data"
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
import { useLoadingStore } from "@/store/loadingStore.js";

export default {
  name: "VaccineDosesCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["vaccineDetails"],
  emits: ["update:modelValue"],
  data() {
    return {
      data: this.modelValue,
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
      this.$emit("update:modelValue", e.itemData);
    },
    async updateDoses() {
      if (this.vaccineDetails.id !== -1) {
        useLoadingStore().setLoading(true);
        this.vaccinesData.data = await useVaccinesStore().getVaccinesDoses(
          this.vaccineDetails
        );
        useLoadingStore().setLoading(false);
      } else {
        this.vaccinesData.data = [];
      }
    },
  },
  async beforeMount() {
    await this.updateDoses();
  },
};
</script>

<style scoped></style>
