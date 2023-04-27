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
            meta: {
              title: "Inicio",
            },
          },
          {
            path: "historico",
            name: "TimelinePage",
            component: () => import("../views/home/TimelinePage.vue"),
            meta: {
              title: "Histórico",
            },
          },
        ],
      },
      {
        path: "/misbebes",
        redirect: "/misbebes/listado",
        name: "patients",
        component: () => import("../views/patients/index.vue"),
        meta: {
          title: "Mis pacientes",
        },
        children: [
          {
            path: "listado",
            name: "PatientsListPage",
            component: () => import("../views/patients/PatientsListPage.vue"),
            meta: {
              title: "Listado pacientes",
            },
          },
          {
            path: "agregar",
            name: "PatientAddPage",
            component: () => import("../views/patients/PatientAddPage.vue"),
            meta: {
              title: "Agregar paciente",
            },
          },
          {
            path: "editar/:id",
            name: "PatientEditPage",
            component: () => import("../views/patients/PatientEditPage.vue"),
            meta: {
              title: "Editar paciente",
            },
          },
          {
            path: "detalles/:id",
            name: "PatientDetailsPage",
            component: () => import("../views/patients/PatientDetailsPage.vue"),
            meta: {
              title: "Detalles paciente",
            },
          },
          {
            path: "notas",
            name: "PatientsNotesPage",
            component: () => import("../views/patients/PatientsNotesPage.vue"),
            meta: {
              title: "Notas paciente",
            },
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
            meta: {
              title: "Consultas médicas",
            },
          },
          {
            path: "agregar",
            name: "ConsultationAddPage",
            component: () =>
              import("../views/consultations/ConsultationAddPage.vue"),
            meta: {
              title: "Agregar consulta",
            },
          },
          {
            path: "editar/:id",
            name: "ConsultationEditPage",
            component: () =>
              import("../views/consultations/ConsultationEditPage.vue"),
            meta: {
              title: "Editar consulta",
            },
          },
          {
            path: "detalles/:id",
            name: "ConsultationDetailsPage",
            component: () =>
              import("../views/consultations/ConsultationDetailsPage.vue"),
            meta: {
              title: "Detalles consulta",
            },
          },
          {
            path: "periodicas",
            name: "RegularVisitPage",
            component: () =>
              import("../views/consultations/RegularVisitPage.vue"),
            meta: {
              title: "Visitas rutinarias",
            },
          },
          {
            path: "urgencias",
            name: "EmergencyPage",
            component: () => import("../views/consultations/EmergencyPage.vue"),
            meta: {
              title: "Emergencias",
            },
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
            meta: {
              title: "Diámetro craneal",
            },
          },
          {
            path: "altura",
            name: "HeightPage",
            component: () => import("../views/measurements/HeightPage.vue"),
            meta: {
              title: "Altura",
            },
          },
          {
            path: "peso",
            name: "WeightPage",
            component: () => import("../views/measurements/WeightPage.vue"),
            meta: {
              title: "Peso",
            },
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
  const appTitle = import.meta.env.VITE_APP_TITLE;
  document.title = to.meta.title ? `${to.meta.title} - ${appTitle}` : appTitle;
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
