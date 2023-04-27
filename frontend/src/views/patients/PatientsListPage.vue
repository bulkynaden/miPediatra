<template>
  <div>
    <BasePageCard
      parentTitle="Mis pacientes"
      subParentTitle="Listado de pacientes"
    >
      <template #button>
        <router-link :to="{ name: 'PatientAddPage' }">
          <BaseBtn class="filled e-primary" text="Añadir paciente"></BaseBtn>
        </router-link>
      </template>

      <template #title>Listado de pacientes</template>
      <div class="container mx-auto">
        <div
          v-if="patients && patients.length"
          class="grid sm:grid-cols-1 md:grid-cols-2 gap-4"
        >
          <PatientCard
            v-for="patient in patients"
            :key="patient.id"
            :patient="patient"
          >
          </PatientCard>
        </div>
        <div v-else class="text-center text-gray-500 py-6">
          <h2 class="text-xl">No hay pacientes registrados.</h2>
          <p class="mt-2">
            Agrega nuevos pacientes usando el botón "Añadir paciente".
          </p>
        </div>
      </div>
    </BasePageCard>
  </div>
</template>

<script>
import PatientCard from "@/components/patients/PatientCard.vue";
import { usePatientsStore } from "@/store/patientsStore.js";

export default {
  name: "PatientsListPage",
  components: { PatientCard },
  computed: {
    patients() {
      const patientStore = usePatientsStore();
      return patientStore.getPatients;
    },
  },
  methods: {},
};
</script>
