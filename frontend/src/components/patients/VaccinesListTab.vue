<template>
  <div>
    <BaseBtn text="Añadir vacuna" type="button" @click="addVaccine" />

    <VaccineForm
      v-if="showModal"
      :formData="vaccine"
      :mode="mode"
      :patient="patient"
      @close="onClose"
    />

    <div v-if="vaccines && vaccines.length > 0">
      <ul class="divide-y divide-gray-400" role="list">
        <li
          v-for="patientVaccine in vaccines"
          :key="patientVaccine.id"
          class="flex justify-between gap-x-6 py-5"
        >
          <VaccineListItem
            :vaccine="patientVaccine"
            @deleteVaccine="deleteVaccine"
            @editVaccine="editVaccine"
          />
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
import Swal from "sweetalert2";
import { useVaccinesStore } from "@/store/vaccinesStore.js";
import { useLoadingStore } from "@/store/loadingStore.js";

export default {
  name: "VaccinesList",
  components: { VaccineListItem, ConsultationListItem, VaccineForm },
  props: ["patient"],
  data() {
    return {
      vaccines: [],
      vaccine: {
        vaccineDetails: { id: -1 },
        vaccine: { id: -1 },
        date: "",
        expectedDate: "",
        reaction: "",
        hasBeenAdministered: false,
        photo: { blob: null },
      },
      showModal: false,
      mode: "add",
    };
  },
  methods: {
    async updateVaccinesList() {
      useLoadingStore().setLoading(true);
      this.vaccines = await usePatientsStore().getVaccines(this.patient);
      useLoadingStore().setLoading(false);
    },
    addVaccine() {
      this.vaccine = {
        vaccineDetails: { id: -1 },
        vaccine: { id: -1 },
        date: "",
        expectedDate: "",
        reaction: "",
        hasBeenAdministered: false,
        photo: null,
      };
      this.mode = "add";
      this.showModal = true;
    },
    editVaccine(vaccine) {
      this.vaccine = {
        ...vaccine,
        vaccine: vaccine.vaccine.id,
        vaccineDetails: vaccine.vaccineDetails.id,
      };
      this.mode = "edit";
      this.showModal = true;
    },
    async deleteVaccine(vaccine) {
      try {
        await Swal.fire({
          title: "¿Estás seguro de eliminar esta vacuna?",
          text: "No podrás recuperar los datos de esta vacuna",
          icon: "warning",
          cancelButtonText: "Cancelar",
          showCancelButton: true,
        }).then(async (result) => {
          if (result.isConfirmed) {
            try {
              useLoadingStore().setLoading(true);
              await useVaccinesStore()
                .deleteVaccine(vaccine)
                .then(async (r) => {
                  await this.updateVaccinesList();
                  useLoadingStore().setLoading(false);
                  await Swal.fire({
                    icon: "success",
                    title: "¡Eliminada!",
                    text: "La vacuna ha sido eliminada con éxito",
                    timer: 1000,
                  });
                });
            } catch (error) {
              useLoadingStore().setLoading(false);
              this.$swal.fire({
                icon: "error",
                title: "Ha ocurrido un error inesperado",
                timer: 1000,
              });
            }
          }
        });
      } catch (error) {}
    },
    async onClose() {
      this.showModal = false;
      await this.updateVaccinesList();
    },
  },
  async beforeMount() {
    await this.updateVaccinesList();
  },
};
</script>

<style scoped></style>
