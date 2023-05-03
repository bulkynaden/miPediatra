<script>
import { useLargeSidebarStore } from "@/store/largeSidebarStore";
import { useRoute } from "vue-router";
import { mapActions, mapState } from "pinia";

export default {
  name: "TheSidebar",
  data() {
    return {
      route: useRoute(),
      selectedParentMenu: "",
      isMenuOver: false,
    };
  },
  mounted() {
    this.toggleSelectedParentMenu();
    document.addEventListener("click", this.returnSelectedParentMenu);
    window.addEventListener("resize", this.handleWindowResize);
  },
  beforeUnmount() {
    document.removeEventListener("click", this.returnSelectedParentMenu);
    window.removeEventListener("resize", this.handleWindowResize);
  },
  computed: {
    ...mapState(useLargeSidebarStore, {
      sidebarToggleProperties: "sidebarToggleProperties",
    }),
  },
  methods: {
    ...mapActions(useLargeSidebarStore, {
      toggleSidebarProperties: "toggleSidebarProperties",
      toggleSecondarySidebarProperties: "toggleSecondarySidebarProperties",
      toggleSecondarySidebarPropertiesViaMenuItem:
        "toggleSecondarySidebarPropertiesViaMenuItem",
    }),
    toggleSelectedParentMenu() {
      const currentParentUrl = this.route.path
        .split("/")
        .filter((x) => x !== "")[0];

      if (currentParentUrl !== undefined) {
        this.selectedParentMenu = currentParentUrl.toLowerCase();
      } else {
        this.selectedParentMenu = "home";
      }
    },
    toggleSubMenu(e) {
      const hasSubmenu = e.target.dataset.submenu;
      const parent = e.target.dataset.item;
      if (hasSubmenu) {
        this.selectedParentMenu = parent;
        this.toggleSecondarySidebarPropertiesViaMenuItem(true);
      } else {
        this.selectedParentMenu = parent;
        this.toggleSecondarySidebarPropertiesViaMenuItem(false);
      }
    },
    handleWindowResize() {
      const sidenav = this.sidebarToggleProperties.isSideNavOpen;
      const secondarySideNav =
        this.sidebarToggleProperties.isSecondarySideNavOpen;

      if (window.innerWidth <= 1200) {
        if (sidenav) {
          this.toggleSidebarProperties();
        }
        if (secondarySideNav) {
          this.toggleSecondarySidebarProperties();
        }
      } else {
        if (!sidenav) {
          this.toggleSidebarProperties();
        }
      }
    },
    returnSelectedParentMenu() {
      if (!this.isMenuOver) {
        this.toggleSelectedParentMenu();
      }
    },
    removeOverlay() {
      this.toggleSecondarySidebarProperties();
      if (window.innerWidth <= 1200) {
        this.toggleSidebarProperties();
      }
      this.toggleSelectedParentMenu();
    },
  },
};
</script>

<template>
  <div
    class="side-content-wrap"
    @mouseenter="isMenuOver = true"
    @mouseleave="isMenuOver = false"
    @touchstart="isMenuOver = true"
  >
    <div class="side-content-wrap">
      <div
        :class="
          this.sidebarToggleProperties.isSideNavOpen === true ? 'open' : ''
        "
        class="sidebar-left bg-white dark:bg-foreground"
      >
        <perfect-scrollbar>
          <ul class="navigation-left dark:bg-foreground dark:text-gray-300">
            <li
              :class="selectedParentMenu === 'home' ? 'active' : ''"
              :data-submenu="true"
              class="nav-item border-b border-gray-200 dark:border-dark"
              data-item="home"
              @mouseenter="toggleSubMenu"
            >
              <a class="nav-item-hold">
                <i class="i-Home-2 text-3xl"></i>
                <p>Inicio</p>
              </a>
            </li>
            <li
              :class="selectedParentMenu === 'patients' ? 'active' : ''"
              class="nav-item border-b border-gray-200 dark:border-dark"
              data-item="patients"
              @mouseenter="toggleSubMenu"
            >
              <router-link
                :to="{ name: 'PatientsListPage' }"
                class="nav-item-hold"
              >
                <i class="i-Baby text-3xl"></i>
                <p>Mis pacientes</p>
              </router-link>
            </li>
            <li
              :class="selectedParentMenu === 'consultations' ? 'active' : ''"
              class="nav-item border-b border-gray-200 dark:border-dark"
              data-item="consultations"
              @mouseenter="toggleSubMenu"
            >
              <router-link
                :to="{ name: 'ConsultationsListPage' }"
                class="nav-item-hold"
              >
                <i class="i-Hospital1 text-3xl"></i>
                <p>Consultas médicas</p>
              </router-link>
            </li>
            <li
              :class="selectedParentMenu === 'measurements' ? 'active' : ''"
              :data-submenu="true"
              class="nav-item border-b border-gray-200 dark:border-dark"
              data-item="measurements"
              @mouseenter="toggleSubMenu"
            >
              <a class="nav-item-hold">
                <i class="i-Gaugage-2 text-3xl"></i>
                <p>Mediciones</p>
              </a>
            </li>
            <li
              :class="selectedParentMenu === 'graphs' ? 'active' : ''"
              class="nav-item border-b border-gray-200 dark:border-dark"
              data-item="graphs"
              @mouseenter="toggleSubMenu"
            >
              <router-link :to="{ name: 'GraphsPage' }" class="nav-item-hold">
                <i class="i-Bar-Chart-2 text-3xl"></i>
                <p>Gráficos</p>
              </router-link>
            </li>
          </ul>
        </perfect-scrollbar>
      </div>

      <div
        :class="{ open: this.sidebarToggleProperties.isSecondarySideNavOpen }"
        class="sidebar-left-secondary shadow bg-white dark:bg-foreground dark:text-gray-300"
      >
        <ul
          :class="selectedParentMenu === 'home' ? 'block' : 'hidden'"
          class="mb-4 childNav"
          data-parent="home"
        >
          <li>
            <router-link :to="{ name: 'HomePage' }">
              <i class="nav-icon i-Home-2 mr-2"></i>
              <span class="item-name"> Resumen </span>
            </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'TimelinePage' }">
              <i class="nav-icon i-Calendar mr-2"></i>
              <span class="item-name"> Calendario </span>
            </router-link>
          </li>
        </ul>
        <ul
          :class="selectedParentMenu === 'measurements' ? 'block' : 'hidden'"
          class="mb-4 childNav"
          data-parent="measurements"
        >
          <li>
            <router-link :to="{ name: 'CranialDiameterPage' }" class>
              <i class="nav-icon i-Brain1 mr-2"></i>
              <span class="item-name"> Diámetro craneal </span>
            </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'HeightPage' }" class>
              <i class="nav-icon i-Men mr-2"></i>
              <span class="item-name"> Altura </span>
            </router-link>
          </li>
          <li>
            <router-link :to="{ name: 'WeightPage' }" class>
              <i class="nav-icon i-Scale mr-2"></i>
              <span class="item-name"> Peso </span>
            </router-link>
          </li>
        </ul>
      </div>
    </div>

    <div
      :class="{
        open: this.sidebarToggleProperties.isSecondarySideNavOpen,
      }"
      class="sidebar-overlay"
      @click="removeOverlay()"
    ></div>
  </div>
</template>

<style lang="scss" scoped>
.submenuLi {
  @apply dark:bg-foreground;
  &:hover {
    .submenuli-icon {
      @apply text-primary;
    }
  }

  .submenuli-icon {
    color: #9ca3af;
  }
}

.dark .dark\:bg-dark {
  .submneu-nested-link {
    color: #f3f4f6 !important;

    &:hover {
      background-color: transparent !important;
      @apply text-primary;
    }
  }
}

.submneu-nested-link {
  padding: 0 !important;
  color: #000 !important;

  &:hover {
    background-color: transparent !important;
    @apply text-primary;
  }
}

.side-content-wrap {
  .sidebar-left {
    position: fixed;
    top: 80px;
    left: -120px;
    width: 120px;

    box-shadow: 0 4px 20px 1px rgba(0, 0, 0, 0.06),
      0 1px 4px rgba(0, 0, 0, 0.08);
    z-index: 90;
    transition: left 0.24s ease-in-out;

    &.open {
      left: 0;
      transition: left 0.24s ease-in-out;
    }

    .ps {
      height: calc(100vh - 80px);
    }

    .navigation-left {
      list-style: none;
      text-align: center;
      width: 120px;
      height: 100%;
      margin: 0;
      padding: 0;

      .nav-item,
      .nav-item-single {
        position: relative;
        display: block;
        width: 100%;
        overflow: hidden;
        cursor: pointer;
        // border-bottom: 1px solid #dee2e6;

        &:hover {
          .nav-item-hold {
            @apply text-primary;
          }

          &:after {
            content: "";
            position: absolute;
            width: 30px;
            height: 30px;
            bottom: -15px;
            right: -15px;
            transform: rotate(45deg);
            @apply bg-primary;
          }
        }

        &.active {
          @apply text-primary;

          &:after {
            content: "";
            position: absolute;
            width: 30px;
            height: 30px;
            bottom: -15px;
            right: -15px;
            transform: rotate(45deg);
            @apply bg-primary;
          }
        }

        .nav-item-hold {
          display: block;
          width: 100%;
          padding: 26px 0;

          p {
            font-size: 13px;
          }

          span.material-icons {
            font-size: 2rem;
          }
        }
      }
    }
  }

  .sidebar-left-secondary {
    position: fixed;
    top: 80px;
    left: calc(-220px - 20px);
    z-index: 89;
    height: calc(100vh - 80px);
    width: 220px;
    padding: 0.75rem 0;
    transition: left 0.24s ease-in-out;
    // background: #fff;

    &.open {
      left: 120px;
    }

    ul.childNav {
      li {
        &.dropdown-sidemenu {
          display: block;
          transition: left 0.24s ease-in-out;

          &.open {
            a {
              .dd-arrow {
                transform: rotate(90deg);
                transition: left 0.24s ease-in-out;
              }
            }

            ul.submenu {
              display: block;
              max-height: 1000px;
              transition: left 0.24s ease-in-out;
            }
          }

          a {
            .dd-arrow {
              margin-left: auto !important;
              transition: left 0.24s ease-in-out;
            }
          }
        }

        a {
          text-transform: capitalize;
          display: flex;
          align-items: center;
          font-size: 16px;
          cursor: pointer;
          padding: 12px 24px;
          transition: 0.15s all ease-in;

          &:hover {
            background-color: #f3f4f6;
            @apply text-primary dark:bg-dark;
          }

          &.router-link-active.router-link-exact-active {
            @apply text-primary;
          }
        }

        ul.submenu {
          @apply bg-gray-50;
          display: none;
          max-height: 0;
          transition: left 0.24s ease-in-out;

          &.open {
            display: block;
            transition: left 0.24s ease-in-out;
          }

          li {
            a {
              padding-left: 48px;
            }
          }
        }
      }
    }
  }

  .sidebar-overlay {
    display: none;
    position: fixed;
    width: calc(100% - 120px - 220px);
    height: calc(100vh - 80px);
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0);
    z-index: 101;
    cursor: w-resize;

    &.open {
      display: block;
    }
  }
}
</style>
