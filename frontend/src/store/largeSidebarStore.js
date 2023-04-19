import { defineStore } from "pinia";

export const useLargeSidebarStore = defineStore({
    id: "largeSidebar",
    state: () => ({
        sidebarToggleProperties: {
            isSideNavOpen: true,
            isSecondarySideNavOpen: false,
            isActiveSecondarySideNav: false,
        },
    }),
    getters: {
        getSideBarToggleProperties() {
            const state = this.getState();
            return state.sidebarToggleProperties;
        },
    },
    actions: {
        toggleSidebarProperties() {
            this.sidebarToggleProperties.isSideNavOpen =
                !this.sidebarToggleProperties.isSideNavOpen;
        },
        toggleSecondarySidebarProperties() {
            this.sidebarToggleProperties.isSecondarySideNavOpen =
                !this.sidebarToggleProperties.isSecondarySideNavOpen;
        },
        toggleSecondarySidebarPropertiesViaMenuItem(data) {
            this.sidebarToggleProperties.isSecondarySideNavOpen = data;
            this.sidebarToggleProperties.isActiveSecondarySideNav = data;
        },
    },
});
