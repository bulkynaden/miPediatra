<template>
  <div v-if="!isLoading" class="flex gap-x-4 w-full">
    <div class="flex flex-col justify-center items-center w-28">
      <span
        v-if="consultation.consultationType === 'Emergency'"
        class="bg-red-600 text-white text-xs font-semibold px-2 py-1 rounded-full"
      >
        Emergencia</span
      >
      <span
        v-else
        class="bg-green-600 text-white text-xs font-semibold px-2 py-1 rounded-full"
      >
        Visita rutinaria</span
      >
    </div>
    <img
      :alt="altText"
      :src="photoSrc"
      class="h-12 w-12 flex-none rounded-full shadow-md object-cover bg-gray-50"
    />
    <div class="flex-grow min-w-0">
      <p class="text-sm font-semibold leading-6 text-gray-900">
        {{ patient.name }} {{ patient.lastName }}
      </p>
      <p class="mt-1 truncate text-xs leading-5 text-gray-500">
        {{ dateFormatted }}
      </p>
    </div>
    <div class="hidden sm:flex sm:flex-col sm:items-end">
      <p class="text-sm leading-6 text-gray-900">
        {{ consultation.specialist }}
      </p>
      <p class="mt-1 text-xs leading-5 text-gray-500">
        {{ consultation.comments }}
      </p>
    </div>
    <div class="flex flex-col items-end">
      <ejs-tooltip ref="tooltip" content="Detalles">
        <router-link
          :to="{
            name: 'ConsultationDetailsPage',
            params: { id: consultation.id },
          }"
          class="hover:text-primary text-2xl"
        >
          <i class="i-Search-People"></i>
        </router-link>
      </ejs-tooltip>
    </div>
  </div>
</template>

<script>
import { TooltipComponent } from "@syncfusion/ej2-vue-popups";
import { publicImagesPath } from "@/router/publicPath.js";
import { usePatientsStore } from "@/store/patientsStore.js";

export default {
  name: "ConsultationCard",
  components: { "ejs-tooltip": TooltipComponent },
  props: {
    consultation: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      patient: null,
    };
  },
  computed: {
    photoSrc() {
      return this.patient.photo && this.patient.photo.url !== ""
        ? this.patient.photo.url
        : this.patient.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
    },
    altText() {
      return !this.patient
        ? "Sin datos"
        : this.patient.photo && this.patient.photo.url !== ""
        ? `Foto de ${this.patient.name}`
        : `No hay foto disponible para ${this.patient.name}`;
    },
    isLoading() {
      return this.patient === null;
    },
    dateFormatted() {
      return (
        new Date(this.consultation.date).toLocaleDateString("es-ES", {
          year: "numeric",
          month: "numeric",
          day: "numeric",
        }) +
        " a las " +
        new Date(this.consultation.date).toLocaleTimeString("es-ES", {
          hour: "numeric",
          minute: "2-digit",
        })
      );
    },
  },
  async beforeMount() {
    const patientStore = usePatientsStore();
    this.patient = await patientStore.getPatient(this.consultation.patient.id);
  },
};
</script>

<style scoped></style>
