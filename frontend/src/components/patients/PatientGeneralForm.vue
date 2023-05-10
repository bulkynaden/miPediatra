<template>
  <div v-if="loaded">
    <h2 class="pb-4">Datos generales</h2>
    <div v-if="!isAdd">
      <div class="grid sm:grid-cols-1 md:grid-cols-3 gap-4">
        <div class="md:col-span-1">
          <img
            :alt="altText"
            :src="photoSrc"
            class="m-auto shadow-lg h-36 rounded-full"
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
        <div class="md:col-span-2">
          <div class="text-align-right">Cambiar foto:</div>
          <div class="upload-area ml-2">
            <ejs-uploader
              ref="uploader"
              :autoUpload="false"
              :multiple="false"
              :removing="onFileRemove"
              :selected="onFileSelect"
              allowedExtensions=".jpg, .png, .jpeg"
              locale="es"
              maxFileSize="5000000"
              name="UploadFiles"
            ></ejs-uploader>
          </div>
        </div>
      </div>
      <hr class="my-6" />
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 pt-4">
      <div class="md:col-span-1">
        <BaseFormInput
          v-model="formData.name"
          :is-valid="isValidName"
          error-message="El nombre es obligatorio"
          text="Nombre"
          @update:modelValue="validateName"
        />
      </div>
      <div class="md:col-span-2">
        <BaseFormInput
          v-model="formData.lastName"
          :is-valid="isValidLastName"
          error-message="Los apellidos son obligatorios"
          text="Apellidos"
          @update:modelValue="validateLastName"
        />
      </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-4 pt-4">
      <div class="md:col-span-1">
        <GenderCombo
          v-model="formData.gender"
          @update:modelValue="validateGender"
        />
        <label v-show="!isValidGender" class="e-error"
          >Debe seleccionar un sexo</label
        >
      </div>
      <div class="md:col-span-1">
        <ejs-datetimepicker
          id="datetimepicker"
          v-model="formData.birthdate"
          format="dd/MM/yyyy hh:mm"
          locale="es"
          placeholder="Fecha de nacimiento"
          @change="validateBirthdate"
        ></ejs-datetimepicker>
        <label v-show="!isValidBirthdate" class="e-error"
          >Debe introducir una fecha correcta</label
        >
      </div>

      <div class="md:col-span-1">
        <AutonomousCommunityCombo
          v-model="formData.autonomousCommunity"
          @update:model-value="validateAutonomousCommunity"
        />
        <label v-show="!isValidAutonomousCommunity" class="e-error"
          >Debe seleccionar una CCAA</label
        >
      </div>

      <div class="md:col-span-1 flex">
        <div class="whitespace-nowrap text-align-right">Peso al nacer:</div>
        <div class="flex-grow ml-2">
          <ejs-numerictextbox
            v-model="formData.birthWeight"
            format="###.### kg"
          ></ejs-numerictextbox>
        </div>
      </div>

      <div class="md:col-span-1">
        <BloodTypesCombo v-model="formData.bloodType" />
      </div>
    </div>

    <BaseFormInput
      v-model="formData.comments"
      class="mt-2"
      text="Comentarios"
    />

    <div v-if="isAdd" class="pt-4">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        <div v-if="formData.previewImage" class="md:col-span-1">
          <img
            :alt="altText"
            :src="formData.previewImage"
            class="m-auto shadow-lg h-36"
          />
        </div>
        <div :class="formData.previewImage ? 'md:col-span-2' : 'md:col-span-3'">
          <div class="text-align-right">Foto:</div>
          <div class="upload-area ml-2">
            <ejs-uploader
              :autoUpload="false"
              :multiple="false"
              :removing="onFileRemove"
              :selected="onFileSelect"
              allowedExtensions=".jpg, .png, .jpeg"
              locale="es"
              maxFileSize="5000000"
              name="UploadFiles"
            ></ejs-uploader>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import GenderCombo from "@/components/patients/combos/GendersCombo.vue";
import AutonomousCommunityCombo from "@/components/patients/combos/AutonomousCommunitiesCombo.vue";
import BloodTypesCombo from "@/components/patients/combos/BloodTypesCombo.vue";
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { DateTimePickerComponent } from "@syncfusion/ej2-vue-calendars";
import {
  NumericTextBoxComponent,
  UploaderComponent,
} from "@syncfusion/ej2-vue-inputs";

import { publicImagesPath } from "@/router/publicPath.js";

export default {
  name: "PatientGeneralForm",
  components: {
    BloodTypesCombo,
    AutonomousCommunityCombo,
    GenderCombo,
    "ejs-dropdownlist": DropDownListComponent,
    "ejs-datetimepicker": DateTimePickerComponent,
    "ejs-numerictextbox": NumericTextBoxComponent,
    "ejs-uploader": UploaderComponent,
  },
  props: {
    formData: {
      type: Object,
      required: true,
    },
    mode: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      isValidName: true,
      isValidLastName: true,
      isValidDescription: true,
      isValidGender: true,
      isValidBirthdate: true,
      isValidAutonomousCommunity: true,
      isValidPhoto: true,
    };
  },
  computed: {
    loaded() {
      return this.formData != null;
    },
    isAdd() {
      return this.mode === "add";
    },
    photoSrc() {
      return this.formData.photo && !this.formData.changedPhoto
        ? "data:" +
            this.formData.photo.type +
            ";base64," +
            this.formData.photo.data
        : this.formData.previewImage
        ? this.formData.previewImage
        : this.formData.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
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
        this.formData.photo = null;
        this.formData.previewImage = null;
        this.formData.changedPhoto = true;
      }
      this.isValidPhoto = true;
    },
    removeImage() {
      this.onFileRemove();
      this.$refs.uploader.clearAll();
    },
    validateName() {
      this.isValidName = !!this.formData.name;
    },
    validateLastName() {
      this.isValidLastName = !(
        this.formData.lastName === "" || this.formData.lastName === null
      );
    },
    validateGender() {
      this.isValidGender = !!this.formData.gender;
    },
    validateBirthdate() {
      this.isValidBirthdate = !!this.formData.birthdate;
    },
    validateAutonomousCommunity() {
      this.isValidAutonomousCommunity = !!this.formData.autonomousCommunity;
    },
    validateAll() {
      this.validateName();
      this.validateLastName();
      this.validateGender();
      this.validateBirthdate();
      this.validateAutonomousCommunity();
      const isValid =
        this.isValidName &&
        this.isValidLastName &&
        this.isValidGender &&
        this.isValidBirthdate &&
        this.isValidAutonomousCommunity &&
        this.isValidPhoto;
      this.$emit("validation", isValid);
    },
  },
};
</script>

<style scoped>
hr {
  border-top: 1px solid #ddd;
  width: 100%;
}
</style>
