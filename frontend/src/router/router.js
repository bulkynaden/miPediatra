import { createRouter, createWebHistory } from "vue-router";
import NotFound from "@/views/sessions/404.vue";
import { useLargeSidebarStore } from "@/store/largeSidebarStore";

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import("../layout/index.vue"),
    meta: {
      title: "Home",
    },
    redirect: "/home/home",
    children: [
      {
        path: "/home",
        redirect: "/home/home",
        component: () => import("../views/home/index.vue"),
        meta: {
          title: "Inicio",
        },
        children: [
          {
            path: "home",
            name: "HomePage",
            component: () => import("../views/home/HomePage.vue"),
          },
          {
            path: "historico",
            name: "TimelinePage",
            component: () => import("../views/home/TimelinePage.vue"),
          },
        ],
      },
      {
        path: "/misbebes",
        redirect: "/misbebes/listado",
        name: "patients",
        component: () => import("../views/patients/index.vue"),
        meta: {
          title: "Mis bebés",
        },
        children: [
          {
            path: "listado",
            name: "PatientsListPage",
            component: () => import("../views/patients/PatientsListPage.vue"),
          },
          {
            path: "agregar",
            name: "PatientAddPage",
            component: () => import("../views/patients/PatientAddPage.vue"),
          },
          {
            path: "editar/:id",
            name: "PatientEditPage",
            component: () => import("../views/patients/PatientEditPage.vue"),
          },
          {
            path: "detalles/:id",
            name: "PatientDetailsPage",
            component: () => import("../views/patients/PatientDetailsPage.vue"),
          },
          {
            path: "notas",
            name: "PatientsNotesPage",
            component: () => import("../views/patients/PatientsNotesPage.vue"),
          },
        ],
      },
      {
        path: "/mediciones",
        name: "measurements",
        redirect: "/mediciones/diametro",
        component: () => import("../views/measurements/index.vue"),
        meta: {
          title: "Mediciones",
        },
        children: [
          {
            path: "diametro",
            name: "CranialDiameterPage",
            component: () =>
              import("../views/measurements/CranialDiameterPage.vue"),
          },
          {
            path: "altura",
            name: "HeightPage",
            component: () => import("../views/measurements/HeightPage.vue"),
          },
          {
            path: "peso",
            name: "WeightPage",
            component: () => import("../views/measurements/WeightPage.vue"),
          },
        ],
      },

      {
        path: "/consultas",
        name: "consultations",
        redirect: "/consultas/periodicas",
        component: () => import("../views/consultations/index.vue"),
        meta: {
          title: "Consultas médicas",
        },
        children: [
          {
            path: "listado",
            name: "ConsultationsListPage",
            component: () =>
              import("../views/consultations/ConsultationsListPage.vue"),
          },
          {
            path: "agregar",
            name: "ConsultationAddPage",
            component: () =>
              import("../views/consultations/ConsultationAddPage.vue"),
          },
          {
            path: "periodicas",
            name: "RegularVisitPage",
            component: () =>
              import("../views/consultations/RegularVisitPage.vue"),
          },
          {
            path: "urgencias",
            name: "EmergencyPage",
            component: () => import("../views/consultations/EmergencyPage.vue"),
          },
        ],
      },
      {
        path: "/graficos",
        name: "GraphsPage",
        component: () => import("../views/graphs/GraphsPage.vue"),
        meta: {
          title: "Gráficos",
        },
      },
    ],
  },
  { path: "/:path(.*)", component: NotFound },
];

const router = createRouter({
  history: createWebHistory(),
  scrollBehavior(to, from, savedPosition) {
    return { left: 0, top: 0 };
  },
  routes,
});
router.beforeEach((to, from, next) => {
  document.title = `${to.name} - ${import.meta.env.VITE_APP_TITLE}`;
  next();
});
router.afterEach(() => {
  const store = useLargeSidebarStore();

  const loader = document.getElementById("loading_wrap");
  if (loader) {
    loader.style.display = "none";
  }

  const sideNav = store.sidebarToggleProperties.isSideNavOpen;
  const secondarySideNav = store.sidebarToggleProperties.isSecondarySideNavOpen;

  if (window.innerWidth <= 1200) {
    if (sideNav) {
      store.toggleSidebarProperties();
    }
    if (secondarySideNav) {
      store.toggleSecondarySidebarProperties();
    }
  } else {
    if (secondarySideNav) {
      store.toggleSecondarySidebarProperties();
    }
  }
});

export default router;
