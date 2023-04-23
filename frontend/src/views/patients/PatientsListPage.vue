<template>
  <div class="container mx-auto">
    <BreadCrumbs
      parentTitle="Mis pacientes"
      subParentTitle="Listado pacientes"
    />
    <router-link :to="{ name: 'PatientAddPage' }">
      <BaseBtn text="Añadir paciente"></BaseBtn>
    </router-link>
    <div class="grid sm:grid-cols-1 md:grid-cols-2 gap-4">
      <PatientCard
        v-for="patient in patients"
        :key="patient.id"
        :patient="patient"
      >
      </PatientCard>
    </div>
  </div>
</template>

<script>
import PatientCard from "@/components/PatientCard.vue";
import { usePatientStore } from "@/store/patientStore";

export default {
  name: "PatientsListPage",
  components: { PatientCard },
  computed: {
    patients() {
      const patientStore = usePatientStore();
      return patientStore.getPatients;
    },
  },
  methods: {},
};
</script>
