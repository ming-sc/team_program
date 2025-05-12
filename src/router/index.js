import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import Vocabulary from '../views/Vocabulary.vue';
import Listening from '../views/Listening.vue';
import Reading from '../views/Reading.vue';
import Statistics from '../views/Statistics.vue';
import AdminUpload from '@/views/AdminUpload.vue';
import AdminLogin from '@/views/AdminLogin.vue';
import store from '../store';
import PracticeRecords from '@/views/ParcticeRecords.vue';
import ReadingRecord from "@/views/ReadingRecord.vue";
import ListeningRecord from "@/views/ListeningRecord.vue";
import Search from "@/views/Search.vue";

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/vocabulary', component: Vocabulary},
  { path: '/listening/:id', component: Listening},
  { path: '/reading/:id', component: Reading },
  { path: '/practiceRecords', component: PracticeRecords },
  { path: '/readingRecord/:id', component: ReadingRecord },
  { path: '/listeningRecord/:id', component: ListeningRecord },
  { path: '/search/:keyword', component: Search, name: 'Search' },
  { path: '/search', component: Search },
  { path: '/statistics', component: Statistics, meta: { requiresAuth: true } },
  { path: '/admin/login', component: AdminLogin },
  { path: '/admin/upload', component: AdminUpload, meta: { requiresAdmin: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = store.state.isAuthenticated;
  const isAdmin = store.state.user && store.state.user.isAdmin; // 假設用戶信息中包含 isAdmin 字段

  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login'); // 如果未登錄且路由需要登錄，重定向到登錄頁面
  } else if (to.meta.requiresAdmin && !isAdmin) {
    next('/'); // 如果沒有管理員權限，重定向到首頁
  } else {
    next();
  }
});

export default router;
