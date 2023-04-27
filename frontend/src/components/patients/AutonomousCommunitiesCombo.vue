<template>
  <ejs-dropdownlist
    v-model="data"
    :dataSource="comboData.data"
    :fields="comboData.fields"
    :filtering="onFiltering"
    :placeholder="comboData.placeholder"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import autonomousCommunities from "../../data/autonomousCommunitiesData.json";
import { Query } from "@syncfusion/ej2-data";

export default {
  name: "AutonomousCommunitiesCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["modelValue"],
  emits: ["update:modelValue"],
  data() {
    return {
      data: this.modelValue,
      comboData: {
        fields: {
          text: "name",
          value: "id",
        },
        placeholder: "Comunidad autónoma",
        data: autonomousCommunities,
      },
    };
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("update:modelValue", this.data);
    },
    onFiltering(e) {
      let query = new Query();
      query =
        e.text !== "" ? query.where("name", "contains", e.text, true) : query;
      e.updateData(this.comboData.data, query);
    },
  },
};
</script>

<style scoped></style>
