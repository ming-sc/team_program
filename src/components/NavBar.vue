<template>
  <nav class="navbar">
    <router-link to="/" class="nav-button">首頁</router-link>
    <router-link to="/vocabulary" class="nav-button">詞彙記憶</router-link>
    <router-link to="/listening" class="nav-button">聽力訓練</router-link>
    <router-link to="/reading" class="nav-button">閱讀訓練</router-link>
    <router-link v-if="isAdmin" to="/admin/upload" class="nav-button">管理員上傳</router-link>
    <router-link v-if="!isAuthenticated" to="/login" class="nav-button">登錄</router-link>
    <router-link v-if="!isAuthenticated" to="/register" class="nav-button">註冊</router-link>
    <router-link v-if="!isAdmin" to="/admin/login" class="nav-button">管理員登錄</router-link>
    <button v-if="isAuthenticated" @click="handleLogout" class="nav-button">登出</button>
  </nav>
</template>

<script>
import store from '../store';

export default {
  name: 'NavBar',
  computed: {
    isAuthenticated() {
      return store.state.isAuthenticated;
    },
    isAdmin() {
      return store.state.user && store.state.user.isAdmin; // 假設用戶信息中包含 isAdmin 字段
    },
    username() {
      return store.state.username;
    },
  },
  methods: {
    handleLogout() {
      store.mutations.logout();
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
.navbar {
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 10px;
}

.nav-button {
  margin: 0 10px;
  padding: 10px 20px;
  text-align: center;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.nav-button:hover {
  background-color: #0056b3;
}
</style>
