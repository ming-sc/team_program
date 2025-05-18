import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/views/Home.vue';
import Login from '@/views/Login.vue';
import Register from '@/views/Register.vue';
import Vocabulary from '@/views/Vocabulary.vue';
import Listening from '@/views/Listening.vue';
import Reading from '@/views/Reading.vue';
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
  { path: '/vocabulary', component: Vocabulary },
  { path: '/listening/', component: Listening },
  { path: '/listening/:id', component: Listening },
  { path: '/reading', component: Reading },
  { path: '/reading/:id', component: Reading },
  { path: '/practiceRecords', component: PracticeRecords },
  { path: '/readingRecord/', component: ReadingRecord },
  { path: '/readingRecord/:id', component: ReadingRecord },
  { path: '/listeningRecord/', component: ListeningRecord },
  { path: '/listeningRecord/:id', component: ListeningRecord },
  { path: '/search/:keyword', component: Search, name: 'Search' },
  { path: '/search', component: Search },
  { path: '/manager', component: Manager, meta: {requiresAdmin: true} },
  { path: '/manager/addReading', component: AddReading, meta: {requiresAdmin: true} },
  { path: '/manager/addListening', component: AddListening, meta: {requiresAdmin: true} },
  { path: '/manager/addVocabulary', component: AddVocabulary, meta: {requiresAdmin: true} },
];

const router = createRouter({
  history: createWebHistory("/cetBoot/"),
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
