<template>
  <ejs-dropdownlist
    :dataSource="getConsultationsTypes"
    :fields="consultationData.fields"
    :placeholder="consultationData.placeholder"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";

export default {
  name: "ConsultationsTypeCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["mode"],
  emits: ["selected-item"],
  data() {
    return {
      consultationsTypes: [
        {
          id: 0,
          type: "RegularVisit",
          text: "Visita rutinaria",
        },
        {
          id: 1,
          type: "Emergency",
          text: "Emergencia",
        },
      ],
      consultationData: {
        fields: { text: "text", value: "type" },
        placeholder: "Seleccione un tipo de consulta...",
      },
    };
  },
  computed: {
    getConsultationsTypes() {
      return this.consultationsTypes;
    },
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("selected-item", e.itemData);
    },
  },
  beforeMount() {
    if (this.mode === "filter") {
      this.consultationsTypes.unshift({
        id: 2,
        type: "All",
        text: "Todas",
      });
    }
  },
};
</script>

<style scoped></style>
