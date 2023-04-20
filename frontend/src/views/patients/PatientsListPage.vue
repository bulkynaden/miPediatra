<template>
  <div class="container mx-auto">
    <Breadcrumbs
      parentTitle="Mis pacientes"
      subParentTitle="Listado pacientes"
    />
    <router-link :to="{ name: 'PatientAddPage' }">
      <BaseBtn text="Añadir paciente"></BaseBtn>
    </router-link>
    <h1>Listado pacientes</h1>
    <ul>
      <li v-for="patient in patients" :key="patient.id">
        {{ patient.name }} {{ patient.lastName }}
      </li>
    </ul>
  </div>
</template>

<script>
import { usePatientStore } from "@/store/patientStore";

export default {
  name: "PatientsListPage",
  computed: {
    patients() {
      const patientStore = usePatientStore();
      return patientStore.getPatients;
    },
  },
};
</script>

<style scoped></style>
