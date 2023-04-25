<template>
  <div>
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

      <div class="container mx-auto">
        <BaseCard>
          <div class="grid md:grid-cols-2 gap-4">
            <div class="space-y-4">
              <p><strong>Nombre:</strong> {{ patient.name }}</p>
              <p><strong>Apellido:</strong> {{ patient.lastName }}</p>
              <p>
                <strong>Sexo: </strong>
                <span>{{ getGenderDisplay().text }}</span>
                <i :class="getGenderDisplay().icon" class="pl-1"></i>
              </p>
              <p>
                <strong>Fecha de nacimiento:</strong>
                {{ formatDate(patient.birthdate) }}
              </p>
            </div>
            <div class="space-y-4">
              <p>
                <strong>Peso al nacer:</strong> {{ patient.birthWeight }} kg
              </p>
              <p>
                <strong>Comunidad autónoma:</strong>
                {{ getAutonomousCommunityDisplay(patient.autonomousCommunity) }}
              </p>
              <p><strong>Tipo de sangre:</strong> {{ patient.bloodType }}</p>
              <p><strong>DNI:</strong> {{ patient.dni }}</p>
            </div>
          </div>
          <div class="space-y-4 mt-4">
            <p><strong>Comentarios:</strong> {{ patient.comments }}</p>
          </div>
        </BaseCard>
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
    </BasePageCard>
  </div>
</template>

<script>
import { usePatientsStore } from "@/store/patientsStore.js";
import { mapActions } from "pinia";
import { publicImagesPath } from "@/router/publicPath.js";
import genders from "@/data/genderData.json";
import router from "@/router/router.js";
import autonomousCommunities from "@/data/autonomousCommunitiesData.json";
import Swal from "sweetalert2";

export default {
  name: "PatientDetailsPage",
  data() {
    return {
      patient: null,
    };
  },
  computed: {
    photoSrc() {
      return this.patient.photoUrl !== ""
        ? this.patient.photoUrl
        : this.patient.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
    },
    altText() {
      return this.patient.photoUrl !== ""
        ? `Foto de ${this.patient.name}`
        : `No hay foto disponible para ${this.patient.name}`;
    },
    patientData() {
      return this.patient
        ? `Detalles de ${this.patient.name} ${this.patient.lastName}`
        : "Detalles de paciente";
    },
  },
  methods: {
    ...mapActions(usePatientsStore, {
      getPatient: "getPatient",
      deletePatient: "deletePatient",
    }),
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
    getAutonomousCommunityDisplay(id) {
      let selectedAutonomousCommunity = "";
      if (id) {
        selectedAutonomousCommunity = autonomousCommunities.find(
          (community) => community.id === id
        ).name;
      }
      return selectedAutonomousCommunity;
    },
    confirmDeletePatient() {
      Swal.fire({
        title: "¿Estas seguro de borrar este paciente?",
        text: "No podrás recuperar a este paciente",
        icon: "warning",
        showCancelButton: true,
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            await this.deletePatient(this.patient.id).then(() => {
              router.push({ name: "PatientsListPage" });
            });
            await Swal.fire(
              "¡Borrado!",
              "El paciente ha sido borrado con éxito.",
              "success"
            );
          } catch (error) {
            this.$swal.fire({
              icon: "error",
              title: "Ha ocurrido un error inesperado",
              timer: 1500,
            });
          }
        }
      });
    },
  },
  async beforeMount() {
    this.patient = await this.getPatient(this.$route.params.id);
    if (!this.patient) {
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
      });
      await router.push({ name: "PatientsListPage" });
    }
  },
};
</script>

<style scoped></style>
