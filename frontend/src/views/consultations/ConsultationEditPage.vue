<template>
  <div>
    <TheLoadingLogo v-if="showLoadingLogo" />
    <BasePageCard
      v-if="consultation"
      :subParentTitle="consultationTypeAction"
      parentTitle="Consultas médicas"
    >
      <template #title>{{ consultationTypeAction }}</template>
      <div class="flex justify-center">
        <BaseCard class="w-full md:w-3/4">
          <form method="post" @submit.prevent="submitForm">
            <transition mode="out-in" name="scale">
              <keep-alive>
                <component
                  :is="currentComponentPage"
                  :key="currentStep"
                  ref="formPage"
                  :formData="consultation"
                  :mode="'edit'"
                  @changeType="changeType"
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
import ConsultationGeneralForm from "@/components/consultations/ConsultationGeneralForm.vue";
import { mapActions } from "pinia";
import Swal from "sweetalert2";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import { markRaw } from "vue";
import EmergencyForm from "@/components/consultations/EmergencyForm.vue";
import TheLoadingLogo from "@/layout/TheLoadingLogo.vue";

export default {
  name: "PatientAddPage",
  components: {
    TheLoadingLogo,
    ConsultationGeneralForm,
  },
  data() {
    return {
      isLoading: false,
      consultationType: null,
      consultation: null,
      formIsValid: false,
      currentComponent: ConsultationGeneralForm,
      pages: [markRaw(ConsultationGeneralForm)],
      currentStep: 0,
    };
  },
  watch: {
    consultationType(newConsultationType, OldConsultationType) {
      if (newConsultationType === "Emergency") {
        this.pages.push(markRaw(EmergencyForm));
      } else {
        if (this.pages.length > 1) {
          this.pages.pop();
        }
      }
    },
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
    consultationTypeAction() {
      return this.consultationType === "Emergency"
        ? "Editar emergencia"
        : "Editar visita rutinaria";
    },
  },
  methods: {
    ...mapActions(useConsultationsStore, {
      editConsultation: "editConsultation",
      getConsultation: "getConsultation",
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
    changeType(id) {
      if (id === 1) {
        this.consultationType = "Emergency";
      } else {
        this.consultationType = "RegularVisit";
      }
    },
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          this.isLoading = true;
          await this.editConsultation(this.consultation).then(() => {
            this.isLoading = false;
            this.$router.push({ name: "ConsultationsListPage" });
            Swal.fire({
              icon: "success",
              title: "Los datos se han registrado correctamente",
              timer: 1500,
            });
          });
        } catch (error) {
          await Swal.fire({
            icon: "error",
            title: "Ha ocurrido un error inesperado",
            timer: 1500,
          });
        }
      } else {
        this.isLoading = false;
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
  async beforeMount() {
    this.isLoading = true;
    this.consultation = await this.getConsultation(this.$route.params.id);
    this.isLoading = false;
    if (!this.consultation) {
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
      });
      await this.$router.push({ name: "ConsultationsListPage" });
    } else {
      this.consultationType = this.consultation.consultationType;
    }
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
