<template>
  <div>
    <TheLoadingLogo v-if="showLoadingLogo" />
    <BasePageCard parentTitle="Mis pacientes" subParentTitle="Añadir ">
      <template #title>Añadir paciente</template>
      <div class="flex justify-center">
        <BaseCard class="w-full lg:w-3/4">
          <form method="post" @submit.prevent="submitForm">
            <transition mode="out-in" name="scale">
              <keep-alive>
                <component
                  :is="currentComponentPage"
                  :key="currentStep"
                  ref="formPage"
                  :formData="formData"
                  :mode="'add'"
                  @validation="handleValidation"
                />
              </keep-alive>
            </transition>
            <div class="pt-8 flex justify-between">
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

                <BaseBtn
                  v-else
                  class="e-primary e-outline"
                  text="Guardar"
                  type="submit"
                />
              </div>
            </div>
          </form>
        </BaseCard>
      </div>
    </BasePageCard>
  </div>
</template>

<script>
import PatientGeneralForm from "@/components/patients/PatientGeneralForm.vue";
import PatientDetails from "@/components/patients/PatientDetails.vue";
import { usePatientsStore } from "@/store/patientsStore.js";
import { markRaw } from "vue";
import { mapActions } from "pinia";
import Swal from "sweetalert2";
import TheLoadingLogo from "@/layout/TheLoadingLogo.vue";

export default {
  name: "PatientAddPage",
  components: {
    TheLoadingLogo,
    PatientGeneralForm,
  },
  data() {
    return {
      isLoading: false,
      formData: {
        name: "",
        lastName: "",
        comments: "",
        gender: null,
        birthdate: null,
        bloodType: null,
        birthWeight: 0,
        autonomousCommunity: "",
        photo: {
          id: null,
          url: "",
        },
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
    showLoadingLogo() {
      return this.isLoading;
    },
  },
  methods: {
    ...mapActions(usePatientsStore, {
      addPatient: "addPatient",
    }),
    getCurrentPage() {
      return this.pages[this.currentStep];
    },
    nextPage() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        this.currentStep++;
      } else {
        Swal.fire({
          icon: "error",
          title: "Los datos introducidos no son correctos",
          timer: 1500,
        });
      }
    },
    previousPage() {
      this.currentStep--;
    },
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          this.isLoading = true;
          await this.addPatient(this.formData).then(() => {
            this.isLoading = false;
            this.$router.push({ name: "PatientsListPage" });
          });
          await Swal.fire({
            icon: "success",
            title: "Los datos se han registrado correctamente",
            timer: 1500,
          });
        } catch (error) {
          this.isLoading = false;
          await Swal.fire({
            icon: "error",
            title: "Ha ocurrido un error inesperado",
            timer: 1500,
          });
        }
      } else {
        await Swal.fire({
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
