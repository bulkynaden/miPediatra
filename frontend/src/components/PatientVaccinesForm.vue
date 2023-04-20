<template>
  <div class="grid sm:grid-cols-1 md:grid-cols-3 gap-4">
    <BaseFormInput
      v-model="formData.name"
      :is-valid="isValidName"
      error-message="El nombre es obligatorio"
      text="Otro"
      @update:modelValue="validateName"
    />
    <div class="md:col-span-2">
      <BaseFormInput
        v-model="formData.lastName"
        :is-valid="isValidLastName"
        error-message="Los apellidos son obligatorios"
        text="Otross"
        @update:modelValue="validateLastName"
      />
    </div>
    <div class="md:col-span-3">
      <BaseFormInput
        v-model="formData.description"
        :is-valid="isValidDescription"
        error-message="La descripción debe tener más de 10 caracteres"
        text="Description"
        @update:modelValue="validateDescription"
      />
    </div>
  </div>
</template>

<script>
import BaseFormInput from "@/components/Base/BaseFormInput.vue";
import BaseBtn from "@/components/Base/BaseBtn.vue";

export default {
  name: "PatientVaccinesForm",
  components: { BaseBtn, BaseFormInput },
  props: ["formData"],
  data() {
    return {
      isValidName: true,
      isValidLastName: true,
      isValidDescription: true,
    };
  },
  methods: {
    validateName() {
      this.isValidName = this.formData.name;
    },
    validateLastName() {
      console.log("hola");
      this.isValidLastName = !(
        this.formData.lastName === "" || this.formData.lastName === null
      );
    },
    validateDescription() {
      this.isValidDescription = !(
        this.formData.description === "" ||
        this.formData.description.length < 10
      );
    },
    validateAll() {
      this.validateName();
      this.validateLastName();
      this.validateDescription();

      const isValid =
        this.isValidName && this.isValidLastName && this.isValidDescription;
      this.$emit("validation", isValid);
    },
  },
};
</script>

<style scoped></style>
