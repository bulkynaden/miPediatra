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
        <span>{{
          getAutonomousCommunityDisplay(formData.autonomousCommunity)
        }}</span>
        <strong>Peso al nacer:</strong>
        <span>{{ formData.birthWeight }} kg</span>
        <strong>Tipo de sangre:</strong>
        <span>{{ formData.bloodType }}</span>
        <strong>Comentarios:</strong>
        <span>{{ formData.comments }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import genders from "../../data/genderData.json";
import autonomousCommunities from "../../data/autonomousCommunitiesData.json";
import { publicImagesPath } from "@/router/publicPath.js";

export default {
  name: "PatientDetailsForm",
  props: ["formData"],
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
        reader.readAsDataURL(this.formData.photo.blob.rawFile);
        imageSrc = this.formData.previewImage;
      } else {
        imageSrc =
          this.formData.gender === "HOMBRE"
            ? publicImagesPath + "no-photo-boy.png"
            : publicImagesPath + "no-photo-girl.png";
      }

      return imageSrc;
    },
    altText() {
      return this.formData.photo.url !== ""
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
    getAutonomousCommunityDisplay(id) {
      let selectedAutonomousCommunity = "";
      if (id) {
        selectedAutonomousCommunity = autonomousCommunities.find(
          (community) => community.id === id
        ).name;
      }
      return selectedAutonomousCommunity;
    },
    validateAll() {
      this.$emit("validation", true);
    },
  },
  filters: {},
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
