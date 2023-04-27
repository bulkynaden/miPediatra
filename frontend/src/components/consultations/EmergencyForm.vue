<template>
  <div class="grid sm:grid-cols-1 md:grid-cols-2 gap-4 pt-4">
    <div class="md:col-span-1">
      <div class="whitespace-nowrap text-align-right">Altura:</div>
      <div class="flex-grow ml-2"></div>
    </div>

    <div class="md:col-span-1 flex">
      <div class="whitespace-nowrap text-align-right">Peso:</div>
      <div class="flex-grow ml-2"></div>
    </div>

    <div class="md:col-span-1 flex">
      <div class="whitespace-nowrap text-align-right">Diámetro craneal:</div>
      <div class="flex-grow ml-2"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmergencyForm",
  props: ["formData"],
};
</script>

<style scoped></style>
