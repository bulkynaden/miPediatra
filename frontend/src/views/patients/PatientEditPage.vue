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
    <BaseBtn text="Volver" type="button" @click="returnToList" />
  </div>
</template>

<script>
import { mapActions } from "pinia";
import { usePatientsStore } from "@/store/patientsStore.js";
import PatientGeneralForm from "@/components/patients/PatientGeneralForm.vue";
import Swal from "sweetalert2";
import BaseCard from "@/components/base/BaseCard.vue";
import { useLoadingStore } from "@/store/loadingStore.js";

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
    patientData() {
      return this.patient
        ? `Detalles de ${this.patient.name} ${this.patient.lastName}`
        : "Detalles de paciente";
    },
  },
  methods: {
    ...mapActions(usePatientsStore, {
      getPatientCopy: "getPatientCopy",
      editPatient: "editPatient",
    }),
    returnToList() {
      this.$router.push({
        name: "PatientDetailsPage",
        params: this.patient.id,
      });
    },
    async submitForm() {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          useLoadingStore().setLoading(true);
          await this.editPatient(this.patient).then(() => {
            useLoadingStore().setLoading(false);
            Swal.fire({
              icon: "success",
              title: "Los datos se han registrado correctamente",
              timer: 1000,
            });
            this.$router.push({ name: "PatientsListPage" });
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
  async beforeMount() {
    try {
      useLoadingStore().setLoading(true);
      this.patient = await this.getPatientCopy(this.$route.params.id);
      useLoadingStore().setLoading(false);
    } catch {
      useLoadingStore().setLoading(false);
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
        timer: 1000,
      });
      await this.$router.push({ name: "PatientsListPage" });
    }
  },
};
</script>

<style scoped></style>
