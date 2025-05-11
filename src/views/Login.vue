<script setup>
    import {Toast} from "primevue";
</script>

<template>
  <div class="login-container">
    <h1>登录</h1>
    <div class="from-container">
      <InputText style="width: 100%;max-width: 300px;margin-top: 10px;" v-model="username" placeholder="用户名" />
      <InputText style="width: 100%;max-width: 300px;margin-top: 5px;" v-model="password" placeholder="密码" type="password" />
      <Button style="width: 100%;max-width: 300px;margin-top: 5px;" @click="handleLogin">
        登录
      </Button>
    </div>
  </div>
  <Toast style="font-family: 微软雅黑,serif" />
</template>

<script>
import {login} from "@/apis/user";

export default {
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    handleLogin() {
      // 使用 apis/user.js 里的登录接口
      login(this.username, this.password)
          .then(data => {
            // 处理登录成功的情况
            console.log(data);
            this.$toast.add({
              severity: 'success',
              summary: '登录成功',
              detail: '欢迎回来！',
              life: 3000,
            });
            localStorage.setItem('token', data.data.token);
            localStorage.setItem('userInfo', JSON.stringify(data.data));
            this.$router.push({ path:"/" });
          })
          .catch(error => {
              // 处理登录失败的情况
              this.$toast.add({
                severity: 'error',
                summary: '登录失败',
                detail: error,
                life: 3000,
              });
          });
    },
  },
  mounted() {
    // 检查是否已经登录
    const token = localStorage.getItem('token');
    if (token) {
      this.$router.push({ path: '/'});
    }
  },
};
</script>

<style scoped>
.login-container {
  height: 100%;
  width: 100%;
}

.from-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
</style>
