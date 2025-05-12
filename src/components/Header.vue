<script setup>

</script>

<template>
  <header class="header">
    <div class="header-title" @click="goToHome">CET-Boot</div>
    <div style="width: 100%"></div>
    <Button severity="secondary"
            class="header-user"
            @click="$refs.menu.toggle($event)"
            aria-haspopup="true"
            aria-controls="overlay_menu"
    >
      {{userinfo.userName}}
      <i class="pi pi-angle-down" />
    </Button>
    <Menu ref="menu" id="overlay_menu" :model="userinfo.token ? items : itemsNoLogin" :popup="true" />
  </header>
</template>

<script>
import {getUserInfo} from "@/utils/userInfo";
import {logout} from "@/apis/user";

export default {
  name: 'Header',
  data() {
    return {
      userinfo: {},
      items: [
        {
          label: '答题记录',
          icon: 'pi pi-fw pi-book',
          command: () => {
            this.$router.push({ path: '/practiceRecords' });
          }
        },
        {
          label: '退出登录',
          icon: 'pi pi-fw pi-sign-out',
          command: () => {
            logout()
                .then(() => {
                  this.$toast.add({
                    severity: 'success',
                    summary: '退出登录成功',
                    life: 3000,
                  });
                  localStorage.removeItem('token');
                  localStorage.removeItem('userInfo');
                  this.$router.push({ path: '/login' }).then(() => {
                    window.location.reload();
                  });
                })
                .catch(error => {
                  this.$toast.add({
                    severity: 'error',
                    summary: '退出登录失败',
                    detail: error,
                    life: 3000,
                  });
                });
          }
        }
      ],
      itemsNoLogin: [
        {
          label: '登录',
          icon: 'pi pi-fw pi-user',
          command: () => {
            this.$router.push({ path: '/login' }).then(() => {
              window.location.reload();
            });
          }
        }
      ]
    }
  },
  methods: {
    goToHome() {
      this.$router.push({ path: '/' });
    }
  },
  mounted() {
    const userInfo = getUserInfo();
    if (userInfo) {
      this.userinfo = userInfo;
    } else {
      this.userinfo.userName = "未登录";
    }
  }
}
</script>

<style scoped>
.header-title {
  align-content: center;
  font-size: xx-large;
  font-weight: bold;
  margin: 15px;
  color: var(--p-text-color);
  flex-shrink: 0;
  user-select: none;
  cursor: pointer;
}

.header {
  height: 65px;
  display: flex;
  flex-direction: row;
  background-color: var(--p-content-background);
  align-items: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  z-index: 100;
  width: 100%;
}

.header-user {
  align-content: center;
  color: var(--p-text-color);
  height: fit-content;
  flex-shrink: 0;
  margin-right: 30px;
}
</style>