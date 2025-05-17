<script setup>
import Tabs from "primevue/tabs";
import Tab from "primevue/tab";
import TabPanel from "primevue/tabpanel";
import TabPanels from "primevue/tabpanels";
import TabList from "primevue/tablist";
import {Toast} from "primevue";
import ConfirmDialog from "primevue/confirmdialog";
</script>

<template>
  <div style="width: 100%;height: 100%;display: flex;flex-direction: column;align-items: center;margin-top: 20px">
    <div style="width: 100%;max-width: 1000px;">
      <Tabs value="0">
        <TabList>
          <Tab value="0" style="font-size: x-large;font-weight: bold">
            <p style="margin: 0">导入词汇练习</p>
          </Tab>
        </TabList>
        <TabPanels>
          <TabPanel value="0">
            <div style="width: 100%;height: 100%;">
              <div
                  v-for="(vocabulary, index) in vocabularies"
                  :key="index"
                  style="width: 100%;display: flex;flex-direction: row; margin-top: 20px"
              >
                <InputText v-model="vocabulary.word" style="flex: 1" placeholder="请输入单词" />
                <InputText v-model="vocabulary.meaning" style="margin-left: 10px;flex: 1;" placeholder="请输入中文意思" />
                <Button text severity="danger" icon="pi pi-times" style="margin-left: 10px" size="small" @click="removeVocabulary(index)" />
              </div>
              <Button v-tooltip.bottom="'添加单词'" icon="pi pi-plus-circle" text style="margin-top: 20px" severity="success" size="small" @click="insertVocabulary" />
            </div>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </div>
    <Button @click="addVocabulary" :disabled="loading" style="width: 100%;max-width: 965px;" label="提交" />
  </div>
  <ConfirmDialog />
  <Toast />
</template>

<script>
import {add} from "@/apis/vocabulary";

export default {
  name: 'AddVocabulary',
  data() {
    return {
      vocabularies: [
        {
          word: '单词一',
          meaning: '中文意思'
        }
      ],
      loading: false,
    }
  },
  methods: {
    insertVocabulary() {
      this.vocabularies.push({ word: '', meaning: '' });
    },
    removeVocabulary(index) {
      this.vocabularies.splice(index, 1);
    },
    checkVocabulary() {
      for (const vocabulariesKey in this.vocabularies) {
        if (this.vocabularies[vocabulariesKey].word === '' || this.vocabularies[vocabulariesKey].meaning === '') {
          return false;
        }
      }
      return true;
    },
    addVocabulary() {
      if (this.loading) {
        return;
      }
      this.loading = true;

      if (this.vocabularies.length === 0) {
        this.$toast.add({
          severity: 'error',
          summary: '错误',
          detail: '请至少添加一个单词',
          life: 3000
        });
        this.loading = false;
        return;
      }

      if (!this.checkVocabulary()) {
        this.$toast.add({
          severity: 'error',
          summary: '错误',
          detail: '单词或中文意思不能为空',
          life: 3000
        });
        this.loading = false;
        return;
      }

      add(this.vocabularies)
        .then(response => {
          this.$confirm.require({
            message: response.message,
            header: '上传成功',
            icon: 'pi pi-check',
            rejectProps: {
              label: '取消',
            },
            acceptProps: {
              label: '返回主页'
            },
            accept: () => {
              this.$router.push({ path: '/manager' });
            }
          });
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