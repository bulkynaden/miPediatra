<template>
  <div id="loading_wrap" class="loading_wrap">
    <div class="loader_logo">
      <img alt="logo" class="logo_image" src="/logo-color.png" />
    </div>
    <div class="loading"></div>
    <div class="loading_text">
      <h1
        class="text-4xl font-bold text-purple-500 animate__animated animate__heartBeat"
      >
        Cargando...
      </h1>
    </div>
  </div>
</template>

<script>
import "animate.css";

export default {
  name: "TheLoadingLogo",
};
</script>

<style scoped>
.loading_wrap {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.9);
  z-index: 10000;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.loader_logo {
  height: 120px;
  width: 120px;
  margin-bottom: 20px;
}

.loader_logo img {
  height: 100%;
  width: 100%;
}

.loading {
  border: 3px solid rgba(102, 51, 153, 0.45);
  width: 55px;
  height: 55px;
  border-radius: 50%;
  border-top-color: #a855f7;
  animation: loader 1s ease-in-out infinite;
  -webkit-animation: loader 1s ease-in-out infinite;
  margin-bottom: 20px;
}

.loading_text {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-align: center;
  animation: heartBeat 2s infinite;
}

@keyframes loader {
  to {
    -webkit-transform: rotate(360deg);
  }
}

@-webkit-keyframes loader {
  to {
    -webkit-transform: rotate(360deg);
  }
}

@keyframes wave {
  0% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
  100% {
    transform: translateY(0);
  }
}

@-webkit-keyframes wave {
  0% {
    -webkit-transform: translateY(0);
  }
  50% {
    -webkit-transform: translateY(-10px);
  }
  100% {
    -webkit-transform: translateY(0);
  }
}
</style>
