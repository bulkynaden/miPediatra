<template>
  <ejs-dropdownlist
    v-model="data"
    :dataSource="comboData.data"
    :fields="comboData.fields"
    :placeholder="comboData.placeholder"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import bloodTypes from "@/data/bloodTypeData.json";

export default {
  name: "BloodTypesCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["modelValue"],
  emits: ["update:modelValue"],
  data() {
    return {
      data: this.modelValue,
      comboData: {
        fields: { text: "value", value: "value" },
        placeholder: "Grupo sanguíneo",
        data: bloodTypes,
      },
    };
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("update:modelValue", this.data);
    },
  },
};
</script>

<style scoped></style>
