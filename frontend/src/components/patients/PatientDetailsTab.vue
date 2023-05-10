<template>
  <div class="container mx-auto">
    <div class="grid md:grid-cols-2 gap-4">
      <div class="space-y-4">
        <p><strong>Nombre: </strong> {{ patient.name }}</p>
        <p><strong>Apellido: </strong> {{ patient.lastName }}</p>
        <p>
          <strong>Sexo: </strong>
          <span>{{ getGenderDisplay().text }}</span>
          <i :class="getGenderDisplay().icon" class="pl-1"></i>
        </p>
        <p>
          <strong>Fecha de nacimiento: </strong>
          {{ formatDate(patient.birthdate) }}
        </p>
      </div>
      <div class="space-y-4">
        <p><strong>Peso al nacer:</strong> {{ patient.birthWeight }} kg</p>
        <p>
          <strong>Comunidad autónoma: </strong>
          {{ patient.autonomousCommunity.name }}
        </p>
        <p>
          <strong>Grupo sanguíneo: </strong>
          <span v-if="patient.bloodType">{{ patient.bloodType.name }}</span>
          <span v-else>No especificado</span>
        </p>
      </div>
    </div>
    <div class="space-y-4 mt-4">
      <p><strong>Comentarios:</strong> {{ patient.comments }}</p>
    </div>

    <div class="mt-4 flex flex-wrap justify-between">
      <router-link
        :to="{ name: 'PatientEditPage', params: { id: patient.id } }"
      >
        <BaseBtn class="i-Pen-3" text=" Editar"></BaseBtn>
      </router-link>
      <BaseBtn
        class="i-Eraser-2 e-danger"
        text=" Borrar"
        @click="confirmDeletePatient"
      ></BaseBtn>
    </div>
  </div>
</template>

<script>
import { publicImagesPath } from "@/router/publicPath.js";
import genders from "@/data/genderData.json";
import Swal from "sweetalert2";
import { usePatientsStore } from "@/store/patientsStore.js";

export default {
  name: "PatientDetailsTab",
  props: ["patient"],
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
    getGenderDisplay() {
      let selectedGender;
      if (this.patient.gender) {
        selectedGender = genders.find(
          (gender) => gender.id === this.patient.gender
        );
      }
      return {
        text: selectedGender ? selectedGender.value : "",
        icon: selectedGender ? selectedGender.icon : "",
      };
    },
    confirmDeletePatient() {
      Swal.fire({
        title: "¿Estás seguro de eliminar este paciente?",
        text: "No podrás recuperar a este paciente",
        cancelButtonText: "Cancelar",
        icon: "warning",
        showCancelButton: true,
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await usePatientsStore()
              .deletePatient(this.patient.id)
              .then(() => {
                this.$router.push({ name: "PatientsListPage" });
              });
            await Swal.fire(
              "¡Eliminado!",
              "El paciente ha sido eliminado con éxito.",
              "success"
            );
          } catch (error) {
            this.$swal.fire({
              icon: "error",
              title: "Ha ocurrido un error inesperado",
              timer: 1000,
            });
          }
        }
      });
    },
  },
};
</script>

<style scoped></style>
