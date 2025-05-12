<script setup>
import {Toast} from "primevue";
</script>

<template>
  <div class="login-container">
    <div class="exercise-card" style="width: 100%;max-width: 800px; height: 400px">
      <div style="flex: 0.5; display: flex; justify-content: center; align-items: center" >
        <i class="pi pi-user-plus" style="font-size: 100px" />
      </div>
      <Divider layout="vertical" />
      <div style="flex: 1; display: flex; justify-content: center; align-items: center;flex-direction: column">
        <div style="width: 100%;display: flex;flex-direction: row-reverse">
          <div class="view-more" @click="this.$router.push({ path: '/login/' })">
            <p>返回登录</p>
            <i class="pi pi-angle-right" />
          </div>
        </div>
        <InputGroup style="width: 100%;max-width: 450px;">
          <InputGroupAddon>
            <i class="pi pi-user"></i>
          </InputGroupAddon>
          <InputText v-model="username" size="large" placeholder="用户名" />
        </InputGroup>
        <InputGroup style="width: 100%;max-width: 450px;margin-top: 20px">
          <InputGroupAddon>
            <i class="pi pi-asterisk"></i>
          </InputGroupAddon>
          <InputText v-model="password" size="large" type="password" placeholder="密码" />
        </InputGroup>
        <InputGroup style="width: 100%;max-width: 450px;margin-top: 20px">
          <InputGroupAddon>
            <i class="pi pi-verified"></i>
          </InputGroupAddon>
          <InputText v-model="confirmPassword" size="large" type="password" placeholder="确认密码" />
        </InputGroup>
        <Button style="width: 100%;max-width: 450px;margin-top: 20px;height: 40px" @click="handlerRegister">
          注册
        </Button>
      </div>
    </div>
  </div>
  <Toast style="font-family: 微软雅黑,serif" />
</template>

<script>
import {register} from "@/apis/user";

export default {
  name: 'Register',
  data() {
    return {
      username: '',
      password: '',
      confirmPassword: '',
    };
  },
  methods: {
    handlerRegister() {
      if (this.password !== this.confirmPassword) {
        this.$toast.add({
          severity: 'warn',
          summary: '两次输入的密码不一致'
        });
        return;
      }
      // 使用 apis/user.js 里的注册接口
      register({userName: this.username, password: this.password})
          .then(data => {
            // 处理注册成功的情况
            this.$toast.add({
              severity: 'success',
              summary: '注册成功',
              detail: data.message,
              life: 3000,
            });
            this.$router.push({ path: '/login' });
          })
          .catch(error => {
            // 处理注册失败的情况
            this.$toast.add({
              severity: 'error',
              summary: '注册失败',
              detail: error,
              life: 3000,
            });
          });
    }
  },
  mounted() {
    // 检查是否已经登录
    const token = localStorage.getItem('token');
    if (token) {
      this.$router.push({ path: '/'});
    }
  }
};
</script>

<style scoped>
.login-container {
  height: 100%;
  width: 100%;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.exercise-card {
  margin: 15px;
  height: 150px;
  background:var(--p-content-background);
  color:var(--p-content-color);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  border: 1px solid var(--p-surface-200);
  display:flex;
  flex-direction:row;
  padding: 20px;
  position: relative;
  overflow: hidden;
}

.view-more {
  align-items: center;
  display: flex;
  flex-direction: row;
  user-select: none;
  cursor: pointer;
  padding-bottom: 15px;
  padding-right: 15px;
}
.view-more p {
  margin: 0;
}
</style>
