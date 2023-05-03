<script>
import { mapActions, mapState } from "pinia";
import { useLargeSidebarStore } from "@/store/largeSidebarStore";
import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/vue";

export default {
  components: { Menu, MenuItems, MenuItem, MenuButton },
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
    sideBarToggle() {
      const sidenav = this.sidebarToggleProperties.isSideNavOpen;
      const secondarySideNav =
        this.sidebarToggleProperties.isSecondarySideNavOpen;

      if (!sidenav) {
        this.toggleSidebarProperties();
      } else if (sidenav && secondarySideNav) {
        this.toggleSecondarySidebarProperties();
      } else {
        this.toggleSidebarProperties();
      }
    },
  },
};
</script>

<template>
  <div
    class="header-wrapper flex bg-white justify-between px-4 dark:bg-foreground dark:text-gray-300"
  >
    <div class="flex items-center">
      <div class="mx-0 sm:mx-3 sidebar-toggle">
        <button
          aria-label="Sidebar Toggle"
          class="menu-toggle cursor-pointer text-gray-500 align-middle focus:outline-none"
          role="button"
          type="button"
          @click="sideBarToggle"
        >
          <svg
            class="h-6 w-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M4 6h16M4 12h16M4 18h16"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
            />
          </svg>
        </button>
      </div>
      <div class="logo flex justify-center">
        <img
          alt="logo"
          class="w-16 mr-6 ml-3"
          src="/logo-color.png"
          srcset=""
        />
      </div>
      <h2>miPediatra</h2>
    </div>
    <div class="flex items-center">
      <!-- alertas  -->
      <Menu as="div" class="relative inline-block text-left">
        <div>
          <MenuButton
            class="btn btn-sm hover:bg-gray-100 dark:hover:bg-dark rounded badge-top-container inline-flex justify-center w-full px-4 py-2 text-sm font-medium"
          >
            <span class="badge-count text-white bg-primary dark:text-gray-300"
              >2</span
            >
            <i
              class="i-Bell text-xl header-icon text-gray-800 dark:text-gray-300"
            ></i>
          </MenuButton>
        </div>

        <MenuItems
          class="absolute right-0 w-44 mt-2 overflow-hidden origin-top-right bg-white dark:bg-dark rounded-md custom-box-shadow focus:outline-none"
        >
          <div class="">
            <MenuItem v-slot="{ active }">
              <div
                :class="[
                  active
                    ? 'bg-primary text-white'
                    : 'text-gray-900 dark:text-gray-300',
                  'group flex  items-center w-full px-4 py-2 text-sm',
                ]"
                aria-label="notifications"
                role="button"
              >
                <div class="flex flex-1 justify-between">
                  <div>
                    <h6>Vacunas</h6>
                    <p :class="[active ? 'text-gray-300' : 'text-gray-500']">
                      Rabia
                    </p>
                  </div>
                  <div>
                    <p :class="[active ? 'text-gray-200' : 'text-gray-400']">
                      1 día
                    </p>
                  </div>
                </div>
              </div>
            </MenuItem>
            <MenuItem v-slot="{ active }">
              <div
                :class="[
                  active
                    ? 'bg-primary text-white'
                    : 'text-gray-900 dark:text-gray-300',
                  'group flex  items-center w-full px-4 py-2 text-sm',
                ]"
                aria-label="notifications"
                role="button"
              >
                <div class="flex flex-1 justify-between">
                  <div>
                    <h6>Visita</h6>
                    <p :class="[active ? 'text-gray-300' : 'text-gray-500']">
                      Pediatra
                    </p>
                  </div>
                  <div>
                    <p :class="[active ? 'text-gray-200' : 'text-gray-400']">
                      4 días
                    </p>
                  </div>
                </div>
              </div>
            </MenuItem>
          </div>
        </MenuItems>
      </Menu>

      <!-- perfil  -->
      <Menu as="div" class="relative inline-block text-left">
        <div>
          <MenuButton
            class="btn btn-sm hover:bg-gray-100 dark:hover:bg-dark rounded badge-top-container inline-flex justify-center w-full px-4 py-2 text-sm font-medium"
          >
            <i
              class="i-Administrator text-xl header-icon text-gray-800 dark:text-gray-300"
            ></i>
          </MenuButton>
        </div>

        <MenuItems
          class="absolute right-0 w-44 mt-2 overflow-hidden origin-top-right bg-white dark:bg-dark rounded-md custom-box-shadow focus:outline-none"
        >
          <MenuItem v-slot="{ active }">
            <button
              :class="[
                active
                  ? 'bg-primary text-white'
                  : 'text-gray-900 dark:text-gray-300',
                'group flex  items-center w-full px-4 py-2 text-sm',
              ]"
              aria-label="Account Settings"
              role="button"
            >
              Configuración
            </button>
          </MenuItem>
          <MenuItem v-slot="{ active }">
            <button
              :class="[
                active
                  ? 'bg-primary text-white'
                  : 'text-gray-900 dark:text-gray-300',
                'group flex  items-center w-full px-4 py-2 text-sm',
              ]"
              aria-label="Account Settings"
              role="button"
            >
              Salir
            </button>
          </MenuItem>
        </MenuItems>
      </Menu>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.sidebar-toggle {
  margin-left: 5px;
  margin-right: 10px;
}

.header-wrapper {
  position: fixed;
  top: 0;
  width: 100%;
  height: 80px;
  z-index: 100;
  box-shadow: 0 1px 15px rgb(0 0 0 / 4%), 0 1px 6px rgb(0 0 0 / 4%);
}
</style>
