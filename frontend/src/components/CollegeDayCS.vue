<template>
  <div class="block">
    <span class="demonstration">周</span>
    <el-date-picker
      v-model="value1"
      type="week"
      format="yyyy 第 WW 周"
      @change="value"
      placeholder="选择周">
    </el-date-picker>
  </div>
</template>

<script>
export default {
  name: "CollegeDayCS",
  data() {
    return {
      value1: ''
    }
  },
  methods: {
    value() {
      console.log(this.value1)
    }
  }
}
</script>

<style scoped>

</style>
