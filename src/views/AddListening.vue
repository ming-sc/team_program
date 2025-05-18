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
        <Tabs value="0" style="width: 100%;max-width: 1300px;height: 100%" >
          <TabList>
            <Tab value="0" style="font-size: x-large;font-weight: bold">
              <InputText v-if="isEdit" v-model="title" placeholder="请输入标题" />
              <p v-else style="margin: 0">{{title}}</p>
            </Tab>
          </TabList>
          <TabPanels style="height: 100%">
            <TabPanel style="height: 100%;display: flex;flex-direction: column" active value="0">
              <FileUpload v-if="isEdit" custom-upload mode="basic" accept="audio/*" choose-label="选择音频文件" @select="onFileSelect" />
              <audio style="margin-top: 10px;width: 400px" v-if="audioFile" :src="audioFile" controls />
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
            </TabPanel>
          </TabPanels>
        </Tabs>
    <div style="display: flex;flex-direction: row; width: 100%;margin-top: 10px;max-width: 1300px">
      <Button @click="isEdit = !isEdit" style="flex: 1" severity="contrast" variant="outlined">{{isEdit ? '预览' : '编辑' }}</Button>
      <Button :disabled="loading" @click="addListening" style="width: 100%;flex: 1;margin-left: 10px">
        提交
      </Button>
    </div>
  </div>
  <Toast />
</template>

<script>
import {add} from "@/apis/listening"
import {upload} from "@/apis/file";

export default {
  name: 'AddListening',
  data() {
    return {
      isEdit: true,
      title: null,
      audioFile: null,
      audio: null,
      audioSrc: null,
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
    onFileSelect(e) {
      const file = e.files[0];
      const reader = new FileReader();
      this.audio = file;
      reader.onload = async (e) => {
        this.audioFile = e.target.result;
      }
      reader.readAsDataURL(file);
    },
    insertExercise(exerciseIndex) {
      this.exercises.splice(exerciseIndex + 1, 0, {
        content: null,
        answerSelectionId: null,
        selections: []
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
      if (this.exercises.length === 0) {
        this.$toast.add({
          severity: 'error',
          summary: '错误',
          detail: '请添加题目',
          life: 3000
        });
        return false;
      }
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
    addListening() {
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
      if (this.audio === null) {
        this.$toast.add({
          severity: 'error',
          summary: '错误',
          detail: '请设置音频',
          life: 3000
        });
        this.loading = false;
        return;
      }
      if (!this.checkExercise()) {
        this.loading = false;
        return;
      }
      upload(this.audio, "audio", 1)
          .then(response => {
            this.audioSrc = response.data.src;
            add(this.title, this.audioSrc, this.exercises)
                .then(response => {
                  this.$toast.add({
                    severity: 'success',
                    summary: '成功',
                    detail: response.data.message,
                    life: 3000
                  });
                  const listeningPracticeId = response.data.listeningPracticeId;
                  this.$router.push({path: `/listening/${listeningPracticeId}`});
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
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '上传文件错误',
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
.p-fileupload {
  width: fit-content;
}
</style>