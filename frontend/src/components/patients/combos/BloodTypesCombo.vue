<template>
  <ejs-dropdownlist
    v-model="model"
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
      model: this.modelValue,
      comboData: {
        fields: { text: "name", value: "id" },
        placeholder: "Grupo sanguíneo",
        data: bloodTypes,
      },
    };
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("update:modelValue", e.itemData);
    },
  },
  beforeMount() {
    if (this.modelValue) {
      this.model = this.modelValue.id;
    }
  },
};
</script>

<style scoped></style>
