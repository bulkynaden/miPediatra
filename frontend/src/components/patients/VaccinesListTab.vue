<template>
  <div>
    <BaseBtn text="Añadir vacuna" type="button" @click="addVaccine" />

    <VaccineForm
      v-if="showModal"
      :formData="selectedVaccine"
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
  computed: {
    selectedVaccine() {
      return this.vaccine;
    },
  },
  methods: {
    async updateVaccinesList() {
      this.vaccines = await usePatientsStore().getVaccines(this.patient);
    },
    addVaccine() {
      this.vaccine = {
        vaccineDetails: { id: -1 },
        vaccine: { id: -1 },
        date: "",
        expectedDate: "",
        reaction: "",
        hasBeenAdministered: false,
        photo: { blob: null },
      };
      this.mode = "add";
      this.showModal = true;
    },
    editVaccine(vaccine) {
      this.vaccine = {
        id: vaccine.id,
        vaccineDetails: vaccine.vaccineDetails.id,
        vaccine: vaccine.vaccine.id,
        date: vaccine.date,
        expectedDate: vaccine.expectedDate,
        reaction: vaccine.reaction,
        hasBeenAdministered: vaccine.hasBeenAdministered,
        photo: vaccine.photo,
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
              useVaccinesStore()
                .deleteVaccine(this.patient, vaccine)
                .then((r) =>
                  Swal.fire({
                    icon: "success",
                    title: "¡Eliminada!",
                    text: "La vacuna ha sido eliminada con éxito",
                    timer: 1000,
                  })
                );

              await this.updateVaccinesList();
            } catch (error) {
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
