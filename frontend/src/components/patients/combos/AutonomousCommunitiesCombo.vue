<template>
  <ejs-dropdownlist
    v-model="model"
    :allowFiltering="true"
    :dataSource="comboData.data"
    :fields="comboData.fields"
    :filtering="onFiltering"
    :placeholder="comboData.placeholder"
    locale="es"
    popupHeight="450px"
    @change="onSelectionChange"
  >
  </ejs-dropdownlist>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { Query } from "@syncfusion/ej2-data";
import { useAutonomousCommunitiesStore } from "@/store/autonomousCommunitiesStore.js";

export default {
  name: "AutonomousCommunitiesCombo",
  components: { "ejs-dropdownlist": DropDownListComponent },
  props: ["modelValue"],
  emits: ["update:modelValue"],
  data() {
    return {
      model: this.modelValue,
      comboData: {
        fields: {
          text: "name",
          value: "id",
        },
        placeholder: "Comunidad autónoma",
        data: null,
      },
    };
  },
  methods: {
    onSelectionChange(e) {
      this.$emit("update:modelValue", e.itemData);
    },
    onFiltering(e) {
      let query = new Query();
      query =
        e.text !== "" ? query.where("name", "contains", e.text, true) : query;
      e.updateData(this.comboData.data, query);
    },
  },
  async beforeMount() {
    if (this.modelValue) {
      this.model = this.modelValue.id;
    }
    const store = useAutonomousCommunitiesStore();
    this.comboData.data = await store.getAutonomousCommunities();
  },
};
</script>

<style scoped></style>
