<template>
  <div>
    <TheLoadingLogo v-if="showLoadingLogo" />
    <BasePageCard
      v-if="consultation"
      :subParentTitle="consultationTypeAction"
      parentTitle="Consultas médicas"
    >
      <template #title>{{ consultationTypeAction }}</template>
      <BaseCard>
        <div class="flex justify-center">
          <div class="w-full md:w-3/4 space-y-4">
            <ConsultationDetailsItem
              :value="
                consultation.patient.name + ' ' + consultation.patient.lastName
              "
              class="border-b border-gray-200"
              field="Paciente"
            />
            <ConsultationDetailsItem
              :value="consultation.center"
              class="border-b border-gray-200"
              field="Centro médico"
            />

            <ConsultationDetailsItem
              :value="formattedDate"
              class="border-b border-gray-200"
              field="Fecha"
            />

            <ConsultationDetailsItem
              :value="consultation.specialist"
              class="border-b border-gray-200"
              field="Especialista"
            />
            <ConsultationDetailsItem
              :value="consultation.comments"
              class="border-b border-gray-200"
              field="Comentarios"
            />

            <section v-if="consultation.consultationType === 'RegularVisit'">
              <ConsultationDetailsItem
                :value="consultation.measurements.weight + ' kg'"
                class="border-b border-gray-200"
                field="Peso"
              />
              <ConsultationDetailsItem
                :value="consultation.measurements.height + ' cm'"
                class="border-b border-gray-200"
                field="Altura"
              />
              <ConsultationDetailsItem
                :value="consultation.measurements.cranialDiameter + ' cm'"
                class="border-b border-gray-200"
                field="Diámetro craneal"
              />
            </section>
            <section v-else>
              {{ consultation.diagnosis }} - {{ consultation.treatment }}
              <div v-for="symptom in consultation.symptoms" :key="symptom.id">
                {{ symptom.name }}
              </div>
            </section>
            <FileList :files="consultation.files" />
          </div>
        </div>
      </BaseCard>
      <div class="mt-4 flex flex-wrap justify-between">
        <router-link
          :to="{
            name: 'ConsultationEditPage',
            params: { id: consultation.id },
          }"
        >
          <BaseBtn class="i-Pen-3" text=" Editar"></BaseBtn>
        </router-link>
        <BaseBtn
          class="i-Eraser-2 e-danger"
          text=" Borrar"
          @click="confirmDeleteConsultation"
        ></BaseBtn>
      </div>
    </BasePageCard>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import { publicImagesPath } from "@/router/publicPath.js";
import { mapActions } from "pinia";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import ConsultationDetailsItem from "@/components/consultations/ConsultationDetailsItem.vue";
import FileListItem from "@/components/consultations/FileListItem.vue";
import FileList from "@/components/consultations/FileList.vue";
import BaseCard from "../../components/base/BaseCard.vue";
import TheLoadingLogo from "@/layout/TheLoadingLogo.vue";

export default {
  name: "ConsultationDetailsPage",
  components: {
    TheLoadingLogo,
    BaseCard,
    FileList,
    FileListItem,
    ConsultationDetailsItem,
  },
  data() {
    return {
      isLoading: false,
      consultation: null,
    };
  },
  computed: {
    photoSrc() {
      return this.consultation.patient.photo &&
        this.consultation.patient.photo.url !== ""
        ? this.consultation.patient.photo.url
        : this.consultation.patient.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
    },
    altText() {
      return this.consultation.patient.photo &&
        this.consultation.patient.photo.url !== ""
        ? `Foto de ${this.consultation.patient.name}`
        : `No hay foto disponible para ${this.consultation.patient.name}`;
    },
    formattedDate() {
      return (
        new Date(this.consultation.date).toLocaleDateString("es-ES", {
          weekday: "long",
          year: "numeric",
          month: "long",
          day: "numeric",
        }) +
        " a las " +
        new Date(this.consultation.date).toLocaleTimeString("es-ES", {
          hour: "numeric",
          minute: "2-digit",
        })
      );
    },
    showLoadingLogo() {
      return this.isLoading;
    },
    consultationTypeAction() {
      return this.consultation.consultationType === "Emergency"
        ? "Detalles de emergencia"
        : "Detalles de visita rutinaria";
    },
  },
  methods: {
    ...mapActions(useConsultationsStore, {
      getConsultation: "getConsultation",
      deleteConsultation: "deleteConsultation",
    }),
    confirmDeleteConsultation() {
      Swal.fire({
        title: "¿Estas seguro de borrar esta consulta?",
        text: "No podrás recuperar los datos de esta consulta",
        icon: "warning",
        showCancelButton: true,
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            this.isLoading = true;
            await this.deleteConsultation(this.consultation.id).then(() => {
              this.isLoading = false;
              this.$router.push({ name: "ConsultationsListPage" });
            });
            await Swal.fire(
              "¡Borrado!",
              "La consulta ha sido borrada con éxito.",
              "success"
            );
          } catch (error) {
            this.isLoading = false;
            this.$swal.fire({
              icon: "error",
              title: "Ha ocurrido un error inesperado",
              timer: 1500,
            });
          }
        }
      });
    },
    async deleteFile(file) {
      try {
        await Swal.fire({
          title: "¿Está seguro de que desea eliminar el archivo?",
          text: "Esta acción no se puede deshacer.",
          icon: "warning",
          showCancelButton: true,
        }).then((result) => {
          if (result.value) {
            try {
              this.isLoading = true;
              useConsultationsStore().deleteFile(file, this.consultation);
              this.isLoading = false;
              Swal.fire(
                "Eliminado!",
                "El archivo ha sido eliminado.",
                "success"
              );
            } catch (error) {
              this.isLoading = false;
              this.$swal.fire({
                icon: "error",
                title: "Ha ocurrido un error inesperado",
                timer: 1500,
              });
            }
          }
        });
      } catch (error) {
        this.isLoading = false;
      }
    },
    async uploadFile(file) {
      try {
        this.isLoading = true;
        await useConsultationsStore().uploadFile(file, this.consultation);
        this.isLoading = false;
        await Swal.fire(
          "¡Subido!",
          "El archivo ha sido subido con éxito.",
          "success"
        );
      } catch (error) {
        this.isLoading = false;
        this.$swal.fire({
          icon: "error",
          title: "Ha ocurrido un error inesperado",
          timer: 1500,
        });
      }
    },
  },
  provide() {
    return {
      deleteFile: this.deleteFile,
      uploadFile: this.uploadFile,
    };
  },
  async beforeMount() {
    this.isLoading = true;
    this.consultation = await this.getConsultation(this.$route.params.id);
    this.isLoading = false;
    if (!this.consultation) {
      await Swal.fire({
        icon: "error",
        title: "No se han podido cargar los datos",
      });
      await this.$router.push({ name: "ConsultationsListPage" });
    }
  },
};
</script>

<style scoped></style>
