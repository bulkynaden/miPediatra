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
import {
  NumericTextBoxPlugin,
  TextBoxPlugin,
} from "@syncfusion/ej2-vue-inputs";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";

// SweetAlert2
import VueSweetalert2 from "vue-sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";

// ApexCharts
import VueApexCharts from "vue3-apexcharts";

// Los componentes base
import BaseCard from "./components/Base/BaseCard.vue";
import BaseBtn from "./components/Base/BaseBtn.vue";
import BaseFormInput from "@/components/Base/BaseFormInput.vue";
import BreadCrumbs from "@/components/BreadCrumbs.vue";

registerLicense(
  "Mgo+DSMBaFt+QHFqUUdrXVNbdV5dVGpAd0N3RGlcdlR1fUUmHVdTRHRcQllhS39VdEFiXnxXdHM=;Mgo+DSMBPh8sVXJ1S0d+WFBPd11dXmJWd1p/THNYflR1fV9DaUwxOX1dQl9gSXpRdERlW3tfd3BVTmc=;ORg4AjUWIQA/Gnt2VFhhQlVFfV5AQmBIYVp/TGpJfl96cVxMZVVBJAtUQF1hSn5XdEdiX31acXNRRGFb;MTgxMDAzM0AzMjMxMmUzMTJlMzQzMWt1RjFkdXJ0ZUswS1BNVmJWbUo2UGE4NW9lV3o4STVPaTQrb0d1MER3a0U9;MTgxMDAzNEAzMjMxMmUzMTJlMzQzMWh5dVdpUGNDOU5DMWEzS09rSWkwamp6UlJ1QUsvNXkwcnRWR0FpNDNwcUk9;NRAiBiAaIQQuGjN/V0d+XU9Ad1RDX3xKf0x/TGpQb19xflBPallYVBYiSV9jS31TckdhWHlcdHVSQmldUw==;MTgxMDAzNkAzMjMxMmUzMTJlMzQzMUNFendiS0diWkZjVHJYc1FJU0c3djJmcmExWHRvZ0FSa05IMzhrSXZ1SHM9;MTgxMDAzN0AzMjMxMmUzMTJlMzQzMVdxdnBKcWhuM0xST1loYnZXL3NSWlgvTldGak51emJRNWJ3aUZNMllqOVU9;Mgo+DSMBMAY9C3t2VFhhQlVFfV5AQmBIYVp/TGpJfl96cVxMZVVBJAtUQF1hSn5XdEdiX31acXNQQGdb;MTgxMDAzOUAzMjMxMmUzMTJlMzQzMUFHdDdjYzNiSTJCTHNnbDdIYTVKczZuU1pkVnNFaVJJUGF4VUd0RGgzaDQ9;MTgxMDA0MEAzMjMxMmUzMTJlMzQzMWM2MmlRUjVpZDRnN0tWVjJFblJobEpXMHZlZHl2a2N6RmNncVRQQzlMY1E9;MTgxMDA0MUAzMjMxMmUzMTJlMzQzMUNFendiS0diWkZjVHJYc1FJU0c3djJmcmExWHRvZ0FSa05IMzhrSXZ1SHM9"
);

createApp(App)
  .component("BaseCard", BaseCard)
  .component("BaseBtn", BaseBtn)
  .component("BaseFormInput", BaseFormInput)
  .component("BreadCrumbs", BreadCrumbs)
  .use(VueSweetalert2)
  .use(ButtonPlugin)
  .use(TooltipPlugin)
  .use(DialogPlugin)
  .use(TextBoxPlugin)
  .use(DatePickerPlugin)
  .use(NumericTextBoxPlugin)
  .use(PerfectScrollbar)
  .use(VueApexCharts)
  .use(createPinia())
  .use(router)
  .mount("#app");
