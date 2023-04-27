<template>
  <div>
    <BasePageCard
      v-if="patient"
      :subParentTitle="patientData"
      parentTitle="Mis pacientes"
    >
      <template #title>Editar paciente</template>
      <div class="flex justify-center">
        <BaseCard class="w-full lg:w-3/4">
          <form method="post" @submit.prevent="submitForm">
            <PatientGeneralForm
              ref="formPage"
              :formData="patient"
              mode="edit"
              @validation="handleValidation"
            />

            <div class="ml-auto pt-8 self-end flex justify-end">
              <BaseBtn
                class="e-primary e-outline"
                text="Guardar"
                type="submit"
              />
            </div>
          </form>
        </BaseCard>
      </div>
    </BasePageCard>
  </div>
</template>

<script>
import { mapActions } from "pinia";
import { usePatientsStore } from "@/store/patientsStore.js";
import PatientGeneralForm from "@/components/patients/PatientGeneralForm.vue";
import Swal from "sweetalert2";
import BaseCard from "@/components/base/BaseCard.vue";

export default {
  name: "PatientEditPage",
  components: {
    BaseCard,
    PatientGeneralForm,
  },
  data() {
    return {
      patient: null,
      formIsValid: false,
    };
  },
  computed: {
    loaded() {
      return this.patient !== null;
    },
    patientData() {
      return this.patient
        ? `Detalles de ${this.patient.name} ${this.patient.lastName}`
        : "Detalles de paciente";
    },
  },
  methods: {
    ...mapActions(usePatientsStore, {
      getPatient: "getPatient",
      editPatient: "editPatient",
    }),
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          await this.editPatient(this.patient).then(() => {
            this.$router.push({ name: "PatientsListPage" });
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
  async beforeMount() {
    let realPatient = await this.getPatient(this.$route.params.id);
    this.patient = { ...realPatient };
    if (!this.patient) {
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
      });
      await this.$router.push({ name: "PatientsListPage" });
    }
  },
};
</script>

<style scoped></style>
