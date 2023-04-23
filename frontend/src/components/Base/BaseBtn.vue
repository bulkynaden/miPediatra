<template>
  <ejs-button
    :class="this.class"
    :type="buttonType"
    class="e-btn button"
    @click="emitEvent"
  >
    {{ text }}
  </ejs-button>

  <slot></slot>
</template>

<script>
import { ButtonComponent } from "@syncfusion/ej2-vue-buttons";

export default {
  emits: ["submitForm", "click"],
  components: { "ejs-button": ButtonComponent },
  props: {
    text: String,
    class: {
      type: String,
      default: "e-btn e-outline",
    },
    type: String,
  },
  name: "BaseBtn",
  data() {
    return {
      buttonType: this.type,
    };
  },
  methods: {
    emitEvent() {
      if (this.type === "submit") {
        this.$emit("submitForm");
      } else {
        this.$emit("click");
      }
    },
  },
};
</script>

<style scoped>
.button {
  margin: 5px;
  text-transform: none;
}

button:hover {
}
</style>
