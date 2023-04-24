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
              allowedExtensions=".jpg, .png"
              locale="es"
              name="UploadFiles"
            ></ejs-uploader>
          </div>
        </div>
      </div>
      <hr class="my-6" />
    </div>

    <div class="grid sm:grid-cols-1 md:grid-cols-3 gap-4">
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
      <div class="md:col-span-1">
        <ejs-dropdownlist
          v-model="formData.gender"
          :dataSource="genderData.data"
          :fields="genderData.fields"
          :itemTemplate="genderItemTemplate"
          :placeholder="genderData.placeholder"
          popupHeight="450px"
          @change="validateGender"
        >
        </ejs-dropdownlist>
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
        <ejs-dropdownlist
          v-model="formData.autonomousCommunity"
          :allowFiltering="true"
          :dataSource="autonomousCommunityData.data"
          :fields="autonomousCommunityData.fields"
          :filtering="onFiltering"
          :placeholder="autonomousCommunityData.placeholder"
          filterBarPlaceholder="Buscar"
          noRecordsTemplate="No se encontraron registros"
          popupHeight="450px"
          @change="validateAutonomousCommunity"
        >
        </ejs-dropdownlist>
        <label v-show="!isValidAutonomousCommunity" class="e-error"
          >Debe seleccionar una CCAA</label
        >
      </div>

      <div class="md:col-span-2 flex align-items-center">
        <div class="text-align-right">Peso al nacer:</div>
        <div class="ml-2">
          <ejs-numerictextbox
            v-model="formData.birthWeight"
            format="###.### kg"
          ></ejs-numerictextbox>
        </div>
      </div>

      <div class="md:col-span-1">
        <ejs-dropdownlist
          v-model="formData.bloodType"
          :dataSource="bloodTypeData.data"
          :fields="bloodTypeData.fields"
          :placeholder="bloodTypeData.placeholder"
          popupHeight="450px"
        >
        </ejs-dropdownlist>
      </div>

      <div class="md:col-span-3">
        <BaseFormInput v-model="formData.comments" text="Comentarios" />
      </div>

      <div v-if="isAdd" class="md:col-span-3">
        <div class="grid md:grid-cols-3 gap-4">
          <div v-if="formData.previewImage" class="md:col-span-1">
            <img
              :alt="altText"
              :src="formData.previewImage"
              class="m-auto shadow-lg h-36"
            />
          </div>
          <div
            :class="formData.previewImage ? 'md:col-span-2' : 'md:col-span-3'"
          >
            <div class="text-align-right">Foto:</div>
            <div class="upload-area ml-2">
              <ejs-uploader
                :autoUpload="false"
                :multiple="false"
                :removing="onFileRemove"
                :selected="onFileSelect"
                allowedExtensions=".jpg, .png"
                locale="es"
                name="UploadFiles"
              ></ejs-uploader>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { DateTimePickerComponent } from "@syncfusion/ej2-vue-calendars";
import {
  NumericTextBoxComponent,
  UploaderComponent,
} from "@syncfusion/ej2-vue-inputs";
import { Query } from "@syncfusion/ej2-data";
import genders from "../data/genderData.json";
import bloodTypes from "../data/bloodTypeData.json";
import autonomousCommunities from "../data/autonomousCommunitiesData.json";

import { L10n, loadCldr } from "@syncfusion/ej2-base";

import * as numberingSystems from "cldr-data/supplemental/numberingSystems.json";
import * as gregorian from "cldr-data/main/es/ca-gregorian.json";
import * as numbers from "cldr-data/main/es/numbers.json";
import * as timeZoneNames from "cldr-data/main/es/timeZoneNames.json";
import * as weekData from "cldr-data/supplemental/weekData.json";
import { publicImagesPath } from "@/router/publicPath.js";

loadCldr(numberingSystems, gregorian, numbers, timeZoneNames, weekData);

L10n.load({
  es: {
    datetimepicker: { today: "Hoy" },
    uploader: {
      Browse: "Examinar",
      dropFilesHint: "...o arrastre sus archivos aquí",
      remove: "Eliminar",
      delete: "Borrar archivo",
      invalidFileType: "El tipo de archivo no está permitido",
    },
  },
});

export default {
  name: "PatientGeneralForm",
  components: {
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
      genderItemTemplate:
        "<div>\n" +
        "<span class='${icon} pr-1 pl-1 e-avatar'>\n" +
        "</span>\n" +
        "<span class='gender'>${value}</span>\n" +
        "</div>",
      genderData: {
        fields: { text: "value", value: "id" },
        placeholder: "Sexo",
        data: genders,
      },
      bloodTypeData: {
        fields: { text: "value", value: "value" },
        placeholder: "Tipo de sangre",
        data: bloodTypes,
      },
      autonomousCommunityData: {
        fields: {
          text: "name",
          value: "id",
        },
        placeholder: "Comunidad autónoma",
        data: autonomousCommunities,
      },
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
      return this.formData.photoUrl !== "" && !this.formData.changedPhoto
        ? this.formData.photoUrl
        : this.formData.previewImage
        ? this.formData.previewImage
        : this.formData.gender === "HOMBRE"
        ? publicImagesPath + "no-photo-boy.png"
        : publicImagesPath + "no-photo-girl.png";
    },
    altText() {
      return this.formData.photoUrl !== ""
        ? `Foto de ${this.formData.name}`
        : `No hay foto disponible para ${this.formData.name}`;
    },
    showRemoveButton() {
      return (
        (this.formData.photoUrl !== "" && !this.formData.changedPhoto) ||
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
    onFiltering(e) {
      let query = new Query();
      query =
        e.text !== "" ? query.where("name", "contains", e.text, true) : query;
      e.updateData(this.autonomousCommunityData.data, query);
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
.gender {
  padding: 8px 42px;
  opacity: 0.87;
  line-height: 1.063em;
}

hr {
  border-top: 1px solid #ddd;
  width: 100%;
}
</style>
