<template>
  <div>
    <h2 class="pb-4">Datos concretos</h2>
    <div class="grid sm:grid-cols-1 md:grid-cols-2 gap-4 pt-4">
      <div class="md:col-span-1">
        <div class="whitespace-nowrap text-align-right">Síntomas:</div>
        <div class="flex-grow ml-2">
          <SymptomsCombo
            :patient="formData.patient"
            @selected-item="handleSelectedSymptom"
          />
          <label v-show="!isValidSymptom" class="e-error"
            >Debe seleccionar al menos un síntoma</label
          >

          <div
            v-for="consultation in formData.consultations"
            class="flex items-center"
          >
            {{ consultation.id }}
          </div>
        </div>
      </div>

      <div class="md:col-span-2 space-y-2">
        <div
          v-for="(symptom, index) in formData.symptoms"
          :key="symptom.id"
          class="border-b border-gray-300 pb-2"
        >
          <div class="flex items-stretch gap-4">
            <button
              class="bg-red-500 text-white text-sm py-1 px-1.5 rounded self-end"
              @click.prevent="removeSymptom(index)"
            >
              X
            </button>
            <div class="flex-grow self-end">{{ symptom.name }}</div>
            <div v-if="symptom.name === 'Otros'" class="w-full">
              <div class="h-full flex-grow items-center">
                <BaseFormInput
                  v-model="symptom.description"
                  placeholder="fds"
                  text="Descripción"
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="md:col-span-1 flex">
        <div class="flex-grow ml-2">
          <ejs-textbox
            v-model="formData.diagnosis"
            :floatLabelType="'Auto'"
            :multiline="true"
            :rows="4"
            placeholder="Diagnóstico"
          ></ejs-textbox>
        </div>
      </div>

      <div class="md:col-span-1 flex">
        <div class="flex-grow ml-2">
          <ejs-textbox
            v-model="formData.treatment"
            :floatLabelType="'Auto'"
            :multiline="true"
            :rows="4"
            placeholder="Tratamiento"
          ></ejs-textbox>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { TextBoxComponent } from "@syncfusion/ej2-vue-inputs";
import SymptomsCombo from "@/components/consultations/combos/SymptomsCombo.vue";

export default {
  name: "EmergencyForm",
  components: { SymptomsCombo, "ejs-textbox": TextBoxComponent },
  props: ["formData"],
  data() {
    return {
      isValidSymptom: true,
    };
  },
  methods: {
    handleSelectedSymptom(e) {
      this.formData.symptoms.push({
        id: e.id,
        name: e.name,
        description: e.description,
      });
      this.validateSymptom();
    },
    removeSymptom(index) {
      this.formData.symptoms.splice(index, 1);
      this.validateSymptom();
    },
    validateSymptom() {
      this.isValidSymptom =
        this.formData.symptoms && this.formData.symptoms.length > 0;
    },
    validateAll() {
      this.validateSymptom();
      const isValid = this.isValidSymptom;
      this.$emit("validation", isValid);
    },
  },
};
</script>

<style scoped></style>
