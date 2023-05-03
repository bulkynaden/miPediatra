<template>
  <div>
    <div
      v-if="value"
      class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0 border-b border-gray-200 pb-2"
    >
      <div class="text-sm font-bold leading-6 text-gray-900">
        {{ field }}
      </div>
      <div class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
        {{ value }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    field: {
      type: String,
      required: true,
    },
    value: {
      type: String,
      default: "",
      required: true,
    },
  },
  name: "ConsultationDetailsItem",
};
</script>

<style scoped></style>
