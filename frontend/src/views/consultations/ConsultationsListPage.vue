<template>
  <div>
    <BasePageCard parentTitle="Consultas médicas" subParentTitle="Listado">
      <template #button>
        <router-link :to="{ name: 'ConsultationAddPage' }">
          <BaseBtn
            class="filled e-primary"
            text="Añadir consulta médica"
          ></BaseBtn>
        </router-link>
      </template>

      <template #title>Listado de consultas</template>
      <div class="container mx-auto">
        <div
          v-if="consultations && consultations.length"
          class="grid sm:grid-cols-1 md:grid-cols-2 gap-4"
        >
          <ul>
            <li v-for="consultation in consultations" class="text-gray-500">
              Centro médico: {{ consultation.center }} - Nombre:
              {{ consultation.patient.name }}
            </li>
          </ul>
          <ConsultationCard
            v-for="consultation in consultations"
            :key="consultation.id"
            :patient="consultation"
          >
          </ConsultationCard>
        </div>
        <div v-else class="text-center text-gray-500 py-6">
          <h2 class="text-xl">No hay consultas registradas.</h2>
          <p class="mt-2">
            Agrega nuevas consultas usando el botón "Añadir consulta médica".
          </p>
        </div>
      </div>
    </BasePageCard>
  </div>
</template>

<script>
import ConsultationCard from "@/components/consultations/ConsultationCard.vue";
import { useConsultationsStore } from "@/store/consultationsStore.js";

export default {
  name: "ConsultationsListPage",
  components: { ConsultationCard },
  computed: {
    consultations() {
      const consultationsStore = useConsultationsStore();
      return consultationsStore.getConsultations;
    },
  },
  methods: {},
};
</script>
