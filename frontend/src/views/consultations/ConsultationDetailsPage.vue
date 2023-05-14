<template>
  <div>
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
              field="Paciente"
            />
            <ConsultationDetailsItem
              :value="consultation.center"
              field="Centro médico"
            />

            <ConsultationDetailsItem :value="dateFormatted" field="Fecha" />

            <ConsultationDetailsItem
              :value="consultation.specialist"
              field="Especialista"
            />
            <section class="bg-gray-100 p-4 rounded-lg shadow mt-4">
              <section v-if="consultation.consultationType === 'RegularVisit'">
                <ConsultationDetailsItem
                  :value="consultation.measurements.weight + ' kg'"
                  field="Peso"
                />
                <ConsultationDetailsItem
                  :value="consultation.measurements.height + ' cm'"
                  field="Altura"
                />
                <ConsultationDetailsItem
                  :value="consultation.measurements.cranialDiameter + ' cm'"
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
                  field="Diagnóstico"
                />
                <ConsultationDetailsItem
                  :value="consultation.treatment"
                  field="Tratamiento"
                />
              </section>
              <ConsultationDetailsItem
                :value="consultation.comments"
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
    <BaseBtn text="Volver" type="button" @click="returnToList" />
  </div>
</template>

<script>
import Swal from "sweetalert2";
import { mapActions } from "pinia";
import { useConsultationsStore } from "@/store/consultationsStore.js";
import ConsultationDetailsItem from "@/components/consultations/ConsultationDetailsItem.vue";
import FileListItem from "@/components/consultations/FileListItem.vue";
import FileList from "@/components/consultations/FileList.vue";
import BaseCard from "../../components/base/BaseCard.vue";
import TheLoadingLogo from "@/layout/TheLoadingLogo.vue";
import { useLoadingStore } from "@/store/loadingStore.js";

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
      consultation: null,
    };
  },
  computed: {
    consultationTypeAction() {
      return this.consultation.consultationType === "Emergency"
        ? "Detalles de consulta de urgencias"
        : "Detalles de consulta rutinaria";
    },
    dateFormatted() {
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
    returnToList() {
      this.$router.push({ name: "ConsultationsListPage" });
    },
    confirmDeleteConsultation() {
      Swal.fire({
        title: "¿Estás seguro de eliminar esta consulta?",
        text: "No podrás recuperar los datos de esta consulta",
        cancelButtonText: "Cancelar",
        icon: "warning",
        showCancelButton: true,
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            useLoadingStore().setLoading(true);
            await this.deleteConsultation(this.consultation.id).then(() => {
              useLoadingStore().setLoading(false);
              this.$router.push({ name: "ConsultationsListPage" });
            });
            await Swal.fire(
              "¡Eliminada!",
              "La consulta ha sido eliminada con éxito.",
              "success"
            );
          } catch (error) {
            useLoadingStore().setLoading(false);
            this.$swal.fire({
              icon: "error",
              title: "Ha ocurrido un error inesperado",
              timer: 1000,
            });
          }
        }
      });
    },
    async deleteFile(file) {
      await Swal.fire({
        title: "¿Está seguro de que desea eliminar el archivo?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
      }).then((result) => {
        if (result.value) {
          try {
            useLoadingStore().setLoading(true);
            useConsultationsStore().deleteFile(file, this.consultation);
            useLoadingStore().setLoading(false);
            Swal.fire("Eliminado!", "El archivo ha sido eliminado.", "success");
          } catch (error) {
            useLoadingStore().setLoading(false);
            this.$swal.fire({
              icon: "error",
              title: "Ha ocurrido un error inesperado",
              timer: 1000,
            });
          }
        }
      });
    },
    async uploadFile(file) {
      try {
        useLoadingStore().setLoading(true);
        await useConsultationsStore().uploadFile(file, this.consultation);
        useLoadingStore().setLoading(false);
        await Swal.fire(
          "¡Subido!",
          "El archivo ha sido subido con éxito.",
          "success"
        );
      } catch (error) {
        useLoadingStore().setLoading(false);
        this.$swal.fire({
          icon: "error",
          title: "Ha ocurrido un error inesperado",
          timer: 1000,
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
    useLoadingStore().setLoading(true);
    this.consultation = await this.getConsultation(this.$route.params.id);
    useLoadingStore().setLoading(false);
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
