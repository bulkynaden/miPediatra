<template>
  <transition :mode="transitionMode" :name="transitionName">
    <component :is="currentStepValue" />
  </transition>
  <button @click="previousStep">Anterior</button>
  <button @click="nextStep">Siguiente</button>
</template>

<script>
import Step1 from "./PatientsNotesPage.vue";
import Step3 from "./PatientsNotesPage.vue";
import Step2 from "./PatientDetailsPage.vue";

export default {
  components: {
    Step1,
    Step2,
    Step3,
  },
  data() {
    return {
      steps: [Step1, Step2, Step3],
      currentStep: 0,
      currentComponent: "Step1",
      formData: {},
      transitionName: "slide-fade",
      transitionMode: "out-in",
    };
  },
  computed: {
    currentStepValue() {
      return this.getCurrentStep();
    },
  },
  methods: {
    getCurrentStep() {
      return this.steps[this.currentStep];
    },
    nextStep() {
      this.transitionName = "slide-fade-left";
      this.transitionMode = "out-in";
      this.currentStep++;
    },
    previousStep() {
      this.transitionName = "slide-fade-right";
      this.transitionMode = "out-in";
      this.currentStep--;
    },
    submitForm() {
      // Envía los datos del formulario a través de una petición HTTP
      // y muestra un mensaje de éxito o de error según corresponda.
    },
  },
};
</script>

<style>
.slide-fade-enter-active,
.slide-fade-leave-active,
.slide-fade-left-enter-active,
.slide-fade-right-enter-active {
  transition: all 0.3s ease;
}

.slide-fade-enter,
.slide-fade-leave-to,
.slide-fade-left-enter {
  opacity: 0;
  transform: translateX(10px);
}

.slide-fade-leave-active,
.slide-fade-right-enter {
  opacity: 0;
  transform: translateX(-10px);
}

.slide-fade-left-enter-active {
  opacity: 0;
  transform: translateX(10px);
}
</style>
