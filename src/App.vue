<template>
  <div id="app">
<!--    <NavBar />-->
    <Header></Header>
    <transition name="fade" mode="out-in">
      <div style="padding-top: 65px;height: 100%;box-sizing: border-box">
        <router-view />
      </div>
    </transition>
  </div>
</template>

<script>
// import NavBar from './components/NavBar.vue'
// import ThemeToggle from './components/ThemeToggle.vue'

import Header from "@/components/Header.vue";

export default {
  name: 'App',
  components: {
    Header
    // NavBar,
    // ThemeToggle
  }
}
</script>

<style>
/* 淺色模式 */
body {
  background-color: white;
  color: #2c3e50;
  height: 100vh;
  box-sizing: border-box;
  margin: unset;
  overflow: scroll;
}

/* 深色模式 */
body.dark-mode {
  background-color: #1a1a1a;
  color: #f0f0f0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  box-sizing: border-box;
  height: 100%;
  overflow: scroll;
}

h1 {
  margin-bottom: 20px;
}

form {
  max-width: 300px;
  margin: 0 auto;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 5px;
}

nav {
  padding: 10px;
  background-color: #f0f0f0;
  text-align: center;
}

router-link {
  margin: 0 10px;
  text-decoration: none;
  color: #333;
}

router-link:hover {
  color: #007bff;
}

/* 添加過渡動畫 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
