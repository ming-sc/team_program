<template>
  <div class="reading-card">
    <h1>{{ currentQuestion.text }}</h1>
    <div class="options">
      <button
        v-for="(option, index) in options"
        :key="index"
        @click="checkAnswer(option)"
      >
        {{ option }}
      </button>
    </div>
    <p v-if="feedback">{{ feedback }}</p>
  </div>
</template>

<script>
export default {
  name: 'Reading',
  data() {
    return {
      questions: [
        {
          text: '閱讀題目 1：以下哪個是正確的？',
          correct: '選項 A',
        },
        {
          text: '閱讀題目 2：以下哪個是錯誤的？',
          correct: '選項 C',
        },
        {
          text: '閱讀題目 3：以下哪個是正確的？',
          correct: '選項 B',
        },
      ],
      currentIndex: 0,
      options: [],
      feedback: '',
    };
  },
  computed: {
    currentQuestion() {
      return this.questions[this.currentIndex];
    },
  },
  methods: {
    generateOptions() {
      const correctAnswer = this.currentQuestion.correct;
      const otherOptions = ['選項 A', '選項 B', '選項 C', '選項 D'].filter(
        (option) => option !== correctAnswer
      );
      // 從 otherOptions 中隨機選取 3 個選項
      const randomOptions = otherOptions.sort(() => Math.random() - 0.5).slice(0, 3);
      // 合併正確答案和隨機選項，並隨機排序
      this.options = [correctAnswer, ...randomOptions].sort(() => Math.random() - 0.5);
    },
    checkAnswer(selected) {
      if (selected === this.currentQuestion.correct) {
        this.feedback = '選擇正確！';
        setTimeout(() => {
          this.nextQuestion();
        }, 1000);
      } else {
        this.feedback = '選擇錯誤，請再試一次！';
      }
    },
    nextQuestion() {
      if (this.currentIndex < this.questions.length - 1) {
        this.currentIndex++;
        this.generateOptions();
        this.feedback = '';
      } else {
        this.feedback = '所有題目已完成！';
      }
    },
  },
  mounted() {
    this.generateOptions();
  },
};
</script>

<style scoped>
.reading-card {
  max-width: 600px;
  margin: 0 auto;
  text-align: center;
}

h1 {
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

p {
  margin-top: 20px;
  font-size: 1.2rem;
  color: green;
}
</style>
