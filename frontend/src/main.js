import { createApp } from "vue";
import App from "./App.vue";
import "./services/ln10.js";

// CSS Globales
import "./assets/scss/global.scss";
import "./index.css";

// Store
import { createPinia } from "pinia";

// Router
import router from "./router/router.js";

// Firebase
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

// PerfectScrollbar
import PerfectScrollbar from "vue3-perfect-scrollbar";
import "vue3-perfect-scrollbar/dist/vue3-perfect-scrollbar.css";

// Syncfusion
import { registerLicense } from "@syncfusion/ej2-base";
import { ButtonPlugin } from "@syncfusion/ej2-vue-buttons";
import { DialogPlugin, TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import {
  NumericTextBoxPlugin,
  TextBoxPlugin,
  UploaderPlugin,
} from "@syncfusion/ej2-vue-inputs";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import { ProgressButtonPlugin } from "@syncfusion/ej2-vue-splitbuttons";
// SweetAlert2
import VueSweetalert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

// ApexCharts
import VueApexCharts from "vue3-apexcharts";

// Los componentes base
import BaseCard from "./components/base/BaseCard.vue";
import BaseBtn from "./components/base/BaseBtn.vue";
import BaseFormInput from "./components/base/BaseFormInput.vue";
import BreadCrumbs from "./components/base/BaseBreadCrumbs.vue";
import BasePageCard from "./components/base/BasePageCard.vue";

registerLicense(import.meta.env.VITE_APP_SYNCFUSION_LICENSE);

const firebaseConfig = {
  apiKey: import.meta.env.VITE_APP_API_KEY,
  authDomain: import.meta.env.VITE_APP_AUTH_DOMAIN,
  databaseURL: import.meta.env.VITE_APP_DATABASE_URL,
  projectId: import.meta.env.VITE_APP_PROJECT_ID,
  storageBucket: import.meta.env.VITE_APP_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_APP_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_APP_APP_ID,
};

getStorage(initializeApp(firebaseConfig));

createApp(App)
  .component("BaseCard", BaseCard)
  .component("BaseBtn", BaseBtn)
  .component("BaseFormInput", BaseFormInput)
  .component("BreadCrumbs", BreadCrumbs)
  .component("BasePageCard", BasePageCard)
  .use(VueSweetalert2)
  .use(ButtonPlugin)
  .use(TooltipPlugin)
  .use(DialogPlugin)
  .use(TextBoxPlugin)
  .use(DatePickerPlugin)
  .use(NumericTextBoxPlugin)
  .use(UploaderPlugin)
  .use(ProgressButtonPlugin)
  .use(PerfectScrollbar)
  .use(VueApexCharts)
  .use(createPinia())
  .use(router)
  .mount("#app");
