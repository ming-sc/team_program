<script setup>

import Tabs from "primevue/tabs";
import TabPanels from "primevue/tabpanels";
import TabList from "primevue/tablist";
import Tab from "primevue/tab";
import TabPanel from "primevue/tabpanel";
import {Toast} from "primevue";
</script>

<template>
  <div style="width: 100%;height: 100%;display: flex; justify-content: center;padding: 20px" v-if="title">
    <Splitter style="width: 100%;height: 100%;">
      <SplitterPanel style="overflow: scroll;padding: 20px;height: 100%">
        <Tabs value="0" style="width: 100%;max-width: 1300px" >
          <TabList>
            <Tab value="0" style="font-size: x-large;font-weight: bold">{{title}}</Tab>
          </TabList>
          <TabPanels>
            <TabPanel active value="0">
              <p style="white-space: pre-wrap; font-size: large;line-height: 25px">
                {{content}}
              </p>
            </TabPanel>
          </TabPanels>
        </Tabs>
      </SplitterPanel>
      <SplitterPanel style="overflow: scroll;padding: 20px;">
        <div style="display: flex;flex-direction: row;">
          <div style="width: 100%"></div>
          <Tag
              :severity="readingRecord.score > 50 ? 'success' : 'danger'"
              class="score-tag"
              v-tooltip.bottom="{
                value: readingRecord.score > 50 ? '太棒了！继续保持！🥳' : '加油！下次一定能做得更好！💪'
              }"
          >
            得分率: {{readingRecord.score}}%
          </Tag>
        </div>
        <div :key="exercise.exerciseId" v-for="(exercise, index) in exercises" style="width: 100%; margin-top: 50px">
          <div>
            <div style="display: flex; flex-direction: row; align-items: center">
              <Tag
                  style="margin-right: 10px;"
                  :severity="userSelect[index].selectionId === exercise.answerSelectionId ? 'success' : 'danger'"
              >
                {{userSelect[index].selectionId === exercise.answerSelectionId ? '正确' : '错误'}}
              </Tag>
              <p>{{exercise.content}}</p>
            </div>
            <RadioButtonGroup>
              <Tag
                  :key="selection.exerciseSelectionId"
                  v-for="selection in exercise.selections"
                  class="select-tag"
                  :severity="selection.exerciseSelectionId === exercise.answerSelectionId ? 'success' : selection.exerciseSelectionId === userSelect[index].selectionId ? 'danger' : 'primary'"
              >
                <RadioButton
                    v-model="userSelect[index].selectionId"
                    :value="selection.exerciseSelectionId"
                    :inputId="selection.exerciseSelectionId"
                    disabled
                />
                <label style="width: fit-content;margin: 0;margin-left: 10px; cursor: pointer" :for="selection.exerciseSelectionId">{{ selection.selection }}</label>
              </Tag>
            </RadioButtonGroup>
          </div>
        </div>

      </SplitterPanel>
    </Splitter>
  </div>
  <div style="width: 100%;text-align: center;color: #999999;font-size: large;margin-top: 20px" v-else>
    暂无数据
  </div>

  <Toast />
</template>

<script>
import {getRecord} from "@/apis/reading";

export default {
  name: 'ReadingRecord',
  data() {
    return {
      readingRecordId: null,
      title: null,
      content: null,
      exercises: [],
      userSelect: [],
      readingRecord: null,
    }
  },
  methods: {
    getReadingRecord(readingRecordId) {
      getRecord(readingRecordId)
          .then(response => {
            const readingPractice = response.data.readingPractice;
            this.content = readingPractice.content;
            this.title = readingPractice.title;
            this.exercises = readingPractice.exercises;
            this.readingRecord = response.data.readingRecord;
            const exerciseRecords = response.data.exerciseRecords;
            for (const exerciseRecordsKey in exerciseRecords) {
              const exerciseRecord = exerciseRecords[exerciseRecordsKey];
              this.userSelect.push({
                exerciseId: exerciseRecord.exerciseId,
                selectionId: exerciseRecord.exerciseSelectionId,
              });
            }
          })
          .catch(error => {
            this.$toast.add({severity: 'error', summary: '获取阅读记录失败', detail: error.message});
          });
    }
  },
  mounted() {
    this.readingRecordId = this.$route.params.id;
    this.getReadingRecord(this.readingRecordId);
  }
}
</script>

<style scoped>
.select-tag {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-top: 5px;
  padding: 5px;
}

.score-tag {
  font-size: x-large;
  flex-shrink: 0;
  user-select: none;
  cursor: pointer;
}
</style>