<template>
  <div class="form-container">
    <div
      :style="{ transform: 'translateX(' + -(currentPage - 1) * 100 + '%)' }"
      class="form-pages"
    >
      <div :class="{ active: currentPage === 1 }" class="form-page">
        <h2>Datos personales</h2>
        <input v-model="name" placeholder="Nombre" type="text" />
        <input v-model="surname" placeholder="Apellidos" type="text" />
        <button @click="nextPage">Siguiente</button>
      </div>
      <div :class="{ active: currentPage === 2 }" class="form-page">
        <h2>Vacunas</h2>
        <input v-model="vaccine1" placeholder="Vacuna 1" type="text" />
        <input v-model="vaccine2" placeholder="Vacuna 2" type="text" />
        <button @click="prevPage">Anterior</button>
        <button @click="nextPage">Siguiente</button>
      </div>
      <div :class="{ active: currentPage === 3 }" class="form-page">
        <h2>Mediciones</h2>
        <input v-model="measurement1" placeholder="Medición 1" type="text" />
        <input v-model="measurement2" placeholder="Medición 2" type="text" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      name: "",
      surname: "",
      vaccine1: "",
      vaccine2: "",
      measurement1: "",
      measurement2: "",
    };
  },
  methods: {
    nextPage() {
      this.currentPage++;
    },
    prevPage() {
      this.currentPage--;
    },
    submitForm() {
      // Enviar los datos del paciente al servidor aquí
    },
  },
};
</script>

<style>
.form-container {
  position: relative;
  overflow: hidden;
}

.form-pages {
  display: flex;
  transition: transform 0.3s ease-in-out;
}

.form-page {
  flex: 1 0 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  opacity: 0;
  transition: opacity 0.3s ease-in-out;
}

.form-page.active {
  opacity: 1;
}
</style>
