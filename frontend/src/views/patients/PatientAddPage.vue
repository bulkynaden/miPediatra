<template>
  <div class="container mx-auto">
    <BreadCrumbs parentTitle="Mis pacientes" subParentTitle="Añadir paciente" />
    <BaseCard class="max-w-3xl mx-auto w-auto">
      <template #cardHeader>Añadir paciente</template>
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
        <div class="pt-4 flex justify-between">
          <div>
            <BaseBtn
              v-if="hasPreviousPage"
              text="Anterior"
              type="button"
              @click="previousPage"
            />
          </div>

          <div class="ml-auto self-end">
            <BaseBtn
              v-if="hasNextPage"
              text="Siguiente"
              type="button"
              @click="nextPage"
            />
            <BaseBtn v-else class="e-primary" text="Aceptar" type="submit" />
          </div>
        </div>
      </form>
    </BaseCard>
  </div>
</template>

<script>
import PatientGeneralForm from "@/components/PatientGeneralForm.vue";
import { markRaw } from "vue";
import { usePatientStore } from "@/store/patientStore.js";
import { mapActions } from "pinia";
import router from "@/router/router.js";
import PatientDetails from "@/components/PatientDetails.vue";

export default {
  name: "PatientAddPage",
  components: { PatientGeneralForm },
  data() {
    return {
      formData: {
        name: "",
        lastName: "",
        comments: "",
        gender: null,
        birthdate: null,
        bloodType: null,
        birthWeight: 0,
        autonomousCommunity: "",
        photoUrls: [],
      },
      formIsValid: false,
      pages: [markRaw(PatientGeneralForm), markRaw(PatientDetails)],
      currentStep: 0,
      currentComponent: PatientGeneralForm,
    };
  },
  computed: {
    currentComponentPage() {
      return this.getCurrentPage();
    },
    hasPreviousPage() {
      return this.currentStep > 0;
    },
    hasNextPage() {
      return this.currentStep < this.pages.length - 1;
    },
  },
  methods: {
    ...mapActions(usePatientStore, {
      addPatient: "addPatient",
    }),
    getCurrentPage() {
      return this.pages[this.currentStep];
    },
    nextPage() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        this.currentStep++;
      }
    },
    previousPage() {
      this.currentStep--;
    },
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          await this.addPatient(this.formData);
          this.$swal.fire({
            icon: "success",
            title: "Los datos se han registrado correctamente",
            timer: 1500,
          });
          await router.push({ name: "PatientsListPage" });
        } catch (error) {
          this.$swal.fire({
            icon: "error",
            title: "Ha ocurrido un error inesperado",
            timer: 1500,
          });
        }
      } else {
        this.$swal.fire({
          icon: "error",
          title: "Los datos introducidos no son correctos",
          timer: 1500,
        });
      }
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
