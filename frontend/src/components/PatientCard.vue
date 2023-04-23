<template>
  <BaseCard class="text-center">
    <img
      :alt="altText"
      :src="photoSrc"
      class="m-auto shadow-lg h-36 rounded-full"
    />
    <p class="text-base mt-4">{{ patient.name }}</p>
    <p class="text-base text-gray-400 dark:text-gray-300">
      {{ patient.lastName }}
    </p>
    <p class="my-2 text-sm text-gray-500 dark:text-gray-400">
      <strong>Fecha de nacimiento: </strong>
      {{ formatDate(patient.birthdate) }}
    </p>

    <p class="my-2 text-sm text-gray-500 dark:text-gray-400">
      {{ patient.comments }}
    </p>
    <div class="mt-4 flex justify-center gap-4">
      <ejs-tooltip ref="tooltip" content="Detalles">
        <router-link
          :to="{ name: 'PatientDetailsPage', params: { id: patient.id } }"
          class="hover:text-primary text-2xl"
        >
          <i class="i-Search-People"></i>
        </router-link>
      </ejs-tooltip>
      <ejs-tooltip ref="tooltip" content="Editar">
        <router-link
          :to="{ name: 'PatientEditPage', params: { id: patient.id } }"
          class="hover:text-primary text-2xl"
        >
          <i class="i-Pen-3"></i>
        </router-link>
      </ejs-tooltip>
    </div>
  </BaseCard>
</template>

<script>
import { publicImagesPath } from "@/router/publicPath.js";
import { TooltipComponent } from "@syncfusion/ej2-vue-popups";

export default {
  name: "PatientCard",
  props: ["patient"],
  components: {
    "ejs-tooltip": TooltipComponent,
  },
  computed: {
    photoSrc() {
      return this.patient.photoUrls[0]
        ? this.patient.photoUrls[0]
        : this.patient.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
    },
    altText() {
      return this.patient.photoUrls
        ? `Foto de ${this.patient.name}`
        : `No hay foto disponible para ${this.patient.name}`;
    },
  },
  methods: {
    formatDate(date) {
      return (
        new Date(date).toLocaleDateString("es-ES", {
          weekday: "long",
          year: "numeric",
          month: "long",
          day: "numeric",
        }) +
        " a las " +
        new Date(date).toLocaleTimeString("es-ES", {
          hour: "numeric",
          minute: "2-digit",
        })
      );
    },
  },
};
</script>

<style scoped></style>
