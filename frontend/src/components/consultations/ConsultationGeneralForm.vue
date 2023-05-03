<template>
  <div v-if="loaded">
    <h2 class="pb-4">Datos generales</h2>
    <div v-if="mode === 'add'" class="grid sm:grid-cols-1 md:grid-cols-2 gap-4">
      <div class="md:col-span-1">
        <ConsultationsTypesCombo
          v-model="formData.consultationType"
          @selected-item="handleSelectedConsultation"
        />
        <label v-show="!isValidType" class="e-error"
          >Debe seleccionar un tipo de consulta</label
        >
      </div>
    </div>

    <div class="grid sm:grid-cols-1 md:grid-cols-2 gap-4 pt-4">
      <div class="md:col-span-1">
        <PatientsCombo
          v-model="formData.patient"
          :selected-id="formData.patient.id"
          @selected-item="handleSelectedPatient"
        />
        <label v-show="!isValidPatient" class="e-error"
          >Debe seleccionar un paciente</label
        >
      </div>

      <div class="md:col-span-1">
        <ejs-datetimepicker
          id="datetimepicker"
          v-model="formData.date"
          format="dd/MM/yyyy hh:mm"
          locale="es"
          placeholder="Fecha"
          @change="validateDate"
        ></ejs-datetimepicker>
        <label v-show="!isValidDate" class="e-error"
          >Debe introducir una fecha correcta</label
        >
      </div>

      <div class="md:col-span-1">
        <BaseFormInput v-model="formData.specialist" text="Especialista" />
      </div>
      <div class="md:col-span-1">
        <BaseFormInput v-model="formData.center" text="Centro médico" />
      </div>
      <div class="md:col-span-2">
        <ejs-textbox
          v-model="formData.comments"
          :floatLabelType="'Auto'"
          :multiline="true"
          :rows="4"
          placeholder="Comentarios"
        ></ejs-textbox>
      </div>
    </div>

    <MeasurementsForm
      v-if="loadRegularVisit"
      :formData="formData.measurements"
    />

    <div v-if="mode === 'add'" class="bg-blue-50 p-6 rounded-lg mt-4">
      <div class="whitespace-nowrap text-align-right">Adjuntar archivos:</div>
      <ejs-uploader
        ref="uploader"
        :removing="onFileRemove"
        :selected="onFileSelect"
        allowedExtensions=".jpg, .png, .jpeg, .pdf"
        autoUpload="true"
        locale="es"
        maxFileSize="5000000"
        name="UploadFiles"
      >
      </ejs-uploader>
    </div>
  </div>
</template>

<script>
import { DateTimePickerComponent } from "@syncfusion/ej2-vue-calendars";
import {
  TextBoxComponent,
  UploaderComponent,
} from "@syncfusion/ej2-vue-inputs";
import PatientsCombo from "@/components/patients/combos/PatientsCombo.vue";
import ConsultationsTypesCombo from "@/components/consultations/combos/ConsultationsTypesCombo.vue";
import MeasurementsForm from "@/components/consultations/MeasurementsForm.vue";

export default {
  name: "ConsultationGeneralForm",
  components: {
    MeasurementsForm,
    ConsultationsTypesCombo,
    PatientsCombo,
    "ejs-datetimepicker": DateTimePickerComponent,
    "ejs-uploader": UploaderComponent,
    "ejs-textbox": TextBoxComponent,
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
      isRegularVisit: false,
      isValidPatient: true,
      isValidDate: true,
      isValidType: true,
    };
  },
  computed: {
    loaded() {
      return this.formData != null;
    },
    isAdd() {
      return this.mode === "add";
    },
    loadRegularVisit() {
      return this.formData.consultationType === "RegularVisit";
    },
  },
  methods: {
    onFileSelect(args) {
      if (!this.formData.blobFiles) {
        this.formData.blobFiles = [];
      }
      for (const file of args.filesData) {
        if (file.statusCode === "1") {
          this.formData.blobFiles.push(file);
        }
      }
    },
    onFileRemove(args) {
      for (const file of args.filesData) {
        let index = this.formData.blobFiles.indexOf(file);
        if (index !== -1) {
          this.formData.blobFiles.splice(index, 1);
        }
      }
    },
    validatePatient() {
      this.isValidPatient =
        !!this.formData.patient && this.formData.patient.id !== -1;
    },
    validateDate() {
      this.isValidDate = !!this.formData.date;
    },
    validateType() {
      this.isValidType = !!this.formData.consultationType;
    },
    validateAll() {
      this.validatePatient();
      this.validateDate();
      this.validateType();
      const isValid =
        this.isValidPatient && this.isValidDate && this.isValidType;
      this.$emit("validation", isValid);
    },
    handleSelectedPatient(item) {
      this.validatePatient();
      this.formData.patient = item;
    },
    handleSelectedConsultation(item) {
      this.validateType();
      this.isRegularVisit = item.id === 0;
      this.formData.consultationType = item.type;
      this.$emit("changeType", item.id);
    },
  },
};
</script>

<style scoped></style>
