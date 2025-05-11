<script setup>
import {Toast} from "primevue";
</script>

<template>
  <div style="width: 100%; height: 100%; display: flex; flex-direction: column; align-items: center">
    <Tabs value="0" style="width: 100%;max-width: 1300px" >
      <TabList>
        <Tab value="0" style="font-size: x-large;font-weight: bold">词汇练习</Tab>
      </TabList>
      <TabPanels>
        <TabPanel value="0">
          <Carousel
              style="width: 100%; margin-top: 15px;"
              :value="vocabulary"
              :numVisible="1"
              :numScroll="1"
              :page="page"
              @update:page="onChange"
          >
            <template #item="slotProps">
              <div class="exercise-card" v-if="slotProps.index < vocabulary.length - 1">
                <div style="width: 100%; text-align: center; padding: 20px; font-size: 100px">
                  {{slotProps.data.word}}
                </div>
                <div style="display: flex; flex-direction: row;flex-wrap: wrap;width: 400px; justify-content: center;margin-top: 20px">
                  <div style="display: flex;flex-direction: row;align-items: center;" :key="selection"  v-for="selection in slotProps.data.selections">
                    <RadioButton v-model="userSelect[slotProps.index].meaning" style="display: none" :value="selection" :inputId="selection" />
                    <label @click="next" class="radio-button" :class="userSelect[slotProps.index].meaning === selection? 'radio-button-selected' : {} " style="width: 120px;height: 120px; margin: 10px;cursor: pointer" :for="selection">{{ selection }}</label>
                  </div>
                </div>
              </div>
              <div v-else class="exercise-card end">
                <div style="width: 100%; text-align: center; padding: 20px; font-size: 100px;">词汇练习已完成！🥳</div>
                <Button style="width: 400px;margin-top: 100px" @click="submitVocabularyPractice" :disabled="buttonDisable">提交</Button>
              </div>
            </template>
            <template #empty>
              <div class="exercise-card" style="height: 550px">
                <div style="width: 100%; text-align: center; padding: 20px; font-size: 100px;">词汇练习为空</div>
              </div>
            </template>
          </Carousel>
        </TabPanel>
      </TabPanels>
    </Tabs>
  </div>

  <Dialog v-model:visible="visible" modal>
    <template #container>
      <div style="padding: 20px">
        <div style="font-size: large;">
          还要继续练习吗?
        </div>
        <div style="margin-top: 35px">
          <Button @click="backHome" severity="danger">返回主页</Button>
          <Button @click="reset" style="margin-left: 5px">继续练习</Button>
        </div>
      </div>
    </template>
  </Dialog>
  <Toast />
</template>

<script>
import Tabs from "primevue/tabs";
import Tab from "primevue/tab";
import TabList from "primevue/tablist";
import TabPanels from "primevue/tabpanels";
import TabPanel from "primevue/tabpanel";
import {getPractices, submit} from "@/apis/vocabulary";

export default {
  name: 'Vocabulary',
  components: {TabPanel, TabPanels, TabList, Tab, Tabs},
  data() {
    return {
      vocabulary: [
      ],
      userSelect: [],
      buttonDisable: false,
      visible: false,
      page: 0,
    };
  },
  methods: {
    getVocabulary(count) {
      getPractices(count)
          .then(response => {
            this.vocabulary = response.data;
            this.userSelect = [];
            for (const vocabularyKey in this.vocabulary) {
              const vocabulary = this.vocabulary[vocabularyKey];
              this.userSelect.push({
                vocabularyId: vocabulary.vocabularyId,
                meaning: null
              });
            }
            this.vocabulary.push({});
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '获取词汇失败',
              detail: error,
              life: 3000,
            });
          });
    },
    submitVocabularyPractice() {
      if (this.buttonDisable) {
        return;
      }
      this.buttonDisable = true;
      for (const userSelectKey in this.userSelect) {
        const exercise = this.userSelect[userSelectKey];
        if (exercise.meaning === null) {
          this.$toast.add({
            severity: 'error',
            summary: '请完成所有题目',
            life: 3000,
          });
          this.buttonDisable = false;
          return;
        }
      }

      submit(this.userSelect)
          .then(response => {
            this.$toast.add({
              severity: 'success',
              summary: '提交成功',
              detail: response.data,
              life: 3000,
            });
            this.visible = true;
          })
          .catch(error => {
            this.$toast.add({
              severity: 'error',
              summary: '提交失败',
              detail: error,
              life: 3000,
            });
          });
      this.buttonDisable = false;
    },
    reset() {
      this.visible = false;
      this.getVocabulary(10);
      this.page = 0;
    },
    backHome() {
      this.$router.push({path: '/'});
    },
    pre() {
      if (this.page > 0) {
        this.page--;
      }
    },
    next() {
      if (this.page < this.vocabulary.length - 1) {
        this.page++;
      }
    },
    onChange(newPage) {
      this.page = newPage;
    }
  },
  mounted() {
    this.reset();
  },
};
</script>

<style scoped>
.exercise-card {
  background:var(--p-content-background);
  color:var(--p-content-color);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  border: 1px solid var(--p-surface-200);
  display:flex;
  flex-direction:column;
  padding: 20px;
  align-items: center;
}

.radio-button {
  border-radius: 12px;
  border: 1px solid var(--p-surface-300);
  text-align: center;
  align-content: center;
  color: var(--p-text-color);
  user-select: none;
}

.radio-button-selected {
  background-color: var(--p-primary-color);
  color: var(--p-primary-100);
  border: 1px solid var(--p-primary-color);
  transition: background-color 0.3s, color 0.3s, border 0.3s;
}

.end {
  height: 100%;
}
</style>
