<template>
  <div>
    <div class="text-left">
      <ejs-textbox
        ref="inputField"
        v-model="data"
        :class="validClass"
        :placeholder="text"
        floatLabelType="Auto"
        @blur="validate"
        @keydown.enter="validate"
      ></ejs-textbox>
    </div>
    <label v-show="!isValid" class="e-error">{{ errorMessage }}</label>
  </div>
</template>

<script>
import { TextBoxComponent } from "@syncfusion/ej2-vue-inputs";

export default {
  name: "BaseFormInput",
  components: { "ejs-textbox": TextBoxComponent },
  props: ["modelValue", "text", "isValid", "errorMessage"],
  emits: ["update:modelValue"],
  data() {
    return {
      data: this.modelValue,
      className: "",
      errorMessageToShow: "",
    };
  },
  computed: {
    validClass() {
      return !this.isValid ? "e-error" : "";
    },
  },
  methods: {
    validate() {
      this.$emit("update:modelValue", this.data);
    },
  },
};
</script>

<style scoped></style>
