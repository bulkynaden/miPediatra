<template>
    <div class="container mx-auto">
        <Breadcrumbs parentTitle="Mis pacientes" subParentTitle="Listado pacientes"/>
        <router-link  :to="{ name: 'PatientAddPage' }">
            <BaseBtn text="Añadir paciente"></BaseBtn>
        </router-link>
        <h1>Listado pacientes</h1>
    </div>
</template>

<script>
export default {
    name: "PatientsListPage",
}
</script>

<style scoped>

</style>