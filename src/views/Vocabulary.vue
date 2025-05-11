<template>
  <div class="vocabulary-card">
    <!-- 顯示當前詞彙的英文單詞 -->
    <h1>{{ currentWord.word }}</h1>
    <!-- 選項按鈕區域 -->
    <div class="options">
      <!-- 遍歷選項，生成按鈕 -->
      <button
        v-for="(option, index) in options"
        :key="index"
        @click="checkAnswer(option)"
      >
        {{ option }}
      </button>
    </div>
    <!-- 顯示反饋信息 -->
    <p v-if="feedback">{{ feedback }}</p>
  </div>
</template>

<script>
export default {
  name: 'Vocabulary',
  data() {
    return {
      words: [
        { word: 'apple', correct: '蘋果' },
        { word: 'banana', correct: '香蕉' },
        { word: 'cat', correct: '貓' },
        { word: 'dog', correct: '狗' },
      ],
      currentIndex: 0,
      options: [],
      feedback: '',
    };
  },
  computed: {
    // 計算屬性，返回當前詞彙
    currentWord() {
      return this.words[this.currentIndex];
    },
  },
  methods: {
    generateOptions() {
      const correctAnswer = this.currentWord.correct;
      const otherOptions = ['橘子', '西瓜', '葡萄'];
      this.options = [correctAnswer, ...otherOptions].sort(() => Math.random() - 0.5);
    },
    checkAnswer(selected) {
      if (selected === this.currentWord.correct) {
        this.feedback = '選擇正確！';
        setTimeout(() => {
          this.nextWord();
        }, 1000);
      } else {
        this.feedback = '選擇錯誤，請再試一次！';
      }
    },
    nextWord() {
      if (this.currentIndex < this.words.length - 1) {
        this.currentIndex++;
        this.generateOptions();
        this.feedback = '';
      } else {
        this.feedback = '所有詞彙已完成！';
      }
    },
  },
  mounted() {
    this.generateOptions();
  },
};
</script>

<style scoped>
/* 詞彙卡片的樣式 */
.vocabulary-card {
  max-width: 400px;
  margin: 0 auto;
  text-align: center;
}

/* 詞彙標題的樣式 */
h1 {
  font-size: 2rem;
  margin-bottom: 20px;
}

/* 選項按鈕的佈局 */
.options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

/* 按鈕的樣式 */
button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* 按鈕的懸停效果 */
button:hover {
  background-color: #0056b3;
}

/* 反饋信息的樣式 */
p {
  margin-top: 20px;
  font-size: 1.2rem;
  color: green;
}
</style>
