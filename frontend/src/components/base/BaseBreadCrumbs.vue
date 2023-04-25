<template>
  <div
    class="breadcrumb flex items-center border-b border-gray-300 dark:border-foreground pb-4 mb-6"
  >
    <ul class="flex items-center space-x-3">
      <li class="inline font-medium text-gray-600 dark:text-gray-400">
        <a class="hover:text-gray-800 dark:hover:text-gray-300">{{
          parentTitle
        }}</a>
      </li>
      <li class="inline">
        <svg
          class="h-5 w-5 text-gray-400"
          fill="currentColor"
          viewBox="0 0 20 20"
        >
          <path
            d="M5.5 13a.5.5 0 01-.5-.5V7a.5.5 0 01.5-.5h1a.5.5 0 01.5.5v5.5a.5.5 0 01-.5.5h-1zm3 0a.5.5 0 01-.5-.5V7a.5.5 0 01.5-.5h1a.5.5 0 01.5.5v5.5a.5.5 0 01-.5.5h-1zm3 0a.5.5 0 01-.5-.5V7a.5.5 0 01.5-.5h1a.5.5 0 01.5.5v5.5a.5.5 0 01-.5.5h-1z"
          />
        </svg>
      </li>
      <li class="inline text-gray-600 dark:text-gray-400">
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

<style lang="scss" scoped>
.breadcrumb ul {
  list-style: none;
}

.breadcrumb a {
  transition: all 0.3s ease;
}
</style>
