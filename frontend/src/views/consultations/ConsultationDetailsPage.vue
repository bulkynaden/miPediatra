<template>
  <div>
    <BasePageCard
      v-if="consultation"
      parentTitle="Consultas médicas"
      subParentTitle="Detalles de consulta"
    >
      <template #title>Detalles de consulta</template>
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
              <!-- TODO: Datos visita urgencia-->
            </section>
            <FileList :files="consultation.files" />
          </div>
        </div>
      </BaseCard>
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

export default {
  name: "ConsultationDetailsPage",
  components: { BaseCard, FileList, FileListItem, ConsultationDetailsItem },
  data() {
    return {
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
            await this.deleteConsultation(this.consultation.id).then(() => {
              this.$router.push({ name: "ConsultationsListPage" });
            });
            await Swal.fire(
              "¡Borrado!",
              "La consulta ha sido borrada con éxito.",
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
    this.consultation = await this.getConsultation(this.$route.params.id);
    console.log(this.consultation);
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
