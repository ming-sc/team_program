<script setup>
import {Toast} from "primevue";
</script>

<template>
  <div style="width: 100%;display: flex; justify-content: center; padding: 20px">
    <div style="display: flex;width: 100%; max-width: 1300px;flex-direction: column; align-items: center">
      <div class="exercise-card" style="width: 100%">
        <!-- 文件上传框 -->
        <div v-if="!selectFile" class="file-uploader"
             style="flex-direction: column; height: 200px"
             @dragover="fileUploaderDropOver"
             @dragenter="fileUploaderDropOver"
             @dragleave="fileUploaderDropLeave"
             @click="fileUploaderOnClick"
             @drop.prevent="fileUploaderDrop"
        >
          <i class="pi pi-cloud-upload" style="font-size: 70px;" />
          <p style="font-size: xx-large;margin: 10px 0 0;">拖拽或选择文件</p>
          <input ref="fileInput" @change="onFileChange" type="file" style="display: none" :multiple="false" />
        </div>
        <div v-else>
          <div class="file">
            <p style="width: 100%">{{selectFile.name}}</p>
            <Button severity="danger" text icon="pi pi-times" @click="removeSelectedFile"></Button>
          </div>
          <div style="display: flex; flex-direction: row;align-items: center;margin-top: 10px">
            <InputText v-model="fileName" style="flex: 1" placeholder="文件名"></InputText>
            <Button icon="pi pi-spin pi-spinner" :disabled="uploading" style="height: 36px; margin-left: 10px;flex: 1" @click="uploadFile" >
              上传
            </Button>
          </div>
        </div>
      </div>
      <div style="width: 100%; display: flex; flex-direction: row">
        <div @click="this.$router.push({ path: '/manager/addReading'})" class="exercise-card card-hover" style="margin: 0;flex: 1">
          <div style="display: flex; flex-direction: column;align-items: center">
            <i class="pi pi-book icon" style="font-size: 70px;" />
            <p style="font-size: xx-large;margin: 10px 0 0;">导入阅读练习</p>
          </div>
        </div>
        <div @click="this.$router.push({ path: '/manager/addListening'})" class="exercise-card card-hover" style="flex: 1; margin: 0 0 0 20px;">
          <div style="display: flex; flex-direction: column;align-items: center">
            <i class="pi pi-volume-up icon" style="font-size: 70px;" />
            <p style="font-size: xx-large;margin: 10px 0 0;">导入听力练习</p>
          </div>
        </div>
        <div @click="this.$router.push({ path: '/manager/addVocabulary'})" class="exercise-card card-hover" style="flex: 1; margin: 0 0 0 20px;">
          <div style="display: flex; flex-direction: column;align-items: center">
            <i class="pi pi-file-word icon" style="font-size: 70px;" />
            <p style="font-size: xx-large;margin: 10px 0 0;">导入词汇练习</p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Toast />
</template>

<script>
import {upload} from "@/apis/file";

export default {
  name: 'Manager',
  data() {
    return {
      selectFile: null,
      fileName: null,
      uploading: false,
    }
  },
  methods: {
    fileUploaderDropOver(e) {
      e.preventDefault();
      e.stopPropagation();
      e.target.classList.add("drag-enter");
    },
    fileUploaderDropLeave(e) {
      e.target.classList.remove("drag-enter");
    },
    fileUploaderOnClick() {
      this.$refs.fileInput.click();
    },
    onFileChange(e) {
      const files = e.target.files;
      if (files.length > 0) {
        this.handleFile(files[0]);
      }
    },
    fileUploaderDrop(e) {
      e.preventDefault();
      e.stopPropagation();
      this.fileUploaderDropLeave(e);
      const files = e.dataTransfer.files;
      if (files.length > 0) {
        this.handleFile(files[0]);
      }
    },
    handleFile(file) {
      if (!file) {
        return;
      }
      console.log(file);
      this.selectFile = file;
    },
    removeSelectedFile() {
      this.selectFile = null;
    },
    uploadFile() {
      if (this.uploading) {
        return;
      }
      this.uploading = true;
      if (!this.fileName || this.fileName === "") {
        this.$toast.add({severity: 'error', summary: '错误', detail: '请输入文件名', life: 3000});
        return;
      }
      if (!this.selectFile) {
        this.$toast.add({severity: 'error', summary: '错误', detail: '请先选择文件', life: 3000});
        return;
      }

      this.$toast.add({severity: 'info', summary: '上传中', detail: '请稍等...', life: 3000});
      upload(this.selectFile, this.fileName)
          .then(response => {
            this.$toast.add({severity: 'success', summary: '成功', detail: response.data.message, life: 3000});
            this.selectFile = null;
            this.fileName = null;
          })
          .catch(error => {
            this.$toast.add({severity: 'error', summary: '错误', detail: error, life: 3000});
          })
          .finally(() => {
            this.uploading = false;
          });
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.exercise-card {
  margin: 15px;
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

.file-uploader {
  height: 100%;
  border-style: dashed;
  border-radius: 8px;
  border-color: var(--p-surface-200);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.file-uploader:hover {
  border-color: var(--p-surface-400);
  transition: border-color 0.3s;
  cursor: pointer;
}

.file-uploader:drag-over {
  border-color: var(--p-surface-400);
}

.file-uploader.drag-enter {
  border-color: var(--p-surface-400);
  background-color: var(--p-surface-200);
}

.file {
  padding: 10px;
  height: 50px;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.file:hover {
  border-radius: 5px;
  border-style: solid;
  border-width: 1px;
  border-color: var(--p-surface-400);
  background-color: var(--p-surface-200);
  transition: border-color 0.3s;
  cursor: pointer;
}

.card-hover:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: box-shadow 0.3s;
  cursor: pointer;
}
</style>