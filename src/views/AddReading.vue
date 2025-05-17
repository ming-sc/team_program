<script setup>
import {Toast} from "primevue";
import Tabs from "primevue/tabs";
import TabList from "primevue/tablist";
import TabPanel from "primevue/tabpanel";
import Tab from "primevue/tab";
import TabPanels from "primevue/tabpanels";
</script>

<template>
  <div style="width: 100%;height: 100%;display: flex; align-items: center;padding: 20px; flex-direction: column">
    <Splitter style="width: 100%;height: 100%">
      <SplitterPanel style="overflow: scroll;padding: 20px;height: 100%">
        <Tabs value="0" style="width: 100%;max-width: 1300px;height: 100%" >
          <TabList>
            <Tab value="0" style="font-size: x-large;font-weight: bold">
              <InputText v-if="isEdit" v-model="title" placeholder="请输入标题" />
              <p v-else style="margin: 0">{{title}}</p>
            </Tab>
          </TabList>
          <TabPanels style="height: 100%">
            <TabPanel style="height: 100%" active value="0">
              <Textarea v-if="isEdit" v-model="content" placeholder="请输入阅读文章内容" style="width: 100%;height: 100%;resize: none"/>
              <p v-else style="white-space: pre-wrap; font-size: large;line-height: 25px">
                {{content}}
              </p>
            </TabPanel>
          </TabPanels>
        </Tabs>
      </SplitterPanel>
      <SplitterPanel style="overflow: scroll;padding: 20px">
        <div :key="exercise.exerciseId" v-for="(exercise, exerciseIndex) in exercises" style="width: 100%; margin-top: 50px">
          <div>
            <p v-if="!isEdit" style="margin: 0;align-content: center">{{exercise.content}}</p>
            <div v-else style="display: flex;flex-direction: row;">
              <Tag
                  style="margin-right: 10px;"
                  severity="danger"
                  v-if="exercise.answerSelectionId === null"
              >
                未设置正确答案
              </Tag>
              <InputText placeholder="请输入题目" style="width: 500px" v-model="exercise.content" />
              <Button v-tooltip.bottom="'添加选项'" icon="pi pi-plus-circle" text style="margin-left: 5px" severity="success" size="small" @click="insertSelection(exerciseIndex)" />
              <Button text severity="danger" icon="pi pi-times" style="margin-left: 5px" size="small" @click="removeExercise(exerciseIndex)" />
            </div>

            <RadioButtonGroup>
              <div style="display: flex;flex-direction: row;align-items: center;margin-top: 15px" :key="index"  v-for="(selection, index) in exercise.selections">
                <RadioButton :disabled="!isEdit" v-model="exercise.answerSelectionId" :value="index" />
                <label v-if="!isEdit" style="width: fit-content;margin: 0 0 0 10px;cursor: pointer">{{ selection }}</label>
                <div v-else style="display: flex;flex-direction: row; margin-left: 10px">
                  <InputText style="width: 400px" placeholder="请输入选项" v-model="exercise.selections[index]" />
                  <Button text severity="danger" icon="pi pi-times" style="margin-left: 5px" size="small" @click="removeSelection(exerciseIndex, index)"></Button>
                </div>
              </div>
            </RadioButtonGroup>
          </div>
        </div>
        <Button v-tooltip.right="'添加题目'" v-if="isEdit" icon="pi pi-plus-circle" text style="margin-top: 10px" severity="success"  @click="insertExercise(exercises.length - 1)" />
      </SplitterPanel>
    </Splitter>
    <div style="display: flex;flex-direction: row; width: 100%;margin-top: 10px;">
      <Button @click="isEdit = !isEdit" style="flex: 1" severity="contrast" variant="outlined">{{isEdit ? '预览' : '编辑' }}</Button>
      <Button :disabled="loading" @click="addReading" style="width: 100%;flex: 1;margin-left: 10px">
        提交
      </Button>
    </div>
  </div>
  <Toast />
</template>

<script>
import {add} from "@/apis/reading"

export default {
  name: 'AddReading',
  data() {
    return {
      isEdit: true,
      title: null,
      content: null,
      exercises: [
        {
          content: "题目1",
          answerSelectionId: null,
          selections: [
            "选项1",
            "选项2",
          ]
        }
      ],
      loading: false,
    }
  },
  methods: {
    insertExercise(exerciseIndex) {
      this.exercises.splice(exerciseIndex + 1, 0, {
        content: null,
        answerSelectionId: null,
        selections: [
        ]
      });
    },
    removeExercise(exerciseIndex) {
      this.exercises.splice(exerciseIndex, 1);
    },
    insertSelection(exerciseIndex) {
      this.exercises[exerciseIndex].selections.push(null);
    },
    removeSelection(exerciseIndex, index) {
      const answerSelectionId = this.exercises[exerciseIndex].answerSelectionId;
      if (answerSelectionId === index) {
        this.exercises[exerciseIndex].answerSelectionId = null;
      } else if (answerSelectionId > index) {
        this.exercises[exerciseIndex].answerSelectionId--;
      }
      this.exercises[exerciseIndex].selections.splice(index, 1);
    },
    checkExercise() {
      // 检查每个题目的答案是否设置以及选项是否设置
      for (const exerciseKey in this.exercises) {
        const exercise = this.exercises[exerciseKey];
        if (exercise.answerSelectionId === null) {
          this.$toast.add({
            severity: 'error',
            summary: '错误',
            detail: '请设置题目' + exerciseKey + '的答案',
            life: 3000
          });
          return false;
        }
        if (exercise.content === null || exercise.content === "") {
          this.$toast.add({
            severity: 'error',
            summary: '错误',
            detail: '请设置题目' + exerciseKey + '的题目',
            life: 3000
          });
          return false;
        }
        let flag = true;
        exercise.selections.forEach((selection) => {
          if (selection === null || selection === "") {
            this.$toast.add({
              severity: 'error',
              summary: '错误',
              detail: '请设置题目' + exerciseKey + '的选项',
              life: 3000
            });
            flag = false;
          }
        });
        if (!flag) {
          return false;
        }
      }
      return true;
    },
    addReading() {
      if (this.loading) {
        return;
      }
      this.loading = true;
      if (this.title === null || this.title === "") {
        this.$toast.add({
          severity: 'error',
          summary: '错误',
          detail: '请设置标题',
          life: 3000
        });
        this.loading = false;
        return;
      }
      if (this.content === null || this.content === "") {
        this.$toast.add({
          severity: 'error',
          summary: '错误',
          detail: '请设置内容',
          life: 3000
        });
        this.loading = false;
        return;
      }
      if (!this.checkExercise()) {
        this.loading = false;
        return;
      }
      add(this.title, this.content, this.exercises)
          .then(response => {
            this.$toast.add({
              severity: 'success',
              summary: '成功',
              detail: response.data.message,
              life: 3000
            });
            const readingPracticeId = response.data.readingPracticeId;
            this.$router.push({ path: `/reading/${readingPracticeId}` });
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '错误',
              detail: error,
              life: 3000
            });
          })
          .finally(() => {
            this.loading = false;
          });
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

</style>