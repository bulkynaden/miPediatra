<template>
  <div
    aria-labelledby="modal-title"
    aria-modal="true"
    class="fixed z-10 inset-0 overflow-y-auto"
    role="dialog"
  >
    <div class="flex items-center justify-center min-h-screen text-center">
      <div
        class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity"
      ></div>

      <div
        class="bg-white rounded-lg overflow-hidden shadow-xl transform transition-all sm:max-w-lg sm:w-full"
      >
        <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
          <h3
            id="modal-title"
            class="text-lg leading-6 font-medium text-gray-900"
          >
            {{ modeToDisplay }} vacuna
          </h3>
          <form class="text-start" @submit.prevent="submitForm">
            <div class="grid grid-cols-1 gap-4 pt-4 pb-4">
              <div class="md:col-span-3">
                <VaccinesCombo
                  v-model="formData.vaccineDetails"
                  @update:model-value="validateVaccine"
                ></VaccinesCombo>
                <label v-show="!isValidVaccine" class="e-error"
                  >Debe seleccionar una vacuna</label
                >
              </div>
              <div v-if="formData.vaccineDetails" class="md:col-span-3">
                <VaccineDosesCombo
                  v-model="formData.vaccine"
                  :vaccineDetails="formData.vaccineDetails"
                  @update:modelValue="validateDose"
                ></VaccineDosesCombo>
                <label v-show="!isValidDose" class="e-error"
                  >Debe seleccionar una dosis</label
                >
              </div>

              <div class="md:col-span-3 text-center">
                <h5 class="pb-4">¿Ha sido ya puesta?</h5>
                <ejs-switch
                  id="checked"
                  v-model="formData.hasBeenAdministered"
                  class="p-4"
                ></ejs-switch>
              </div>

              <section v-if="isAdministered" class="md:col-span-3">
                <div class="md:col-span-3">
                  <ejs-datepicker
                    id="datepicker"
                    v-model="formData.date"
                    format="dd/MM/yyyy"
                    locale="es"
                    placeholder="Fecha"
                    @change="validateDate"
                  ></ejs-datepicker>
                  <label v-show="!isValidDate" class="e-error"
                    >Debe introducir una fecha correcta</label
                  >
                </div>
                <div class="md:col-span-3">
                  <BaseFormInput v-model="formData.reaction" text="Reacción" />
                </div>

                <div class="md:col-span-3">
                  <div class="grid grid-cols-3 gap-4 pt-4 pb-4">
                    <div class="col-span-3 md:col-span-1">
                      <img
                        :alt="altText"
                        :src="photoSrc"
                        class="m-auto shadow-lg h-28"
                      />
                      <div v-if="showRemoveButton" class="text-center pt-4">
                        <BaseBtn
                          class="e-danger i-Eraser-2"
                          text=" Borrar"
                          type="button"
                          @click="removeImage"
                        ></BaseBtn>
                      </div>
                    </div>
                    <div class="col-span-3 md:col-span-2">
                      <div class="text-align-right">
                        {{ modeToDisplay }}
                        foto:
                      </div>
                      <div class="upload-area">
                        <ejs-uploader
                          ref="uploader"
                          :autoUpload="false"
                          :multiple="false"
                          :removing="onFileRemove"
                          :selected="onFileSelect"
                          allowedExtensions=".jpg, .png, .jpeg, .pdf"
                          locale="es"
                          maxFileSize="5000000"
                          name="UploadFiles"
                        ></ejs-uploader>
                      </div>
                    </div>
                  </div>
                </div>
              </section>
              <section v-else class="md:col-span-3">
                <div class="md:col-span-3">
                  <ejs-datepicker
                    id="datepicker"
                    v-model="formData.expectedDate"
                    format="dd/MM/yyyy"
                    locale="es"
                    placeholder="Fecha prevista"
                    @change="validateExpectedDate"
                  ></ejs-datepicker>
                  <label v-show="!isValidExpectedDate" class="e-error"
                    >Debe introducir una fecha correcta</label
                  >
                </div>
              </section>
            </div>
            <div class="text-center">
              <BaseBtn text="Cancelar" type="button" @click="$emit('close')" />
              <BaseBtn
                class="e-primary e-outline"
                text="Guardar"
                type="submit"
              />
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import VaccineDosesCombo from "@/components/vaccines/combos/VaccineDosesCombo.vue";
import VaccinesCombo from "@/components/vaccines/combos/VaccinesCombo.vue";
import { DatePickerComponent } from "@syncfusion/ej2-vue-calendars";
import { SwitchComponent } from "@syncfusion/ej2-vue-buttons";
import { UploaderComponent } from "@syncfusion/ej2-vue-inputs";
import Swal from "sweetalert2";
import GenderCombo from "@/components/patients/combos/GendersCombo.vue";
import { usePatientsStore } from "@/store/patientsStore.js";
import { useLoadingStore } from "@/store/loadingStore.js";
import { publicImagesPath } from "@/router/publicPath.js";

export default {
  name: "VaccineForm",
  components: {
    GenderCombo,
    "ejs-uploader": UploaderComponent,
    "ejs-datepicker": DatePickerComponent,
    "ejs-switch": SwitchComponent,
    VaccinesCombo,
    VaccineDosesCombo,
  },
  props: ["mode", "formData", "patient"],
  data() {
    return {
      isValidDate: true,
      isValidExpectedDate: true,
      isValidPhoto: true,
      isValidDose: true,
      isValidVaccine: true,
    };
  },
  computed: {
    isAdministered() {
      return this.formData.hasBeenAdministered;
    },
    modeToDisplay() {
      return this.mode === "add" ? "Añadir" : "Editar";
    },
    photoSrc() {
      return this.formData.photo && !this.formData.changedPhoto
        ? "data:" +
            this.formData.photo.type +
            ";base64," +
            this.formData.photo.data
        : this.formData.previewImage
        ? this.formData.previewImage
        : publicImagesPath + "no-photo.jpg";
    },
    altText() {
      return this.formData.photo
        ? `Foto de ${this.formData.name}`
        : `No hay foto disponible para ${this.formData.name}`;
    },
    showRemoveButton() {
      return (
        (this.formData.photo && !this.formData.changedPhoto) ||
        this.formData.previewImage
      );
    },
  },
  methods: {
    onFileSelect(args) {
      if (args.filesData[0].statusCode !== "1") {
        this.isValidPhoto = false;
      } else {
        this.formData.changedPhoto = true;
        this.isValidPhoto = true;
        this.formData.photo = args.filesData[0];
        const reader = new FileReader();
        reader.onload = (event) => {
          this.formData.previewImage = event.target.result;
        };
        reader.readAsDataURL(this.formData.photo.rawFile);
      }
    },
    onFileRemove() {
      if (this.isValidPhoto) {
        this.formData.changedPhoto = true;
        this.formData.photo = null;
        this.formData.previewImage = null;
      }
      this.isValidPhoto = true;
    },
    removeImage() {
      this.onFileRemove();
      this.$refs.uploader.clearAll();
    },
    validateDate() {
      this.isValidDate = !!this.formData.date;
    },
    validateExpectedDate() {
      this.isValidExpectedDate = !!this.formData.expectedDate;
    },
    validateDose() {
      this.isValidDose = !!this.formData.vaccine;
    },
    validateVaccine() {
      this.isValidVaccine = !!this.formData.vaccineDetails;
    },
    async submitForm() {
      this.validateDate();
      this.validateExpectedDate();
      this.validateDose();
      this.validateVaccine();
      let formIsValid = this.isValidVaccine && this.isValidDose;
      if (this.formData.hasBeenAdministered) {
        formIsValid = formIsValid && this.isValidDate && this.isValidPhoto;
      } else {
        formIsValid = formIsValid && this.isValidExpectedDate;
      }

      if (formIsValid) {
        if (this.mode === "add") {
          this.formData.patient = this.patient;
          await this.addVaccine();
        } else {
          await this.editVaccine();
        }
      } else {
        await Swal.fire({
          icon: "error",
          title: "Los datos introducidos no son correctos",
          timer: 1000,
        });
      }
    },
    async addVaccine() {
      try {
        useLoadingStore().setLoading(true);
        await usePatientsStore()
          .addVaccine(this.patient, this.formData)
          .then(() => {
            useLoadingStore().setLoading(false);
            Swal.fire({
              icon: "success",
              title: "Los datos se han registrado correctamente",
              timer: 1000,
            });
          });
        this.$emit("close");
      } catch (error) {
        useLoadingStore().setLoading(false);
        await Swal.fire({
          icon: "error",
          title: "Ha ocurrido un error inesperado",
          timer: 1000,
        });
      }
    },
    async editVaccine() {
      try {
        useLoadingStore().setLoading(true);
        await usePatientsStore()
          .editVaccine(this.formData)
          .then(() => {
            useLoadingStore().setLoading(false);
            Swal.fire({
              icon: "success",
              title: "Los datos se han registrado correctamente",
              timer: 1000,
            });
          });
        this.$emit("close");
      } catch (error) {
        useLoadingStore().setLoading(false);
        await Swal.fire({
          icon: "error",
          title: "Ha ocurrido un error inesperado",
          timer: 1000,
        });
      }
    },
  },
};
</script>
