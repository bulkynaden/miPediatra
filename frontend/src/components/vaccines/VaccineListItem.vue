<template>
  <div class="grid grid-cols-1 md:grid-cols-6 gap-4 w-full">
    <div class="col-span-1 flex flex-col justify-center items-center">
      <span
        class="bg-yellow-600 text-white text-xs font-semibold px-2 py-1 rounded-full"
      >
        {{ vaccine.name }}</span
      >
    </div>
    <div class="col-span-1 flex justify-center items-center">
      <strong class="mr-2">Dosis:</strong>{{ vaccine.dose }}º
    </div>
    <div class="col-span-1 flex flex-col justify-center items-center">
      <span
        v-if="vaccine.hasBeenAdministered"
        class="bg-green-600 text-white text-xs font-semibold px-2 py-1 rounded-full"
      >
        Administrada</span
      >
      <span
        v-else
        class="bg-red-600 text-white text-xs font-semibold px-2 py-1 rounded-full"
        >Prevista
      </span>
    </div>
    <div v-if="vaccine.hasBeenAdministered" class="col-span-2">
      <p class="text-sm font-semibold leading-6 text-gray-900">
        {{ dateFormatted(vaccine.date) }}
      </p>
      <p class="mt-1 truncate text-xs leading-5 text-gray-500">
        <strong>Reacción:</strong>
        {{ vaccine.reaction }}
      </p>
    </div>
    <div v-else class="col-span-2">
      <p class="text-sm font-semibold leading-6 text-gray-900">
        {{ dateFormatted(vaccine.expectedDate) }}
      </p>
    </div>
    <div class="col-span-1 flex flex-col items-end">
      <div class="flex flex-row space-x-1">
        <ejs-tooltip v-if="vaccine.photo.url" content="Ver foto">
          <i
            class="i-Search-People hover:text-primary text-2xl"
            @click="openPhoto"
          ></i>
        </ejs-tooltip>

        <ejs-tooltip content="Editar vacuna">
          <i
            class="i-Pen-3 hover:text-primary text-2xl"
            @click="editVaccine"
          ></i>
        </ejs-tooltip>

        <ejs-tooltip content="Eliminar vacuna">
          <i
            class="i-File-Trash hover:text-danger text-2xl"
            @click="deleteVaccine"
          ></i>
        </ejs-tooltip>
      </div>
    </div>
  </div>
</template>

<script>
import { TooltipComponent } from "@syncfusion/ej2-vue-popups";
import Swal from "sweetalert2";

export default {
  name: "VaccineListItem",
  components: { "ejs-tooltip": TooltipComponent },
  props: {
    vaccine: {
      type: Object,
      required: true,
    },
  },
  methods: {
    async openPhoto() {
      try {
        await Swal.fire({
          imageUrl: this.vaccine.photo.url,
          imageHeight: "80vh",
          width: "80vw",
          confirmButtonText: "Cerrar",
          showClass: {
            popup: "animate__animated animate__zoomIn",
          },
          hideClass: {
            popup: "animate__animated animate__zoomOut",
          },
        });
      } catch (error) {}
    },
    dateFormatted(date) {
      return new Date(date).toLocaleDateString("es-ES", {
        year: "numeric",
        month: "long",
        day: "numeric",
      });
    },
    async editVaccine() {
      try {
        await Swal.fire({
          title: "Editar vacuna",
          showClass: {
            popup: "animate__animated animate__zoomIn",
          },
          hideClass: {
            popup: "animate__animated animate__zoomOut",
          },
        });
      } catch (error) {}
    },
    async deleteVaccine() {
      try {
        await Swal.fire({
          title: "¿Estás seguro de eliminar esta vacuna?",
          text: "No podrás recuperar los datos de esta vacuna",
          icon: "warning",
          cancelButtonText: "Cancelar",
          showCancelButton: true,
        }).then(async (result) => {
          if (result.isConfirmed) {
            try {
              // TODO: Lógica para borrar
              await Swal.fire(
                "¡Eliminada!",
                "La vacuna ha sido eliminada con éxito.",
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
      } catch (error) {}
    },
  },
};
</script>

<style scoped></style>
