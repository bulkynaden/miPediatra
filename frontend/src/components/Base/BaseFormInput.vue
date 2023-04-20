<template>
  <div>
    <div :class="validClass" class="e-input-group e-float-input text-left">
      <input
        ref="inputField"
        v-model="data"
        type="text"
        @blur="validate"
        @keydown.enter="validate"
      />
      <span class="e-float-line"></span>
      <label class="e-float-text">{{ text }}</label>
    </div>
    <label v-show="!isValid" class="e-error">{{ errorMessage }}</label>
  </div>
</template>

<script>
export default {
  props: ["modelValue", "text", "isValid", "errorMessage"],
  emits: ["update:modelValue"],
  name: "BaseFormInput",
  data() {
    return {
      data: "",
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
