<template>
  <div>
    <BasePageCard
      parentTitle="Consultas médicas"
      subParentTitle="Añadir consultas"
    >
      <template #title>Añadir consulta</template>
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
                  :mode="'add'"
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
    <BaseBtn text="Volver" type="button" @click="returnToList" />
  </div>
</template>

<script>
import ConsultationGeneralForm from "@/components/consultations/ConsultationGeneralForm.vue";
import { mapActions } from "pinia";
import Swal from "sweetalert2";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import { markRaw } from "vue";
import EmergencyForm from "@/components/consultations/EmergencyForm.vue";
import { useLoadingStore } from "@/store/loadingStore.js";

export default {
  name: "PatientAddPage",
  components: {
    ConsultationGeneralForm,
  },
  data() {
    return {
      consultation: {
        patient: { id: -1 },
        specialist: "",
        center: "",
        date: "",
        comments: "",
        consultationType: null,
        blobFiles: [],
        measurements: {
          height: 0,
          weight: 0,
          cranialDiameter: 0,
        },
        symptoms: [],

        files: [],
      },
      formIsValid: false,
      currentComponent: ConsultationGeneralForm,
      pages: [markRaw(ConsultationGeneralForm)],
      currentStep: 0,
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
    ...mapActions(useConsultationsStore, {
      addConsultation: "addConsultation",
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
        this.pages.push(markRaw(EmergencyForm));
      } else {
        if (this.pages.length > 1) {
          this.pages.pop();
        }
      }
    },
    returnToList() {
      this.$router.push({ name: "ConsultationsListPage" });
    },
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          useLoadingStore().setLoading(true);
          await this.addConsultation(this.consultation).then(() => {
            useLoadingStore().setLoading(false);
            this.$router.push({ name: "ConsultationsListPage" });
            Swal.fire({
              icon: "success",
              title: "Los datos se han registrado correctamente",
              timer: 1000,
            });
          });
        } catch (error) {
          useLoadingStore().setLoading(false);
          await Swal.fire({
            icon: "error",
            title: "Ha ocurrido un error inesperado",
            timer: 1000,
          });
        }
      } else {
        await Swal.fire({
          icon: "error",
          title: "Los datos introducidos no son correctos",
          timer: 1000,
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
