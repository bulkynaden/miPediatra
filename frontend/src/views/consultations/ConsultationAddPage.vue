<template>
  <div>
    <BasePageCard
      parentTitle="Consultas médicas"
      subParentTitle="Añadir consulta"
    >
      <template #title>Añadir consulta</template>
      <div class="flex justify-center">
        <BaseCard class="max-w-5xl">
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

                <ejs-progressbutton
                  v-else
                  :enableProgress="true"
                  content="Guardar"
                  cssClass="e-flat e-success"
                  iconCss="e-icons e-save"
                  isPrimary="true"
                  type="submit"
                ></ejs-progressbutton>
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
import { ProgressButtonComponent } from "@syncfusion/ej2-vue-splitbuttons";
import { mapActions } from "pinia";
import Swal from "sweetalert2";
import router from "@/router/router.js";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import { markRaw } from "vue";
import EmergencyForm from "@/components/consultations/EmergencyForm.vue";

export default {
  name: "PatientAddPage",
  components: {
    ConsultationGeneralForm,
    "ejs-progressbutton": ProgressButtonComponent,
  },
  data() {
    return {
      consultation: {
        patient: null,
        specialist: "",
        center: "",
        date: "",
        comments: "",
        consultationType: null,
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
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          await this.addConsultation(this.consultation).then(() => {
            router.push({ name: "ConsultationsListPage" });
          });
          await Swal.fire({
            icon: "success",
            title: "Los datos se han registrado correctamente",
            timer: 1500,
          });
        } catch (error) {
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
