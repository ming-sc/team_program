<script setup>
import {fromDateTimeArrayToString} from "@/utils/time";
import TabPanels from "primevue/tabpanels";
import Tabs from "primevue/tabs";
import TabList from "primevue/tablist";
import Tab from "primevue/tab";
import TabPanel from "primevue/tabpanel";
</script>

<template>
  <div style="width: 100%; display: flex; justify-content: center">
    <Tabs @update:value="onTabChange" style="width: 100%; max-width: 1300px" value="0">
      <TabList>
        <Tab value="0" style="font-size: large;font-weight: bold">阅读练习记录</Tab>
        <Tab value="1" style="font-size: large;font-weight: bold">听力练习记录</Tab>
        <Tab value="2" style="font-size: large;font-weight: bold">词汇练习记录</Tab>
      </TabList>
      <TabPanels>
        <TabPanel value="0">
          <div v-if="total > 0">
            <div :key="readingRecord.readingRecordId"
                 v-for="readingRecord in records"
                 class="exercise-card"
                 @click="goToReadingRecord(readingRecord.readingRecordId)"
            >
              <i class="pi pi-book icon" />
              <div style="position: relative">
                <p>{{readingRecord.title}}</p>
                <Tag style="position: absolute; right: 0;top: 0;">
                  {{fromDateTimeArrayToString(readingRecord.recordTime)}}
                </Tag>
              </div>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                  <Tag :severity="readingRecord.score > 50 ? 'success' : 'danger' "
                       style="flex-shrink: 0;height: 30px"
                  >
                    得分率: {{readingRecord.score}}%
                  </Tag>
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
            <div :key="listeningRecord.listeningRecordId"
                 v-for="listeningRecord in records"
                 class="exercise-card"
                 @click="goToListeningRecord(listeningRecord.listeningRecordId)"
            >
              <i class="pi pi-volume-up icon" />
              <div style="position: relative">
                <p>{{listeningRecord.title}}</p>
                <Tag style="position: absolute; right: 0;top: 0;">
                  {{fromDateTimeArrayToString(listeningRecord.recordTime)}}
                </Tag>
              </div>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                  <Tag :severity="listeningRecord.score > 50 ? 'success' : 'danger' "
                       style="flex-shrink: 0;height: 30px"
                  >
                    得分率: {{listeningRecord.score}}%
                  </Tag>
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
            <div :key="vocabularyRecord.vocabularyRecordId"
                 v-for="vocabularyRecord in records"
                 class="exercise-card"
            >
              <i class="pi pi-file-word icon" />
              <div style="position: relative">
                <p style="font-size: xx-large">{{vocabularyRecord.word}}</p>
                <p style="font-size: large;margin-top: 10px">{{vocabularyRecord.meaning}}</p>
                <Tag style="position: absolute; right: 0;top: 0;">
                  {{fromDateTimeArrayToString(vocabularyRecord.recordTime)}}
                </Tag>
              </div>
              <div style="height: 100%; display: flex; flex-direction: row">
                <div style="width: 100%"></div>
                <div style="display: flex; flex-direction: column; flex-shrink: 0">
                  <div style="height: 100%"></div>
                  <Tag :severity="vocabularyRecord.isCorrect ? 'success' : 'danger' "
                       style="flex-shrink: 0;height: 30px"
                  >
                    {{vocabularyRecord.isCorrect ? '正确' : '错误'}}
                  </Tag>
                </div>
              </div>
            </div>
          </div>
          <div style="width: 100%;text-align: center;color: #999999;font-size: large;margin-top: 20px" v-else>
            暂无数据
          </div>
        </TabPanel>
      </TabPanels>
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
    </Tabs>
  </div>

</template>

<script>
import {getRecords as getReading} from "@/apis/reading";
import {getRecords as getListening} from "@/apis/listening";
import {getRecords as getVocabulary} from "@/apis/vocabulary";

export default {
  name: 'PracticeRecords',
  data() {
    return {
      records: [],
      current: 0,
      pageSize: 10,
      pages: 1,
      total: 0,
      currentRecordsType: '0',
    };
  },
  methods: {
    goToReadingRecord(readingRecordId){
      this.$router.push({ path: `/readingRecord/${readingRecordId}` });
    },
    getRecords(current, pageSize, recordType) {
      let getRecordFunction;
      switch (recordType) {
        case '0':
          getRecordFunction = getReading;
          break;
        case '1':
          getRecordFunction = getListening;
          break;
        case '2':
          getRecordFunction = getVocabulary;
          break;
        default:
          return;
      }
      getRecordFunction(current, pageSize)
          .then(response => {
            this.records = response.data.records;
            this.pages = response.data.pages;
            this.total = response.data.total;
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '获取练习记录失败',
              detail: error,
              life: 3000,
            });
          });
    },
    goToListeningRecord(listeningRecordId){
      this.$router.push({ path: `/listeningRecord/${listeningRecordId}` });
    },
    onTabChange(value) {
      this.current = 0;
      this.currentRecordsType = value;
      this.updateRecord();
    },
    updateRecord() {
      console.log(this.current);
      this.getRecords(this.current + 1, this.pageSize, this.currentRecordsType);
    },
    onPageChange(event) {
      this.current = event.page;
      this.updateRecord();
    },
    onPageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.current = 0;
    },
  },
  mounted() {
    this.updateRecord();
  },
}
</script>

<style scoped>
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
</style>