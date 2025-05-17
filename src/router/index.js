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
import PracticeRecords from '@/views/ParcticeRecords.vue';
import ReadingRecord from "@/views/ReadingRecord.vue";
import ListeningRecord from "@/views/ListeningRecord.vue";
import Search from "@/views/Search.vue";
import Manager from "@/views/Manager.vue";
import AddReading from "@/views/AddReading.vue";
import AddListening from "@/views/AddListening.vue";
import AddVocabulary from "@/views/AddVocabulary.vue";
import {getUserInfo} from "@/utils/userInfo";

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
  { path: '/manager', component: Manager, meta: {requiresAdmin: true} },
  { path: '/manager/addReading', component: AddReading, meta: {requiresAdmin: true} },
  { path: '/manager/addListening', component: AddListening, meta: {requiresAdmin: true} },
  { path: '/manager/addVocabulary', component: AddVocabulary, meta: {requiresAdmin: true} },
  { path: '/statistics', component: Statistics, meta: { requiresAuth: true } },
  { path: '/admin/login', component: AdminLogin },
  { path: '/admin/upload', component: AdminUpload, meta: { requiresAdmin: true } },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAdmin) {
    const isAdmin = getUserInfo().role === 1 ; // 假設用戶信息中包含 isAdmin 字段
    if (!isAdmin) {
      next({ path: '/' });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
