<script setup>
import Tabs from "primevue/tabs";
import TabPanels from "primevue/tabpanels";
import TabList from "primevue/tablist";
import Tab from "primevue/tab";
import TabPanel from "primevue/tabpanel";
import {Toast} from "primevue";
import {BASE_URL} from "@/apis/request";
</script>

<template>
  <div style="width: 100%;height: 100%;display: flex; justify-content: center;padding: 20px" v-if="title">
    <Tabs value="0" style="width: 100%;max-width: 1300px" >
      <TabList>
        <Tab value="0" style="font-size: x-large;font-weight: bold">{{title}}</Tab>
      </TabList>
      <TabPanels>
        <TabPanel value="0">
          <div style="display: flex;flex-direction: row;">
            <div style="width: 100%"></div>
            <Tag
                :severity="listeningRecord.score > 50 ? 'success' : 'danger'"
                class="score-tag"
                v-tooltip.bottom="{
                value: listeningRecord.score > 50 ? '太棒了！继续保持！🥳' : '加油！下次一定能做得更好！💪'
              }"
            >
              得分率: {{listeningRecord.score}}%
            </Tag>
          </div>
          <audio :src="BASE_URL + audio" controls></audio>
          <div :key="exercise.exerciseId" v-for="(exercise, index) in exercises" style="width: 100%; margin-top: 50px">
            <div>
              <p>{{exercise.content}}</p>
              <RadioButtonGroup>
<!--                <div style="display: flex;flex-direction: row;align-items: center;margin-top: 15px" :key="selection.exerciseSelectionId"  v-for="selection in exercise.selections">-->
<!--                  <RadioButton v-model="userSelect[index].selectionId" :value="selection.exerciseSelectionId"  />-->
<!--                  <label style="width: fit-content;margin: 0;margin-left: 10px">{{ selection.selection }}</label>-->
<!--                </div>-->
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
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>
  <div v-else>
    听力不存在
  </div>

  <Toast />
</template>

<script>
import {getRecord} from "@/apis/listening";

export default {
  name: 'ListeningRecord',
  data() {
    return {
      title: null,
      audio: null,
      userSelect: [],
      exercises: [],
      listeningPracticeId: null,
      exerciseCount: null,
      listeningRecord: null,
      listeningRecordId: null,
    }
  },
  methods: {
    getListeningRecord(listeningRecordId) {
      getRecord(listeningRecordId)
          .then(response => {
            const listeningPractice = response.data.listeningPractice;
            this.audio = listeningPractice.audio;
            this.exercises = listeningPractice.exercises;
            this.title = listeningPractice.title;
            this.listeningRecord = response.data.listeningRecord;
            const exerciseRecords = response.data.exerciseRecords;
            for (const exerciseRecordsKey in exerciseRecords) {
              const exerciseRecord = exerciseRecords[exerciseRecordsKey];
              this.userSelect.push({
                selectionId: exerciseRecord.exerciseSelectionId,
                exerciseId: exerciseRecord.exerciseId,
              });
            }
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '错误',
              detail: error,
              life: 3000,
            });
          });
    }
  },
  mounted() {
    this.listeningRecordId = this.$route.params.id;
    this.getListeningRecord(this.listeningRecordId);
  },
}
</script>

<style scoped>
.select-tag {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-top: 5px;
  padding: 6px 10px 6px 6px;
  width: fit-content;
}

.score-tag {
  font-size: x-large;
  flex-shrink: 0;
  user-select: none;
  cursor: pointer;
}
</style>