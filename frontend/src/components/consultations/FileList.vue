<template>
  <div class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
    <div class="text-sm font-bold leading-6 text-gray-900">Archivos</div>
    <div class="mt-2 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
      <ul
        v-for="file in files"
        v-if="files.length > 0"
        :key="file.id"
        class="divide-y divide-gray-100 rounded-md border border-gray-200"
        role="list"
      >
        <FileListItem :file="file" />
      </ul>
      <div
        v-else
        class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0"
      >
        No existen archivos.
      </div>
    </div>
  </div>
</template>

<script>
import FileListItem from "./FileListItem.vue";

export default {
  name: "FileList",
  components: { FileListItem },
  props: ["files"],
};
</script>

<style scoped></style>
