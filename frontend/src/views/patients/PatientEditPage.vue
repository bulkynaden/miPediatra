<template>
  <div class="container mx-auto">
    <BreadCrumbs
      v-if="patient"
      :subParentTitle="`Editar ${patient.name} ${patient.lastName}`"
      parentTitle="Mis pacientes"
    />
    <BreadCrumbs
      v-else
      :subParentTitle="`Editar paciente`"
      parentTitle="Mis pacientes"
    />
    <BaseCard v-if="loaded" class="max-w-3xl mx-auto w-auto">
      <template #cardHeader>Editar paciente</template>
      <form
        id="formAddChild"
        class="grid max-w-3xl w-auto mx-auto"
        method="post"
        @submit.prevent="submitForm"
      >
        <PatientGeneralForm
          ref="formPage"
          :formData="patient"
          @validation="handleValidation"
        />
        <div class="ml-auto self-end">
          <BaseBtn class="e-primary" text="Aceptar" type="submit" />
        </div>
      </form>
    </BaseCard>
  </div>
</template>

<script>
import { mapActions } from "pinia";
import { usePatientStore } from "@/store/patientStore.js";
import router from "@/router/router.js";
import PatientGeneralForm from "@/components/PatientGeneralForm.vue";
import Swal from "sweetalert2";

export default {
  name: "PatientEditPage",
  components: { PatientGeneralForm },
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
  },
  methods: {
    ...mapActions(usePatientStore, {
      getPatient: "getPatient",
      editPatient: "editPatient",
    }),
    async submitForm(patient) {
      this.$refs.formPage.validateAll();
      if (this.formIsValid) {
        try {
          await this.editPatient(patient);
          await Swal.fire({
            icon: "success",
            title: "Los datos se han registrado correctamente",
            timer: 1500,
          });
          await router.push({ name: "PatientsListPage" });
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
    this.patient = await this.getPatient(this.$route.params.id);
    if (!this.patient) {
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
      });
      await router.push({ name: "PatientsListPage" });
    }
  },
};
</script>

<style scoped></style>
