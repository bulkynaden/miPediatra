<template>
  <div v-if="consultations" class="container mx-auto">
    <div class="flex flex-row items-start max-w-xs mb-4 mt-4">
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
      <p v-else class="mt-2">Agregue nuevas consultas.</p>
    </div>
  </div>
</template>

<script>
import ConsultationsTypeCombo from "@/components/consultations/combos/ConsultationsTypesCombo.vue";
import ConsultationListItem from "@/components/consultations/ConsultationListItem.vue";

export default {
  name: "ConsultationsList",
  components: { ConsultationListItem, ConsultationsTypeCombo },
  props: ["consultations"],
  data() {
    return {
      filter: "All",
    };
  },
  computed: {
    consultationsToShow() {
      if (this.consultations) {
        return this.consultations.filter(
          (consultation) =>
            consultation.consultationType === this.filter ||
            this.filter === "All"
        );
      }
    },
    isFiltered() {
      return this.filter !== "All";
    },
  },
  methods: {
    handleFilter(e) {
      this.filter = e.type;
    },
  },
};
</script>

<style scoped></style>
