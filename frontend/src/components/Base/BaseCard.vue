<template>
  <div class="card bg-white dark:bg-foreground dark:text-white">
    <div v-if="hasHeaderSlot" class="header">
      <slot name="cardHeader" />
    </div>
    <div :class="noPadding ? 'noPadding' : ''" class="card-body">
      <slot />
    </div>
  </div>
</template>

<script>
import { defineComponent } from "vue";

export default defineComponent({
  props: {
    noPadding: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    hasHeaderSlot() {
      return !!this.$slots.cardHeader;
    },
  },
});
</script>

<style lang="scss" scoped>
.card {
  border-radius: 10px;
  box-shadow: 0 4px 20px 1px rgb(0 0 0 / 6%), 0 1px 4px rgb(0 0 0 / 8%);
  margin-bottom: 10px;
  overflow: hidden;

  .card-body {
    -webkit-box-flex: 1;
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    padding: 1.25rem;

    &.noPadding {
      padding: 0 !important;
    }
  }

  .header {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.25rem;
    font-weight: 700;
    color: #ffffff;
    text-transform: uppercase;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 1rem;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  }
}
</style>
