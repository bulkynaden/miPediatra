import { createApp } from "vue";
import App from "./App.vue";

// CSS Globales
import "./assets/scss/global.scss";
import "./index.css";

// Store
import { createPinia } from "pinia";

// Router
import router from "./router/router.js";

// PerfectScrollbar
import PerfectScrollbar from "vue3-perfect-scrollbar";
import "vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css";

// Syncfusion
import { registerLicense } from "@syncfusion/ej2-base";
import { ButtonPlugin } from "@syncfusion/ej2-vue-buttons";
import { DialogPlugin, TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { TextBoxPlugin, UploaderPlugin } from "@syncfusion/ej2-vue-inputs";

// SweetAlert2
import VueSweetalert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

// ApexCharts
import VueApexCharts from "vue3-apexcharts";

// Los componentes base
import BaseCard from "./components/Base/BaseCard.vue";
import BaseBtn from "./components/Base/BaseBtn.vue";
import Breadcrumbs from "@/components/BreadCrumbs.vue";

registerLicense(
    "ORg4AjUWIQA/Gnt2VVhkQlFadVdJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxQdkZiWH5fcXVRT2ZdU0w="
);

createApp(App)
    .component("BaseCard", BaseCard)
    .component("BaseBtn", BaseBtn)
    .component("Breadcrumbs", Breadcrumbs)
    .use(VueSweetalert2)
    .use(ButtonPlugin)
    .use(TooltipPlugin)
    .use(UploaderPlugin)
    .use(DialogPlugin)
    .use(TextBoxPlugin)
    .use(PerfectScrollbar)
    .use(VueApexCharts)
    .use(createPinia())
    .use(router)
    .mount("#app");