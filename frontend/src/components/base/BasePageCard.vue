<template>
  <div>
    <BreadCrumbs
      :parent-title="parentTitle"
      :sub-parent-title="subParentTitle"
    ></BreadCrumbs>
    <div class="page-card">
      <div v-if="hasHeader">
        <div class="page-card-header">
          <div
            class="header-content flex flex-wrap items-center justify-center md:justify-between"
          >
            <div class="title mb-2 md:mb-0">
              <slot name="title" />
            </div>
            <div class="button">
              <slot name="button" />
            </div>
          </div>
        </div>
      </div>
      <div class="page-card-body">
        <slot />
      </div>
    </div>
  </div>
</template>
<script>
import BreadCrumbs from "@/components/base/BaseBreadCrumbs.vue";

export default {
  props: {
    parentTitle: {
      type: String,
      required: true,
    },
    subParentTitle: {
      type: String,
      required: false,
    },
  },
  components: { BreadCrumbs },
  computed: {
    hasHeader() {
      return this.$slots.title || this.$slots.button;
    },
  },
};
</script>

<style lang="scss" scoped>
.page-card {
  border-radius: 10px;
  box-shadow: 0 4px 20px 1px rgb(0 0 0 / 6%), 0 1px 4px rgb(0 0 0 / 8%);
  margin-bottom: 10px;
  overflow: hidden;
  background-color: #ffffff;

  .page-card-header {
    background: linear-gradient(135deg, #243b55 0%, #cce7ff 100%);
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  }

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 1rem;
  }

  .title {
    font-size: 1.5rem;
    font-weight: 700;
    color: #ffffff;
  }

  .page-card-body {
    -webkit-box-flex: 1;
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    padding: 1.25rem;
    background-color: #f7f7f7;
  }
}
</style>
