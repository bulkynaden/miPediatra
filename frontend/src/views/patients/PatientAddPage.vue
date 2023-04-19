<template>
  <div class="container text-center">
    <Breadcrumbs parentTitle="Mis pacientes" subParentTitle="Añadir paciente" />

    <base-card class="max-w-3xl mx-auto w-auto">
      <h4>Añadir paciente</h4>
      <form
        id="formAddChild"
        class="grid max-w-3xl w-auto mx-auto"
        method="post"
        @submit.prevent="submitForm"
      >
        <div class="grid sm:grid-cols-1 md:grid-cols-3 gap-4">
          <BaseFormInput
            v-model="name"
            :is-valid="isValidName"
            error-message="El nombre es obligatorio"
            text="Nombre"
            @update:modelValue="validateName"
          />
          <div class="md:col-span-2">
            <BaseFormInput
              v-model="lastName"
              :is-valid="isValidLastName"
              error-message="Los apellidos son obligatorios"
              text="Apellidos"
              @update:modelValue="validateLastName"
            />
          </div>
          <div class="md:col-span-3">
            <BaseFormInput
              v-model="description"
              :is-valid="isValidDescription"
              error-message="La descripción debe tener más de 10 caracteres"
              text="Description"
              @update:modelValue="validateDescription"
            />
          </div>
        </div>
      </form>

      <div class="submitBtn">
        <BaseBtn
          text="Aceptar"
          type="submit"
          @submitForm="submitForm"
        ></BaseBtn>
      </div>
    </base-card>
  </div>
</template>

<script>
import BaseBtn from "@/components/Base/BaseBtn.vue";
import BaseCard from "@/components/Base/BaseCard.vue";
import BaseFormInput from "@/components/Base/BaseFormInput.vue";
import ChildrenService from "@/services/ChildrenService";

export default {
  name: "PatientAddPage",
  components: { BaseFormInput, BaseCard, BaseBtn },
  data: function () {
    return {
      name: "",
      isValidName: true,
      lastName: "",
      isValidLastName: true,
      description: "",
      isValidDescription: true,
    };
  },
  methods: {
    validateName() {
      if (!this.name) {
        this.isValidName = false;
      } else {
        this.isValidName = true;
      }
    },
    validateLastName() {
      if (this.lastName === "" || this.lastName === null) {
        this.isValidLastName = false;
      } else {
        this.isValidLastName = true;
      }
    },
    validateDescription() {
      if (this.description === "" || this.description.length < 10) {
        this.isValidDescription = false;
      } else {
        this.isValidDescription = true;
      }
    },
    submitForm() {
      this.validateName();
      this.validateLastName();
      this.validateDescription();
      if (this.isValidName && this.isValidLastName && this.isValidDescription) {
        const child = {
          nombre: this.name,
          apellidos: this.lastName,
          fechaNacimiento: new Date("09/11/2021"),
          descripcion: this.description,
        };
        ChildrenService.create(child)
          .then(() => {
            /* const Toast = this.$swal.mixin({
                                                  toast: true,
                                                  position: 'top-end',
                                                  showConfirmButton: false,
                                                  timer: 3000,
                                                  timerProgressBar: true,
                                                })
                        
                                                Toast.fire({
                                                  icon: 'success',
                                                  title: 'Los datos se han registrado correctamente'
                                                })*/

            this.$swal.fire({
              icon: "success",
              title: "Los datos se han registrado correctamente",
              timer: 1500,
            });
            this.$router.push("/misbebes/listado");
          })
          .catch(() => {
            this.$swal.fire({
              icon: "error",
              title: "Ha ocurrido un error inesperado",
              timer: 1500,
            });
          });
      } else {
        this.$swal.fire({
          icon: "error",
          title: "Los datos introducidos no son correctos",
          timer: 1500,
        });
      }
    },
  },
};
</script>

<style scoped></style>
