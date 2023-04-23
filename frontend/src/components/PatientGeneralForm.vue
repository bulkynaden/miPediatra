<template>
  <div>
    <h2 class="pb-4">Datos generales</h2>
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
          id="multi-template"
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
      <div class="md:col-span-2">
        <ejs-datepicker
          id="datetimepicker"
          v-model="formData.birthdate"
          :placeholder="'Seleccione fecha de nacimiento'"
          format="dd/MM/yyyy"
          @change="validateBirthdate"
        ></ejs-datepicker>
        <label v-show="!isValidBirthdate" class="e-error"
          >Debe introducir una fecha correcta</label
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
          id="multi-template"
          v-model="formData.bloodType"
          :dataSource="bloodTypeData.data"
          :fields="bloodTypeData.fields"
          :placeholder="bloodTypeData.placeholder"
          popupHeight="450px"
        >
        </ejs-dropdownlist>
        <label v-show="!isValidGender" class="e-error"
          >Debe seleccionar un sexo</label
        >
      </div>

      <div class="md:col-span-3">
        <BaseFormInput v-model="formData.comments" text="Comentarios" />
      </div>
    </div>
  </div>
</template>

<script>
import { DropDownListComponent } from "@syncfusion/ej2-vue-dropdowns";
import { DatePickerComponent } from "@syncfusion/ej2-vue-calendars";

import genders from "../data/genderData.json";
import bloodTypes from "../data/bloodTypeData.json";
import { NumericTextBoxComponent } from "@syncfusion/ej2-vue-inputs";

export default {
  name: "PatientGeneralForm",
  components: {
    "ejs-dropdownlist": DropDownListComponent,
    "ejs-datepicker": DatePickerComponent,
    "ejs-numerictextbox": NumericTextBoxComponent,
  },
  props: ["formData"],
  data() {
    return {
      isValidName: true,
      isValidLastName: true,
      isValidDescription: true,
      isValidGender: true,
      isValidBirthdate: true,
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
    };
  },
  methods: {
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
    validateAll() {
      this.validateName();
      this.validateLastName();
      this.validateGender();
      this.validateBirthdate();
      const isValid =
        this.isValidName &&
        this.isValidLastName &&
        this.isValidGender &&
        this.isValidBirthdate;
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
</style>
