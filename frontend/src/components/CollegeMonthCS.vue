<template>
  <div style="margin-left: 30px">
    <el-row :gutter="20" style="height: 70px">
      <el-col :span="24" style="border-radius: 4px;">
        <el-card shadow="always" style="width: content-box;height: content-box;text-align: left"
                 body-style="padding: 10px 5px 0px 20px;height:60px">
          <el-form :inline="true" :model="formInline" style="margin-top: 5px;height: 40px" :rules="rules"
                   ref="formInline">
            <el-form-item prop="date">
              <div class="block">
                <span class="demonstration">年</span>
                <el-date-picker
                  v-model="formInline.year"
                  unlink-panels
                  style="width: 300px"
                  type="year"
                  placeholder="选择年">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit('formInline')">查询</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 450px">
      <el-col :span="12" style="height: 400px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart" ref="chart" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
      <el-col :span="12" style="height: 400px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart1" ref="chart1" style="height: 100%;width: 100%"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 10px;height: 350px">
      <el-col :span="13" style="height: 300px;">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart2" ref="chart2" style="height: 100%;width: 100%;margin-left: 10px"></div>
        </el-card>
      </el-col>
      <el-col :span="10" style="height: 300px">
        <el-card style="height:inherit;width: content-box;padding: 20px"
                 body-style="height:100%;width: 100%;padding:0px">
          <div id="chart3" ref="chart3" style="height:100%;width: 100%"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "CollegeMonthCS",
  data() {
    return {
      formInline: {
        year: ''
      },
    }
  },
}
</script>

<style scoped>

</style>
