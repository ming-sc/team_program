<script setup>
import Tabs from 'primevue/tabs';
import TabList from 'primevue/tablist';
import Tab from 'primevue/tab';
import TabPanels from 'primevue/tabpanels';
import TabPanel from 'primevue/tabpanel';
import {Toast} from "primevue";
</script>

<template>
  <div style="height: 100%;padding: 20px;display: flex;flex-direction: column; align-items: center">
    <div style="display: flex; flex-direction: row">
      <InputText v-model="keyword" style="width: 700px;" size="large"></InputText>
      <Button style="margin-left: 15px;" size="large" @click="onSearchClick">搜索</Button>
    </div>
    <Tabs @update:value="onTabChange" value="0" style="width: 100%;max-width: 800px" >
      <TabList>
        <Tab value="0" style="font-size: x-large;font-weight: bold">阅读练习</Tab>
        <Tab value="1" style="font-size: x-large;font-weight: bold">听力练习</Tab>
        <Tab value="2" style="font-size: x-large;font-weight: bold">文件</Tab>
      </TabList>
      <TabPanels>
        <TabPanel value="0">
          <div v-if="total > 0">
            <div
                class="exercise-card"
                v-for="record in result"
                :key="record.readingPracticeId"
                @click="goToReading(record.readingPracticeId)"
            >
              <!-- 阅读图标 -->
              <i class="pi pi-book icon"></i>
              <p>
                {{record.title}}
              </p>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                  <Tag severity="success" style="flex-shrink: 0;height: 30px">题数: {{record.exerciseCount}}</Tag>
                </div>
              </div>
            </div>
          </div>
          <div style="width: 100%;text-align: center;color: #999999;font-size: large;margin-top: 20px" v-else>
            暂无数据
          </div>
        </TabPanel>
        <TabPanel value="1">
          <div v-if="total > 0">
            <div
                class="exercise-card"
                v-for="record in result"
                :key="record.listeningPracticeId"
                @click="goToListening(record.listeningPracticeId)"
            >
              <!-- 听力图标 -->
              <i class="pi pi-volume-up icon"></i>
              <p>
                {{record.title}}
              </p>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                  <Tag severity="success" style="flex-shrink: 0;height: 30px">题数: {{record.exerciseCount}}</Tag>
                </div>
              </div>
            </div>
          </div>
          <div style="width: 100%;text-align: center;color: #999999;font-size: large;margin-top: 20px" v-else>
            暂无数据
          </div>
        </TabPanel>
        <TabPanel value="2">
          <div v-if="total > 0">
            <div
                class="exercise-card"
                v-for="record in result"
                :key="record.fileId"
                @click="goToFile(record.src)"
            >
              <!-- 文件图标 -->
              <i class="pi pi-file icon"></i>
              <p>
                {{record.name}}
              </p>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                </div>
              </div>
            </div>
          </div>
          <div style="width: 100%;text-align: center;color: #999999;font-size: large;margin-top: 20px" v-else>
            暂无数据
          </div>
        </TabPanel>
      </TabPanels>
    </Tabs>
    <Paginator
        v-if="total > pageSize"
        :rows="10"
        :totalRecords="total"
        :rowsPerPageOptions="[10, 20, 30]"
        style="padding: 0"
        :first="current * pageSize"
        @page="onPageChange"
        @update:rows="onPageSizeChange"
    >
    </Paginator>
  </div>
  <Toast />
</template>

<script>
import {getPractices as getReading} from "@/apis/reading";
import {getPractices as getListening} from "@/apis/listening";
import {search as getFile} from "@/apis/file";
import {BASE_URL} from "@/apis/request";

export default {
  name: 'Search',
  data() {
    return {
      keyword: "",
      current: 0,
      pageSize: 10,
      total: 0,
      result: [],
      type: '0',
    }
  },
  methods: {
    search(current, pageSize , keyword, type) {
      let searchFunction;
      switch (type) {
        case '0':
          searchFunction = getReading;
          break;
        case '1':
          searchFunction = getListening;
          break;
        case '2':
          searchFunction = getFile;
          break;
        default:
          return;
      }

      searchFunction(current + 1, pageSize, keyword)
        .then(response => {
          this.result = response.data.records;
          this.total = response.data.total;
        })
        .catch(error => {
          this.$toast.add({
            severity: 'error',
            summary: 'Error',
            detail: error.message,
            life: 3000
          });
        });
    },
    updatePage() {
      this.search(this.current, this.pageSize, this.keyword, this.type);
    },
    onTabChange(value) {
      this.type = value;
      this.updatePage();
    },
    onSearchClick() {
      if (this.keyword) {
        this.$router.push({name: 'Search', params: {keyword: this.keyword} });
      } else {
        this.$router.push({path: '/search'});
      }
      this.updatePage();
    },
    goToReading(readingPracticeId) {
      this.$router.push({ path: `/reading/${readingPracticeId}` });
    },
    goToListening(listeningPracticeId) {
      this.$router.push({ path: `/listening/${listeningPracticeId}` });
    },
    goToFile(src) {
      window.open(`${BASE_URL}${src}`);
    },
    onPageChange(event) {
      this.current = event.page;
      this.updatePage();
    },
    onPageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.current = 0;
    },
  },
  mounted() {
    this.keyword = this.$route.params.keyword ? this.$route.params.keyword : "";
    this.updatePage();
  },
}

</script>

<style scoped>
.exercise-card {
  margin-top: 15px;
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
</style>