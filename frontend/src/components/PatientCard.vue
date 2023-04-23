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
      {{
        patient.birthdate
          ? new Date(patient.birthdate).toLocaleDateString("es-ES", options) +
            " a las " +
            new Date(patient.birthdate).toLocaleTimeString("es-ES", {
              hour: "numeric",
              minute: "2-digit",
            })
          : ""
      }}
    </p>
    <p class="my-2 text-sm text-gray-500 dark:text-gray-400">
      {{ patient.comments }}
    </p>
  </BaseCard>
</template>

<script>
export default {
  data() {
    return {
      options: {
        weekday: "long",
        year: "numeric",
        month: "long",
        day: "numeric",
      },
    };
  },
  name: "PatientCard",
  props: ["patient"],
  computed: {
    photoSrc() {
      return this.patient.photoUrls[0]
        ? publicImagesPath + "patients/" + this.patient.photoUrls[0]
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
    openDetails() {},
  },
};
</script>

<style scoped></style>
