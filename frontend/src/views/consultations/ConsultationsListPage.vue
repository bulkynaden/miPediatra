<template>
  <div>
    <BasePageCard
      parentTitle="Consultas médicas"
      subParentTitle="Listado de consultas"
    >
      <template #button>
        <router-link :to="{ name: 'ConsultationAddPage' }">
          <BaseBtn
            class="filled e-primary"
            text="Añadir consulta médica"
          ></BaseBtn>
        </router-link>
      </template>

      <template #title> Listado de consultas médicas</template>
      <ConsultationsList :consultations="consultations" />
    </BasePageCard>
  </div>
</template>

<script>
import { useConsultationsStore } from "@/store/consultationsStore.js";
import ConsultationsList from "@/components/consultations/ConsultationsList.vue";

export default {
  name: "ConsultationsListPage",
  components: {
    ConsultationsList,
  },
  computed: {
    consultations() {
      const consultationsStore = useConsultationsStore();
      return consultationsStore.consultations;
    },
  },
};
</script>
