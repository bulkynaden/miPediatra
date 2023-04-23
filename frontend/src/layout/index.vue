<template>
  <div class="app">
    <TheHeader />
    <TheSidebar />
    <div
      :class="this.sidebarToggleProperties.isSideNavOpen === true ? '' : 'full'"
      class="main-content-wrap"
    >
      <main>
        <div class="main-content-wrap flex flex-col flex-grow print-area pt-10">
          <TheLoadingLogo v-if="showLoadingLogo" />
          <router-view v-slot="{ Component }">
            <transition mode="out-in" name="scale">
              <component :is="Component" />
            </transition>
          </router-view>
          <div class="flex-grow-1"></div>
          <TheFooter />
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import { useLargeSidebarStore } from "@/store/largeSidebarStore";
import { mapState } from "pinia";
import TheFooter from "@/layout/TheFooter.vue";
import TheSidebar from "@/layout/TheSidebar.vue";
import TheHeader from "@/layout/TheHeader.vue";
import { usePatientStore } from "@/store/patientStore.js";
import TheLoadingLogo from "@/layout/TheLoadingLogo.vue";

export default {
  components: { TheHeader, TheSidebar, TheFooter, TheLoadingLogo },
  data() {
    return {
      isLoading: null,
    };
  },
  computed: {
    ...mapState(useLargeSidebarStore, {
      sidebarToggleProperties: "sidebarToggleProperties",
    }),
    showLoadingLogo() {
      return this.isLoading;
    },
  },
  beforeMount() {
    this.fetchPatients();
  },
  methods: {
    async fetchPatients() {
      const patientStore = usePatientStore();
      try {
        this.isLoading = true;
        await patientStore.fetchPatients();
      } catch (error) {
        console.error("Error fetching patients:", error);
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.scale-enter-active,
.scale-leave-active {
  transition: all 0.5s ease;
}

.scale-enter-from,
.scale-leave-to {
  opacity: 0;
  transform: scale(0.9);
}

.flex-grow-1 {
  -webkit-box-flex: 1 !important;
  -ms-flex-positive: 1 !important;
  flex-grow: 1 !important;
}

.app {
  width: 100%;
  height: 100%;

  .main-content-wrap {
    width: calc(100% - 120px);
    margin-left: 90px;
    // min-height: 100vh;
    min-height: calc(100vh - 60px);
    padding-top: 50px;
    transition: all 0.24s ease-in-out;

    .main-content-body {
      min-height: calc(100vh - 80px);
    }

    &.full {
      width: 100%;
      margin-left: 0;
      transition: all 0.24s ease-in-out;
    }

    @media screen and (max-width: 991px) {
      width: 100%;
      margin-left: 0;
      padding-right: 16px;
      padding-left: 16px;
    }
  }
}
</style>
