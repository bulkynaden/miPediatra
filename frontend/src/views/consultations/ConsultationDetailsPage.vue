<template>
  <div class="bg-gray-100 min-h-screen">
    <TheLoadingLogo v-if="showLoadingLogo" />
    <BasePageCard
      v-if="consultation"
      :subParentTitle="consultationTypeAction"
      class="p-4"
      parentTitle="Consultas médicas"
    >
      <template #title>
        <h1 class="text-3xl font-semibold mb-4">
          {{ consultationTypeAction }}
        </h1>
      </template>
      <BaseCard class="bg-white p-4 rounded-lg shadow">
        <div class="flex justify-center">
          <div class="w-full md:w-3/4 space-y-4">
            <ConsultationDetailsItem
              :value="
                consultation.patient.name + ' ' + consultation.patient.lastName
              "
              class="border-b border-gray-200 pb-2"
              field="Paciente"
            />
            <ConsultationDetailsItem
              :value="consultation.center"
              class="border-b border-gray-200 pb-2"
              field="Centro médico"
            />

            <ConsultationDetailsItem
              :value="formattedDate"
              class="border-b border-gray-200 pb-2"
              field="Fecha"
            />

            <ConsultationDetailsItem
              :value="consultation.specialist"
              class="border-b border-gray-200 pb-2"
              field="Especialista"
            />
            <section class="bg-gray-100 p-4 rounded-lg shadow mt-4">
              <section v-if="consultation.consultationType === 'RegularVisit'">
                <ConsultationDetailsItem
                  :value="consultation.measurements.weight + ' kg'"
                  class="border-b border-gray-200 pb-2"
                  field="Peso"
                />
                <ConsultationDetailsItem
                  :value="consultation.measurements.height + ' cm'"
                  class="border-b border-gray-200 pb-2"
                  field="Altura"
                />
                <ConsultationDetailsItem
                  :value="consultation.measurements.cranialDiameter + ' cm'"
                  class="border-b border-gray-200 pb-2"
                  field="Diámetro craneal"
                />
              </section>
              <section v-else>
                <div
                  v-if="consultation.symptoms.length > 0"
                  class="text-sm font-bold leading-6 text-gray-900"
                >
                  Síntomas
                </div>
                <div
                  v-for="symptom in consultation.symptoms"
                  :key="symptom.id"
                  class="divide-y divide-gray-100 bg-gray-300 text-gray-900 rounded-md border border-gray-100 mt-4"
                >
                  <div
                    class="flex items-center py-2 pl-4 pr-5 text-sm leading-6"
                  >
                    <span>{{ symptom.name }}</span
                    ><span v-if="symptom.name === 'Otros'">
                      : {{ symptom.description }}</span
                    >
                  </div>
                </div>
                <ConsultationDetailsItem
                  :value="consultation.diagnosis"
                  class="border-b border-gray-200 pb-2"
                  field="Diagnóstico"
                />
                <ConsultationDetailsItem
                  :value="consultation.treatment"
                  class="border-b border-gray-200 pb-2"
                  field="Tratamiento"
                />
              </section>
              <ConsultationDetailsItem
                :value="consultation.comments"
                class="border-b border-gray-200 pb-2"
                field="Comentarios"
              />
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
