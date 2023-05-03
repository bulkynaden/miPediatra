<template>
  <ejs-dropdownlist
    v-model="data"
    :dataSource="genderData.data"
    :fields="genderData.fields"
    :itemTemplate="genderItemTemplate"
    :placeholder="genderData.placeholder"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import gendersList from "../../../data/genderData.json";

export default {
  name: "GendersCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["modelValue"],
  emits: ["update:modelValue"],
  data() {
    return {
      data: this.modelValue,
      genderItemTemplate:
        "<div>\n" +
        "<span class='${icon} pr-1 pl-1 e-avatar'>\n" +
        "</span>\n" +
        "<span class='gender'>${value}</span>\n" +
        "</div>",
      genderData: {
        fields: { text: "value", value: "id" },
        placeholder: "Sexo",
        data: gendersList,
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

<style scoped>
.gender {
  padding: 8px 42px;
  opacity: 0.87;
  line-height: 1.063em;
}
</style>
