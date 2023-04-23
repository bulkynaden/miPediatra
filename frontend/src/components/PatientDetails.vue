<template>
  <div>
    <h2 class="pb-4">Resumen</h2>
    <div class="resumen-container">
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
</template>

<script>
import genders from "../data/genderData.json";
import autonomousCommunities from "../data/autonomousCommunitiesData.json";

export default {
  name: "PatientDetails",
  props: ["formData"],
  computed: {
    formatDate() {
      if (!this.formData.birthdate) return "";
      return new Date(this.formData.birthdate).toLocaleDateString();
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
