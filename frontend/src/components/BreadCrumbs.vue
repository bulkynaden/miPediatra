<template>
  <div class="
            breadcrumb
            flex
            items-end
            border-b border-gray-300
            dark:border-foreground
            pb-4
            mb-6">
    <ul>
      <li class="border-r border-gray-400 inline pr-2 dark:text-gray-400">
        {{ parentTitle }}
      </li>
      <li class="inline pl-2 text-gray-600 dark:text-gray-400">
        {{ subParentTitle }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    parentTitle: {
      type: String,
      required: true,
    },
    subParentTitle: {
      type: String,
      required: true,
    },
  },
};
</script>


