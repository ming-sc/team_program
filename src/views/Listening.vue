<script setup>
import Tabs from 'primevue/tabs';
import TabList from 'primevue/tablist';
import Tab from 'primevue/tab';
import TabPanels from 'primevue/tabpanels';
import TabPanel from 'primevue/tabpanel';
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
        <TabPanel active value="0">
          <audio :src="BASE_URL + audio" controls></audio>
          <div :key="exercise.exerciseId" v-for="(exercise, index) in exercises" style="width: 100%; margin-top: 50px">
            <div>
              <p>{{exercise.content}}</p>
              <RadioButtonGroup>
                <div style="display: flex;flex-direction: row;align-items: center;margin-top: 15px" :key="selection.exerciseSelectionId"  v-for="selection in exercise.selections">
                  <RadioButton v-model="userSelect[index].selectionId" :value="selection.exerciseSelectionId"  />
                  <label style="width: fit-content;margin: 0;margin-left: 10px">{{ selection.selection }}</label>
                </div>
              </RadioButtonGroup>
            </div>
          </div>
          <Button :disabled="submitLock" style="width: 100%;margin-top: 30px" @click="submitListeningPractice">提交</Button>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>
  <div class="empty-card" v-else>
    听力不存在
    <Button style="margin: 40px" @click="this.$router.push({path: '/'})">返回主页</Button>
  </div>

  <Toast />
</template>

<script>
import {getPractice} from "@/apis/listening";
import {submit} from "@/apis/listening";

export default {
  name: 'Listening',
  data() {
    return {
      title: null,
      audio: null,
      userSelect: [],
      exercises: [],
      listeningPracticeId: null,
      exerciseCount: null,
      submitLock: false,
    };
  },
  methods: {
    getListeningPractice(id) {
      getPractice(id)
          .then(response => {
            this.audio = response.data.audio;
            this.exercises = response.data.exercises;
            this.title = response.data.title;
            this.exerciseCount = response.data.exerciseCount;
            this.userSelect = [];
            for (const exercisesKey in this.exercises) {
              const exercise = this.exercises[exercisesKey];
              this.userSelect.push({
                exerciseId: exercise.exerciseId,
                selectionId: null,
              });
            }
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '获取阅读练习失败',
              detail: error,
              life: 3000,
            });
          });
    },
    submitListeningPractice() {
      if (this.submitLock) {
        return;
      }
      this.submitLock = true;
      for (const userSelectKey in this.userSelect) {
        const exercise = this.userSelect[userSelectKey];
        if (exercise.selectionId === null) {
          this.$toast.add({
            severity: 'error',
            summary: '请完成所有题目',
            life: 3000,
          });
          this.submitLock = false;
          return;
        }
      }
      const data = {
        listeningPracticeId: this.listeningPracticeId,
        exercises: this.userSelect
      }

      submit(data)
          .then(response => {
            console.log(response);
            this.$router.push({ path: `/listeningRecord/${response.data.listeningRecordId}` });
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '提交失败',
              detail: error,
              life: 3000,
            });
          });

      this.submitLock = false;
    },
  },
  mounted() {
    this.listeningPracticeId = this.$route.params.id;
    this.getListeningPractice(this.listeningPracticeId);
  }
};
</script>

<style scoped>
.empty-card {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: x-large;
  font-weight: bold;
  flex-direction: column;
  background-color: #f0f0f0;
  color: #999999;
}
</style>
