<script setup>
import Tabs from 'primevue/tabs';
import TabList from 'primevue/tablist';
import Tab from 'primevue/tab';
import TabPanels from 'primevue/tabpanels';
import TabPanel from 'primevue/tabpanel';
import {Toast} from "primevue";
</script>
<template>

  <div class="pic">
    <div class="pic-container">
      <div class="pic-text">
        “未来独立于过去，只基于当下”
      </div>
      <div class="pic-text-en">
        The future is independent of the past given the present.
      </div>
    </div>
  </div>
  <div class="page-container">
    <div class="page">
      <Tabs value="0">
        <TabList style="position: relative">
          <div class="view-more" @click="goToSearch">
            <p>查看更多</p>
            <i class="pi pi-angle-right" />
          </div>
          <Tab value="0" style="font-size: x-large;font-weight: bold">阅读练习</Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="0">
            <div class="exercise-container">
              <Carousel
                  content-class="exercise-content"
                  container-class="container-class"
                  style="width: 100%"
                  :value="reading"
                  :numVisible="3"
                  :numScroll="3"
              >
                <template #item="slotProps">
                  <div @click="goToReading(slotProps.data.readingPracticeId)" class="exercise-card">
                    <!-- 阅读图标 -->
                    <i class="pi pi-book icon"></i>
                    <p>
                      {{slotProps.data.title}}
                    </p>
                    <div style="height: 100%; display: flex; flex-direction: row">
                      <div style="width: 100%"></div>
                      <div style="display: flex; flex-direction: column; flex-shrink: 0">
                        <div style="height: 100%"></div>
                        <Tag severity="success" style="flex-shrink: 0;height: 30px">题数: {{slotProps.data.exerciseCount}}</Tag>
                      </div>
                    </div>
                  </div>
                </template>
              </Carousel>
            </div>
          </TabPanel>
        </TabPanels>
        <TabList>
          <div class="view-more" @click="goToSearch">
            <p>查看更多</p>
            <i class="pi pi-angle-right" />
          </div>
          <Tab value="0" style="font-size: x-large;font-weight: bold">听力练习</Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="0">
            <div class="exercise-container">
              <Carousel
                  content-class="exercise-content"
                  container-class="container-class"
                  style="width: 100%"
                  :value="listening"
                  :numVisible="3"
                  :numScroll="3"
              >
                <template #item="slotProps">
                  <div @click="goToListening(slotProps.data.listeningPracticeId)" class="exercise-card">
                    <!-- 听力图标 -->
                    <i class="pi pi-volume-up icon"></i>
                    <p>
                      {{slotProps.data.title}}
                    </p>
                    <div style="height: 100%; display: flex; flex-direction: row">
                      <div style="width: 100%"></div>
                      <div style="display: flex; flex-direction: column; flex-shrink: 0">
                        <div style="height: 100%"></div>
                        <Tag severity="success" style="flex-shrink: 0;height: 30px">题数: {{slotProps.data.exerciseCount}}</Tag>
                      </div>
                    </div>
                  </div>
                </template>
              </Carousel>
            </div>
          </TabPanel>
        </TabPanels>
        <TabList style="position: relative">
          <div class="view-more" @click="goToSearch">
            <p>查看更多</p>
            <i class="pi pi-angle-right" />
          </div>
          <Tab value="0" style="font-size: x-large;font-weight: bold">学习资料</Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="0">
            <div class="exercise-container">
              <Carousel
                  content-class="exercise-content"
                  container-class="container-class"
                  style="width: 100%"
                  :value="files"
                  :numVisible="3"
                  :numScroll="3"
              >
                <template #item="slotProps">
                  <div @click="goToFile(slotProps.data.src)" class="exercise-card">
                    <!-- 阅读图标 -->
                    <i class="pi pi-file icon"></i>
                    <p>
                      {{slotProps.data.name}}
                    </p>
                  </div>
                </template>
              </Carousel>
            </div>
          </TabPanel>
        </TabPanels>
        <TabList>
          <Tab value="0" style="font-size: x-large;font-weight: bold">词汇练习</Tab>
        </TabList>
        <TabPanels>
          <TabPanel @click="goToVocabulary" value="0">
            <div class="exercise-card">
              <!-- 单词图标 -->
              <i class="pi pi-file-word icon"></i>
              <p>
                词汇练习
              </p>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                  <Tag severity="success" style="flex-shrink: 0;height: 30px">题数: ∞</Tag>
                </div>
              </div>
            </div>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>

  </div>
  <Toast/>
</template>

<script>
import {getPractices as getReadingPractices} from "@/apis/reading";
import {getPractices as getListeningPractices} from "@/apis/listening";
import {search} from "@/apis/file"
import {BASE_URL} from "@/apis/request";

export default {
  name: 'Home',
  data() {
    return {
      reading: [
      ],
      listening: [
      ],
      files: [
      ],
    }
  },
  methods: {
    getReading() {
      getReadingPractices(1, 10)
          .then(response => {
            this.reading = response.data.records;
          })
          .catch(e => {
            this.$toast.add({
              severity: 'error',
              summary: '获取阅读练习失败',
              detail: e,
              life: 3000,
            });
          });
    },
    goToReading(id) {
      this.$router.push({ path: `/reading/${id}` });
    },
    getListening() {
      getListeningPractices(1, 10)
          .then(response => {
            this.listening = response.data.records;
          })
          .catch(e => {
            this.$toast.add({
              severity: 'error',
              summary: '获取听力练习失败',
              detail: e,
              life: 3000,
            });
          });
    },
    goToListening(id) {
      this.$router.push({ path: `/listening/${id}` });
    },
    goToVocabulary() {
      this.$router.push({ path: '/vocabulary' });
    },
    getFiles() {
      search(1, 10)
          .then(response => {
            this.files = response.data.records;
          })
          .catch(e => {
            this.$toast.add({
              severity: 'error',
              summary: '获取文件失败',
              detail: e,
              life: 3000,
            });
          });
    },
    goToFile(src) {
      window.open(`${BASE_URL}${src}`);
    },
    goToSearch() {
      this.$router.push({ path: '/search/' });
    },
  },
  mounted() {
    this.getReading();
    this.getListening();
    this.getFiles();
  }
};
</script>

<style scoped>
p {
  max-width: 600px;
  margin: 20px auto;
  text-align: left;
  line-height: 1.6;
}
router-link {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}
router-link:hover {
  background-color: #0056b3;
}

.pic {
  background-image: url("@/assets/fuko.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  height: 600px;
}

.pic-text {
  text-align: center;
  align-content: center;
  color: #ffffff;
  font-family: 宋体,serif;
  font-size: 40px;
  letter-spacing: 18px;
  font-weight: 600;
  text-shadow: 0 0 5px #000;
}

.pic-container {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  background-color: rgba(0, 0, 0, 0.5);
  user-select: none;
}

.pic-text-en {
  text-align: center;
  align-content: center;
  color: #ffffff;
  font-family: "Baskerville Old Face",serif;
  font-size: 24px;
  letter-spacing: 5px;
  font-weight: 1000;
  margin-top: 20px;
  font-style: italic;
  text-shadow: 0 0 5px #000;
}

.page-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.page {
  width: 100%;
  margin: 30px;
  max-width: 1400px;
}

.exercise-container {
  width: 100%;
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
  flex-direction:column;
  padding: 20px;
  position: relative;
  overflow: hidden;
}
.exercise-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s;
  cursor: pointer;
}
.exercise-card p {
  margin: 0;
  font-size: x-large;
  cursor: pointer;
}
.exercise-card p:hover {
  color: var(--p-primary-color);
  transition: color 0.3s;
}

.icon {
  font-size: 50px;
  color: var(--p-primary-color);
  opacity: 0.1;
  position: absolute;
  transform: translate(40px, 50px) rotate(-30deg) scale(4);
}

.view-more {
  position: absolute;
  top: 0;
  height: 100%;
  align-items: center;
  right: 0px;
  display: flex;
  flex-direction: row;
  user-select: none;
  cursor: pointer;
}

.view-more:hover {
  color: var(--p-primary-color);
  transition: color 0.3s;
}
</style>
