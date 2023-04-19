<template>
  <div class="card bg-white dark:bg-foreground dark:text-white">
    <slot class="card-header" name="cardHeader"/>
    <div :class="noPadding ? 'noPadding' : ''" class="card-body">
      <slot/>
    </div>
  </div>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  props: {
    noPadding: {
      type: Boolean,
      default: false,
    },
  },
})
</script>

<style lang="scss" scoped>
.card {
  border-radius: 10px;
  box-shadow: 0 4px 20px 1px rgb(0 0 0 / 6%), 0 1px 4px rgb(0 0 0 / 8%);
  margin-bottom: 10px;

  .card-body {
    -webkit-box-flex: 1;
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    padding: 1.25rem;

    &.noPadding {
      padding: 0 !important;
    }
  }

  .card-header {
    margin-bottom: 10px;
    font-size: 2rem;
  }
}
</style>
