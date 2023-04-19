<template>
    <div class="h-screen w-screen flex justify-center items-center">
        <div class="app-error text-center">
            <svg class="text-danger justify-center" fill="currentColor" viewBox="0 0 20 20"
                 xmlns="http://www.w3.org/2000/svg">
                <path clip-rule="evenodd"
                      d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7 4a1 1 0 11-2 0 1 1 0 012 0zm-1-9a1 1 0 00-1 1v4a1 1 0 102 0V6a1 1 0 00-1-1z"
                      fill-rule="evenodd"/>
            </svg>
            <h1 class="text-8xl font-bold">404</h1>
            <p class="text-2xl font-bold">Página no encontrada</p>
            <div class="flex justify-center mt-10">
                <router-link :to="{ name: 'Home' }">
                    <BaseBtn
                            text="Volver al inicio"/>
                </router-link>
                <router-link :to="{ name: 'Home' }">
                    <!-- TODO: INFORMAR DE UN PROBLEMA-->
                    <BaseBtn
                            class="e-danger"
                            text="Informar de un problema"/>
                </router-link>
            </div>
        </div>
    </div>
</template>
<script setup>
</script>