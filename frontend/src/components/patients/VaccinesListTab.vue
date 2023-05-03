<template>
  <div>
    <BaseBtn text="Añadir vacuna" type="button" @click="addVaccine" />

    <VaccineForm
      v-if="showModal"
      :mode="mode"
      :patient="patient"
      :vaccine="vaccine"
      @close="onClose"
    />

    <div v-if="vaccines && vaccines.length > 0">
      <ul class="divide-y divide-gray-400" role="list">
        <li
          v-for="vaccine in vaccines"
          :key="vaccine.id"
          class="flex justify-between gap-x-6 py-5"
        >
          <VaccineListItem :vaccine="vaccine" />
        </li>
      </ul>
    </div>
    <div v-else class="text-center text-gray-500 py-6">
      <h2 class="text-xl">Sin datos.</h2>
      <p class="mt-2">Agregue datos de las vacunas.</p>
    </div>
  </div>
</template>

<script>
import { usePatientsStore } from "@/store/patientsStore.js";
import VaccineForm from "@/components/vaccines/VaccineForm.vue";
import ConsultationListItem from "@/components/consultations/ConsultationListItem.vue";
import VaccineListItem from "@/components/vaccines/VaccineListItem.vue";

export default {
  name: "VaccinesList",
  components: { VaccineListItem, ConsultationListItem, VaccineForm },
  props: ["patient"],
  data() {
    return {
      vaccines: [],
      vaccine: null,
      showModal: false,
      mode: "add",
    };
  },
  methods: {
    addVaccine() {
      this.mode = "add";
      this.showModal = true;
    },
    editVaccine() {
      this.mode = "edit";
      this.showModal = true;
    },
    async onClose() {
      this.showModal = false;
      this.vaccines = await usePatientsStore().getVaccines(this.patient);
    },
  },
  async beforeMount() {
    this.vaccines = await usePatientsStore().getVaccines(this.patient);
  },
};
</script>

<style scoped></style>
