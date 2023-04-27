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
      <div class="container mx-auto">
        <div class="flex flex-row items-start max-w-xs mb-4">
          <ConsultationsTypeCombo
            class="w-24"
            mode="filter"
            @selected-item="handleFilter"
          />
        </div>
        <div
          v-if="consultationsToShow && consultationsToShow.length"
          class="grid sm:grid-cols-1 gap-4"
        >
          <ul class="divide-y divide-gray-400" role="list">
            <li
              v-for="consultation in consultationsToShow"
              :key="consultation.id"
              class="flex justify-between gap-x-6 py-5"
            >
              <ConsultationListItem :consultation="consultation" />
            </li>
          </ul>
        </div>
        <div v-else class="text-center text-gray-500 py-6">
          <h2 class="text-xl">Sin datos.</h2>
          <p v-if="isFiltered" class="mt-2">
            No hay consultas con los filtros seleccionados.
          </p>
          <p v-else class="mt-2">
            Agrega nuevas consultas usando el botón "Añadir consulta médica".
          </p>
        </div>
      </div>
    </BasePageCard>
  </div>
</template>

<script>
import { useConsultationsStore } from "@/store/consultationsStore.js";
import ConsultationListItem from "@/components/consultations/ConsultationListItem.vue";
import ConsultationsTypeCombo from "../../components/consultations/ConsultationsTypesCombo.vue";

export default {
  name: "ConsultationsListPage",
  components: { ConsultationsTypeCombo, ConsultationListItem },
  data() {
    return {
      filter: "All",
    };
  },
  computed: {
    consultations() {
      const consultationsStore = useConsultationsStore();
      return consultationsStore.getConsultations;
    },
    consultationsToShow() {
      return this.consultations.filter(
        (consultation) =>
          consultation.consultationType === this.filter || this.filter === "All"
      );
    },
    isFiltered() {
      return this.filter !== "";
    },
  },
  methods: {
    handleFilter(e) {
      this.filter = e.type;
    },
  },
};
</script>
