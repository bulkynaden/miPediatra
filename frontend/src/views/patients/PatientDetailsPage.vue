<template>
  <div v-if="patient">
    <BasePageCard :subParentTitle="patientData" parentTitle="Mis pacientes">
      <template #title>
        <div class="flex justify-between items-center">
          <div class="flex items-center">
            <img
              :alt="altText"
              :src="photoSrc"
              class="h-32 w-32 rounded-full shadow-lg object-cover mr-8"
            />
            <h2 class="text-3xl font-semibold text-white pr-4">
              {{ patient.name }} {{ patient.lastName }}
            </h2>
          </div>
        </div>
      </template>
      <BaseCard>
        <div class="container mx-auto">
          <div class="mb-4 flex border-b border-gray-300 overflow-x-auto">
            <button
              v-for="(tab, index) in tabs"
              :key="index"
              :class="[
                'text-xs sm:text-sm font-semibold text-gray-600 py-2 px-2 sm:px-4 whitespace-nowrap',
                activeTab === index
                  ? 'border-b-4 border-blue-500 text-blue-500'
                  : '',
              ]"
              @click="changeTab(index)"
              @mouseleave="unhoverTab(index)"
              @mouseover="hoverTab(index)"
            >
              {{ tab }}
            </button>
          </div>
          <transition mode="out-in" name="fade">
            <component
              :is="activeComponent"
              :consultations="consultations"
              :patient="patient"
            ></component>
          </transition>
        </div>
      </BaseCard>
    </BasePageCard>
    <BaseBtn text="Volver" type="button" @click="returnToList" />
  </div>
</template>

<script>
import { usePatientsStore } from "@/store/patientsStore.js";
import { publicImagesPath } from "@/router/publicPath.js";
import Swal from "sweetalert2";
import PatientDetailsTab from "@/components/patients/PatientDetailsTab.vue";
import ConsultationsList from "@/components/consultations/ConsultationsList.vue";
import BaseCard from "@/components/base/BaseCard.vue";
import VaccinesListTab from "@/components/patients/VaccinesListTab.vue";
import { useLoadingStore } from "@/store/loadingStore.js";

export default {
  name: "PatientDetailsPage",
  components: {
    BaseCard,
    PatientDetailsTab,
    ConsultationsList,
    VaccinesListTab,
  },
  data() {
    return {
      patient: null,
      consultations: [],
      vaccines: [],
      activeTab: 0,
      tabs: ["Detalles", "Consultas médicas", "Vacunas"],
    };
  },
  computed: {
    photoSrc() {
      if (this.patient.photo && this.patient.photo.data) {
        return (
          "data:" +
          this.patient.photo.type +
          ";base64," +
          this.patient.photo.data
        );
      } else {
        return this.patient.gender === "HOMBRE"
          ? publicImagesPath + "no-photo-boy.png"
          : publicImagesPath + "no-photo-girl.png";
      }
    },
    altText() {
      return this.patient.photo
        ? `Foto de ${this.patient.name}`
        : `No hay foto disponible para ${this.patient.name}`;
    },
    patientData() {
      return this.patient
        ? `Detalles de ${this.patient.name} ${this.patient.lastName}`
        : "Detalles de paciente";
    },
    activeComponent() {
      switch (this.activeTab) {
        case 1:
          return ConsultationsList;
        case 2:
          return VaccinesListTab;
        default:
          return PatientDetailsTab;
      }
    },
    getConsultations() {},
  },
  methods: {
    returnToList() {
      this.$router.push({ name: "PatientsListPage" });
    },
    changeTab(index) {
      this.activeTab = index;
    },
    hoverTab(index) {
      if (index !== this.activeTab) {
        this.$el
          .querySelectorAll("button")
          [index].classList.add("text-blue-500");
      }
    },
    unhoverTab(index) {
      if (index !== this.activeTab) {
        this.$el
          .querySelectorAll("button")
          [index].classList.remove("text-blue-500");
      }
    },
  },
  async beforeMount() {
    try {
      useLoadingStore().setLoading(true);
      this.patient = await usePatientsStore().getPatientDetails(
        this.$route.params.id
      );
      this.consultations = await usePatientsStore().getConsultations(
        this.patient
      );
      useLoadingStore().setLoading(false);
    } catch {
      useLoadingStore().setLoading(false);
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
        timer: 1000,
      });
      await this.$router.push({ name: "PatientsListPage" });
    }
  },
};
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

button {
  outline: none;
  transition: all 0.3s;
}
</style>
