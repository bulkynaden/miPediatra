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
    <div v-for="vaccine in vaccines" :key="vaccine.id">
      {{ vaccine.name }} - {{ vaccine.dose }}
    </div>
  </div>
</template>

<script>
import { usePatientsStore } from "@/store/patientsStore.js";
import VaccineForm from "@/components/vaccines/VaccineForm.vue";

export default {
  name: "VaccinesList",
  components: { VaccineForm },
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
