<template>
  <button :class="this.class" :type="buttonType" class="e-btn button" isPrimary="true" @click="emitEvent">{{
      text
    }}
  </button>
  <slot></slot>
</template>

<script>

export default {
  emits: ['submitForm', 'click'],
  props: {
    text: String,
    class: {
      type: String,
      default: "e-outline"
    },
    type: String
  },
  name: "BaseBtn",
  data() {
    return {
      buttonType: this.type
    }
  },
  methods: {
    emitEvent() {
      if (this.type === "submit") {
        this.$emit('submitForm');
      } else {
        this.$emit('click');
      }
    }
  }
}
</script>

<style scoped>
.button {
  margin: 5px;
  text-transform: none;
}

button:hover {

}
</style>
