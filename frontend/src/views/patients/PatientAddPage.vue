<template>
  <div class="container text-center">
    <Breadcrumbs parentTitle="Mis pacientes" subParentTitle="Añadir paciente" />

    <base-card class="max-w-3xl mx-auto w-auto">
      <h4 class="pb-4">Añadir paciente</h4>
      <form
        id="formAddChild"
        class="grid max-w-3xl w-auto mx-auto"
        method="post"
        @submit.prevent="submitForm"
      >
        <transition mode="out-in" name="scale">
          <keep-alive>
            <component
              :is="currentComponentPage"
              :key="currentStep"
              ref="formPage"
              :formData="formData"
              @validation="handleValidation"
            />
          </keep-alive>
        </transition>

        <div class="pt-4">
          <BaseBtn
            text="Anterior"
            type="button"
            @click="previousPage"
          ></BaseBtn>
          <BaseBtn text="Siguiente" type="button" @click="nextPage"></BaseBtn>
          <BaseBtn text="Aceptar" type="submit"></BaseBtn>
        </div>
      </form>
    </base-card>
  </div>
</template>

<script>
import BaseBtn from "@/components/Base/BaseBtn.vue";
import BaseCard from "@/components/Base/BaseCard.vue";
import BaseFormInput from "@/components/Base/BaseFormInput.vue";
import PatientGeneralForm from "@/components/PatientGeneralForm.vue";
import PatientVaccinesForm from "@/components/PatientVaccinesForm.vue";
import { markRaw } from "vue";

export default {
  name: "PatientAddPage",
  computed: {
    currentComponentPage() {
      return this.getCurrentPage();
    },
  },
  components: { BaseFormInput, BaseCard, BaseBtn },
  data: function () {
    return {
      formData: {
        name: "",
        lastName: "",
        description: "",
      },
      formIsValid: false,
      pages: [markRaw(PatientGeneralForm), markRaw(PatientVaccinesForm)],
      currentStep: 0,
      currentComponent: PatientGeneralForm,
    };
  },
  methods: {
    getCurrentPage() {
      return this.pages[this.currentStep];
    },
    nextPage() {
      this.currentStep++;
    },
    previousPage() {
      this.currentStep--;
    },
    submitForm() {
      this.$refs.formPage.validateAll();
    },
    handleValidation(isValid) {
      this.formIsValid = isValid;
    },
  },
};
</script>

<style scoped>
.scale-enter-active,
.scale-leave-active {
  transition: all 0.5s ease;
}

.scale-enter-from,
.scale-leave-to {
  opacity: 0;
  transform: scale(0.9);
}
</style>
