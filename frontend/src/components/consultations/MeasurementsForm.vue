<template>
  <div class="grid sm:grid-cols-1 md:grid-cols-2 gap-4 pt-4">
    <div class="md:col-span-1 flex">
      <div class="whitespace-nowrap text-align-right">Altura:</div>
      <div class="flex-grow ml-2">
        <ejs-numerictextbox
          v-model="formData.height"
          format="###.### cm"
        ></ejs-numerictextbox>
      </div>
    </div>

    <div class="md:col-span-1 flex">
      <div class="whitespace-nowrap text-align-right">Peso:</div>
      <div class="flex-grow ml-2">
        <ejs-numerictextbox
          v-model="formData.weight"
          format="###.### kg"
        ></ejs-numerictextbox>
      </div>
    </div>

    <div class="md:col-span-1 flex">
      <div class="whitespace-nowrap text-align-right">Diámetro craneal:</div>
      <div class="flex-grow ml-2">
        <ejs-numerictextbox
          v-model="formData.cranialDiameter"
          format="###.### cm"
        ></ejs-numerictextbox>
      </div>
    </div>
  </div>
</template>

<script>
import { NumericTextBoxComponent } from "@syncfusion/ej2-vue-inputs";

export default {
  name: "MeasurementsForm",
  components: { "ejs-numerictextbox": NumericTextBoxComponent },
  props: ["formData"],
};
</script>

<style scoped></style>
