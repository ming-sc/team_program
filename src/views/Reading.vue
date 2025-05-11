<script setup>
import Tabs from 'primevue/tabs';
import TabList from 'primevue/tablist';
import Tab from 'primevue/tab';
import TabPanels from 'primevue/tabpanels';
import TabPanel from 'primevue/tabpanel';
import {Toast} from "primevue";
</script>

<template>
  <div style="width: 100%;height: 100%;display: flex; justify-content: center;padding: 20px" v-if="title">
    <Splitter style="width: 100%;height: 100%">
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
      <SplitterPanel style="overflow: scroll;padding: 20px">
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
        <Button @click="submitReadingPractice" style="width: 100%;margin-top: 30px">
          提交
        </Button>
      </SplitterPanel>
    </Splitter>
<!--      <Tabs value="0" style="width: 100%;max-width: 1300px" >-->
<!--        <TabList>-->
<!--          <Tab value="0" style="font-size: x-large;font-weight: bold">{{title}}</Tab>-->
<!--        </TabList>-->
<!--        <TabPanels>-->
<!--          <TabPanel active value="0">-->

<!--            <p style="white-space: pre-wrap; font-size: large;line-height: 25px">-->
<!--              {{content}}-->
<!--            </p>-->
<!--            <div :key="exercise.exerciseId" v-for="(exercise, index) in exercises" style="width: 100%; margin-top: 50px">-->
<!--              <div>-->
<!--                {{userSelect[index].selectionId}}-->
<!--                <p>{{exercise.content}}</p>-->
<!--                <RadioButtonGroup>-->
<!--                  <div style="display: flex;flex-direction: row;align-items: center;margin-top: 15px" :key="selection.exerciseSelectionId"  v-for="selection in exercise.selections">-->
<!--                    <RadioButton v-model="userSelect[index].selectionId" :value="selection.exerciseSelectionId"  />-->
<!--                    <label style="width: fit-content;margin: 0;margin-left: 10px">{{ selection.selection }}</label>-->
<!--                  </div>-->
<!--                </RadioButtonGroup>-->
<!--              </div>-->
<!--            </div>-->
<!--          </TabPanel>-->
<!--        </TabPanels>-->
<!--      </Tabs>-->
  </div>
  <div v-else>
    阅读不存在
  </div>

  <Toast />
</template>

<script>
import {getPractice} from "@/apis/reading";
import {submit} from "@/apis/reading";

export default {
  name: 'Reading',
  data() {
    return {
      content: null,
      value: null,
      exercises: [
      ],
      readingPracticeId: null,
      title: null,
      exerciseCount: null,
      userSelect: [],
    };
  },
  methods: {
    getReading(readingPractiseId) {
      getPractice(readingPractiseId)
          .then(response => {
            this.content = response.data.content;
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
    submitReadingPractice() {
      for (const userSelectKey in this.userSelect) {
        const exercise = this.userSelect[userSelectKey];
        if (exercise.selectionId === null) {
          this.$toast.add({
            severity: 'error',
            summary: '请完成所有题目',
            life: 3000,
          });
          return;
        }
      }
      const data = {
        readingPracticeId: this.readingPracticeId,
        exercises: this.userSelect
      }

      submit(data)
          .then(response => {
            console.log(response);
            this.$router.push({ path: '/' });
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '提交失败',
              detail: error,
              life: 3000,
            });
          });
    }
  },
  mounted() {
    this.readingPracticeId = this.$route.params.id;
    this.getReading(this.readingPracticeId);
  },
};
</script>

<style scoped>
</style>
