<template>
  <div>
    <h2 class="pb-4">Resumen</h2>
    <div class="flex flex-col md:flex-row">
      <div
        class="md:w-1/2 md:pr-4 mb-4 md:mb-0 flex justify-center items-center"
      >
        <img
          :alt="altText"
          :src="photoSrc"
          class="shadow-lg h-64 md:h-auto w-auto"
        />
      </div>
      <div
        class="resumen-container flex flex-col justify-center items-center md:w-1/2"
      >
        <strong>Nombre:</strong>
        <span>{{ formData.name }}</span>
        <strong>Apellidos:</strong>
        <span>{{ formData.lastName }}</span>
        <strong>Sexo:</strong>
        <div>
          <span>{{ getGenderDisplay().text }}</span>
          <i :class="getGenderDisplay().icon" class="pl-1"></i>
        </div>
        <strong>Fecha de nacimiento:</strong>
        <span>{{ formatDate }}</span>
        <strong>Comunidad Autónoma:</strong>
        <span>{{ formData.autonomousCommunity.name }}</span>
        <strong>Peso al nacer:</strong>
        <span>{{ formData.birthWeight }} kg</span>
        <strong>Grupo sanguíneo:</strong>
        <span v-if="formData.bloodType">{{ formData.bloodType.name }}</span>
        <span v-else>No especificado</span>
        <strong>Comentarios:</strong>
        <span>{{ formData.comments }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import genders from "../../data/genderData.json";
import { publicImagesPath } from "@/router/publicPath.js";
import { useAutonomousCommunitiesStore } from "@/store/autonomousCommunitiesStore.js";

export default {
  name: "PatientDetailsForm",
  props: ["formData"],
  data() {
    return {
      autonomousCommunities: null,
      autonomousCommunityDisplay: "",
    };
  },
  computed: {
    formatDate() {
      if (!this.formData.birthdate) return "";
      return new Date(this.formData.birthdate).toLocaleDateString();
    },
    photoSrc() {
      let imageSrc;

      if (this.formData.previewImage) {
        const reader = new FileReader();
        reader.onload = (event) => {
          this.formData.previewImage = event.target.result;
        };
        imageSrc = URL.createObjectURL(this.formData.photo.rawFile);
      } else {
        imageSrc =
          this.formData.gender === "HOMBRE"
            ? publicImagesPath + "no-photo-boy.png"
            : publicImagesPath + "no-photo-girl.png";
      }

      return imageSrc;
    },
    altText() {
      return this.formData.photo
        ? `Foto de ${this.formData.name}`
        : `No hay foto disponible para ${this.formData.name}`;
    },
  },
  methods: {
    getGenderDisplay() {
      let selectedGender;
      if (this.formData.gender) {
        selectedGender = genders.find(
          (gender) => gender.id === this.formData.gender
        );
      }
      return {
        text: selectedGender ? selectedGender.value : "",
        icon: selectedGender ? selectedGender.icon : "",
      };
    },
    validateAll() {
      this.$emit("validation", true);
    },
  },
  async beforeMount() {
    this.autonomousCommunities =
      await useAutonomousCommunitiesStore().getAutonomousCommunities();
  },
};
</script>

<style scoped>
.resumen-container {
  width: 100%;
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 0.5rem;
  align-items: center;
  background-color: #f8f9fa;
  border-radius: 10px;
  padding: 1.5rem;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  margin: 0 auto;
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 10px;
}

strong {
  color: #495057;
  font-size: 0.9rem;
  margin-right: 0.5rem;
}

span {
  color: #212529;
  font-size: 0.9rem;
  line-height: 1.5;
}

i {
  font-size: 1.1rem;
  margin-left: 0.3rem;
}
</style>
