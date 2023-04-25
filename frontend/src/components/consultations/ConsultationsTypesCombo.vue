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
  data() {
    return {
      consultationsTypes: [
        {
          id: 0,
          type: "RegularVisit",
          text: "Consulta rutinaria",
        },
        {
          id: 1,
          type: "Emergency",
          text: "Emergencias",
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
};
</script>

<style scoped></style>
