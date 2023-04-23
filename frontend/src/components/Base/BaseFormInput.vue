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
  props: ["modelValue", "text", "isValid", "errorMessage"],
  components: { "ejs-textbox": TextBoxComponent },
  emits: ["update:modelValue"],
  name: "BaseFormInput",
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
